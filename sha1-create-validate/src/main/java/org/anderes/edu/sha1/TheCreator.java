package org.anderes.edu.sha1;

import static java.lang.Integer.MAX_VALUE;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TheCreator {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private ConcurrentLinkedQueue<ResultData> queue = new ConcurrentLinkedQueue<>();
    private AtomicBoolean filesReaderFinish = new AtomicBoolean(false);
    private Path csvFilePath = Paths.get("sha1.csv");
    private TheCalculator calculator = new TheSHA1Calculator();
    private final Logger logger = LogManager.getLogger();
    private final Logger loggerFile = LogManager.getLogger("FileWithError");
    
    public static TheCreator build() {
        return new TheCreator();
    }
    
    private TheCreator() {};
      
    public long createSha1FromPath(Path theDirectory) throws IOException {
        logger.debug("TheCreator 'createSha1FromPath' started");
        
        if (!Files.isDirectory(theDirectory, NOFOLLOW_LINKS)) {
            throw new IOException("not a directory (" + theDirectory + ")");
        }
        final FutureTask<Long> command = new FutureTask<Long>(new CsvFileWriter());
        executorService.execute(command);
        
        long count = Files.find(theDirectory, MAX_VALUE, (path, basicFileAttributes) -> basicFileAttributes.isRegularFile())
                        .parallel()
                        .peek(p -> handleFile(p))
                        .count();
        
        filesReaderFinish.set(true);
        logger.debug("Reader finished");
        logger.info("read {} files.", count);
        try {
            logger.info("write {} SHA-1 files.", command.get());
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
        executorService.shutdown();
        logger.debug("TheCreator 'createSha1FromPath' finished");
        return count;
    }

    /*package*/void handleFile(Path theFile) {

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
        csvFilePath = theFile;
        return this;
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
                        writer.write(String.format("%s;%s%n", data.getPath(), data.getValue()));
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
