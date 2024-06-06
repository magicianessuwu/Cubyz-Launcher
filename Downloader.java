import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Downloader {

    public static void downloadAndUnzip(String urlStr, String destDir, JTextArea outputConsole, JProgressBar progressBar, JLabel fetchingLabel) throws IOException {
        // Ensure the destination directory exists
        Files.createDirectories(Paths.get(destDir));

        // Download the zip file
        URL url = new URL(urlStr);
        InputStream inputStream = url.openStream();
        String zipFilePath = destDir + "master.zip";
        FileOutputStream outputStream = new FileOutputStream(zipFilePath);

        outputConsole.append("Downloading...\n");
        progressBar.setValue(0);
        fetchingLabel.setText("Waiting");

        byte[] buffer = new byte[4096];
        int bytesRead;
        int totalBytesRead = 0;
        int contentLength = url.openConnection().getContentLength();

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
            totalBytesRead += bytesRead;
            int progress = (int) ((totalBytesRead / (double) contentLength) * 100);
            progressBar.setValue(progress);
            fetchingLabel.setText("Downloading");
        }

        inputStream.close();
        outputStream.close();

        SwingUtilities.invokeLater(() -> {
            outputConsole.append("Download complete.\nExtracting..\n");
        });

        fetchingLabel.setText("Waiting");

        unzip(zipFilePath, destDir, outputConsole, progressBar, fetchingLabel);

        fetchingLabel.setText("Ready");
        outputConsole.append("Extraction complete.\n");
        progressBar.setValue(0);
    }

    private static void unzip(String zipFilePath, String destDir, JTextArea outputConsole, JProgressBar progressBar, JLabel fetchingLabel) throws IOException {
        File destDirectory = new File(destDir);
        if (!destDirectory.exists()) {
            destDirectory.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        long totalBytes = 0;
        while (entry != null) {
            totalBytes += entry.getSize();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
        
        zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        entry = zipIn.getNextEntry();
        long extractedBytes = 0;
        while (entry != null) {
            String filePath = destDir + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                extractFile(zipIn, filePath);
            } else {
                File dir = new File(filePath);
                dir.mkdirs();
            }
            extractedBytes += entry.getSize();
            int progress = (int) ((extractedBytes / (double) totalBytes) * 100);
            progressBar.setValue(progress);
            fetchingLabel.setText("Extracting");
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}
