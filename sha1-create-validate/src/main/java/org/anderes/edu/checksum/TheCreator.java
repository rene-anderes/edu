package org.anderes.edu.checksum;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.FluentIterable;
import static com.google.common.base.Preconditions.*;


public class TheCreator {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private ConcurrentLinkedQueue<ResultData> queue = new ConcurrentLinkedQueue<>();
    private AtomicBoolean filesReaderFinish = new AtomicBoolean(false);
    private Path csvFilePath = Paths.get("checksum.csv");
    private Optional<Path> blacklist = Optional.empty();
    private TheCalculator calculator = new TheSHA1Calculator();
    private final Logger logger = LogManager.getLogger();
    private final Logger loggerFile = LogManager.getLogger("FileWithError");
    
    public static TheCreator build() {
        return new TheCreator();
    }
    
    private TheCreator() {};
      
    public long createChecksumFromPath(final Path theDirectory) throws IOException {
        checkNotNull(theDirectory);
        logger.debug("TheCreator started");
        
        if (!Files.isDirectory(theDirectory, NOFOLLOW_LINKS)) {
            throw new IOException("not a directory (" + theDirectory + ")");
        }
        
        final FutureTask<Long> command = new FutureTask<Long>(new CsvFileWriter());
        executorService.execute(command);

        long count = createCheckSumFromPath(theDirectory);
        
        filesReaderFinish.set(true);
        logger.debug("Reader finished");
        logger.info("... {} files were processed", count);
        try {
            final Long writerCount = command.get();
            logger.info("The checksum of {} files were written in the file '{}'", writerCount, csvFilePath.toAbsolutePath().toString());
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
        executorService.shutdown();
        logger.debug("TheCreator 'createSha1FromPath' finished");
        return count;
    }
    
    public long createCheckSumFromPath(final Path theDirectory) throws IOException {
        checkNotNull(theDirectory);
        
        final FluentIterable<File> files = com.google.common.io.Files.fileTreeTraverser()
                        .breadthFirstTraversal(theDirectory.toFile())
                            .filter(file -> file.isFile())
                            .filter(file -> !isInBlacklist(file.toPath()));
        logger.info("{} files are processed...", files.size());
        final HashSet<Path> paths = new HashSet<>(files.size());
        files.forEach(f -> paths.add(f.toPath()));
        return paths.stream().parallel().peek(file -> handleFile(file)).count();
    }
    
    private List<String> createBlacklist() {
        if (!blacklist.isPresent()) {
            return new ArrayList<>(0);
        }
        try {
            return Files.lines(blacklist.get()).collect(Collectors.toList());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return new ArrayList<>(0);
    }

    /*package*/void handleFile(Path theFile) {

        logger.trace("processed path: {}", theFile);
        try {
            queue.add(calculator.eval(theFile));
        } catch (IOException e) {
            loggerFile.info("The file '{}' was not processed.", theFile);
            logger.warn(e);
        }
        
    }

    /*package*/long queueSize() {
        return queue.size();
    }
    
    public TheCreator setCsvFilePath(final Path theFile) {
        checkNotNull(theFile);
        csvFilePath = theFile;
        return this;
    }
    
    public TheCreator setBlacklist(final Path blacklistFile) {
        checkNotNull(blacklistFile);
        logger.info("Blacklist-File is '{}'", blacklistFile.toAbsolutePath().toString());
        blacklist = Optional.of(blacklistFile);
        return this;
    }
    
    /*package*/boolean isInBlacklist(Path theFile) {
        return createBlacklist().stream().filter(regex -> {
                final PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("regex:" + regex);
                return pathMatcher.matches(theFile);
            })
            .count() > 0;
    }

    private class CsvFileWriter implements Callable<Long> {
        
        private File filePath = csvFilePath.toFile();

        @Override
        public Long call() throws Exception {
            long c = 0;
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                while(!filesReaderFinish.get()) {
                    while(!queue.isEmpty()) {
                        final ResultData data = queue.poll();
                        writer.write(String.format("%s;%s%n", data.getPath().toAbsolutePath(), data.getValue()));
                        c++;
                    }
                    writer.flush();
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            logger.debug("Writer finished");
            return c;
        }
    }
}
