package com.jarviz.webstore.Exceptions;
import org.springframework.stereotype.Repository;
import java.io.*;
import java.time.LocalDateTime;

@Repository
public class ExceptionWriter {
    private String passToFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java"
            + File.separator + "com" + File.separator + "jarviz" + File.separator + "webstore" + File.separator + "Exceptions" +  File.separator + "Exceptions.txt";


    public void write(String exception) throws IOException {
        File file = new File(passToFile);
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream outputStream = new FileOutputStream(file, true);
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write(LocalDateTime.now() + " " + exception + "\n");
            printWriter.close();
    }


    public String getPassToFile() {
        return passToFile;
    }

    public void setPassToFile(String passToFile) {
        this.passToFile = passToFile;
    }
}
