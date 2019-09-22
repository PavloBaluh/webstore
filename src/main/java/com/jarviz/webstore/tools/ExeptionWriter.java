package com.jarviz.webstore.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.servlet.ServletContext;
import java.io.*;
import java.time.LocalDateTime;

@Repository
public class ExeptionWriter {
    @Autowired
    ServletContext servletContext;

    private String passToFile = System.getProperty("user.home") + File.separator + "Desktop"  + File.separator + "proj" + File.separator + "src" + File.separator + "main" + File.separator + "java"
            + File.separator + "MVC" + File.separator + "tools" +  File.separator + "Exceptions.txt";


    public boolean write(String exception) {
        File file = new File(passToFile);
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            OutputStream outputStream = new FileOutputStream(file, true);
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write(LocalDateTime.now() + " " +  exception + "\n");
            printWriter.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public String getPassToFile() {
        return passToFile;
    }

    public void setPassToFile(String passToFile) {
        this.passToFile = passToFile;
    }
}
