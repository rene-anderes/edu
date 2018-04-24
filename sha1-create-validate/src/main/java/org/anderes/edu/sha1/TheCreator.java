package org.anderes.edu.sha1;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import static java.time.format.DateTimeFormatter.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class TheCreator {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private ConcurrentLinkedQueue<ResultData> queue = new ConcurrentLinkedQueue<>();
    private AtomicBoolean filesReaderFinish = new AtomicBoolean(false);
    private OutputStream infoLog = System.out;
    private Path errorLogPath = Paths.get("error.log");
    
    public static TheCreator build() {
        return new TheCreator();
    }
    
    private TheCreator() {};
    
    public ResultData createSha1(Path theFile) throws FileNotFoundException, IOException {

        try (InputStream input = new FileInputStream(theFile.toFile())) {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");

            byte[] buffer = new byte[8192];
            int len = input.read(buffer);

            while (len != -1) {
                sha1.update(buffer, 0, len);
                len = input.read(buffer);
            }

            final String marshal = new HexBinaryAdapter().marshal(sha1.digest());
            return new ResultData(theFile, marshal);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
            System.exit(99);
            return null;
        } 
    }

    public long createSha1FromPath(Path theDirectory) throws IOException {
        logInfo(infoLog, "Programm started");
        
        if (!Files.isDirectory(theDirectory, NOFOLLOW_LINKS)) {
            throw new IOException("not a directory (" + theDirectory + ")");
        }
        final FutureTask<Long> command = new FutureTask<Long>(new CsvFileWriter());
        executorService.execute(command);
        
        long count = Files.find(theDirectory, Integer.MAX_VALUE, (path, basicFileAttributes) -> basicFileAttributes.isRegularFile())
                        .parallel()
                        .peek(p -> handleFile(p))
                        .count();
        
        filesReaderFinish.set(true);
        logInfo(infoLog, "Reader finished");
        try {
            logInfo(infoLog, String.format("Writer write %s SHA-1 files", command.get()));
        } catch (InterruptedException | ExecutionException e) {
            System.err.println(e.getMessage());
        }
        executorService.shutdown();
        logInfo(infoLog, "Programm finished");
        return count;
    }

    /*package*/void handleFile(Path theFile) {

        try {
            queue.add(createSha1(theFile));
        } catch (IOException e) {
            final String errorMsg = String.format("File: '%s', error-message: %s", theFile, e.getMessage());
            logError(errorLogPath, errorMsg);
        }
        
    }

    /*package*/long queueSize() {
        return queue.size();
    }
    
    /*package*/void logInfo(final OutputStream out, final String message) {
        final String msg = String.format("%s | %s", dateTimeNow(), message);
        try {
            out.write(msg.getBytes());
            out.write(System.lineSeparator().getBytes());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /*package*/void logError(final Path logFile, final String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile.toFile(), true))) {
            final String msg = String.format("%s | %s", dateTimeNow(), message);
            writer.write(msg);
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    private String dateTimeNow() {
        return LocalDateTime.now().format(ISO_DATE_TIME);
    }
    
    public void setErrorLogFile(final Path logFile) {
        errorLogPath = logFile;
    }

    private class CsvFileWriter implements Callable<Long> {
        
        private File filePath = Paths.get("target", "sha1.csv").toFile();

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
                logError(errorLogPath, e.getMessage());
            }
            logInfo(infoLog, "Writer finished");
            return c;
        }
    }
}
