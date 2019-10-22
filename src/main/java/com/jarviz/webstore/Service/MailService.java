package com.jarviz.webstore.Service;

import com.jarviz.webstore.Models.User;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@PropertySource("classpath:application.properties")
@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender javaMailSender;
    private Environment env;


    @Synchronized
    public void send(User user) throws MessagingException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        Path path = Paths.get(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
                + File.separator + "resources" + File.separator + "static" + File.separator + "logo.png");
        MultipartFile file = new MockMultipartFile("logo.png",
                "logo.png", "text/plain", Files.readAllBytes(path));
        mimeMessage.setFrom(new InternetAddress(Objects.requireNonNull(env.getProperty("spring.mail.username"))));
        helper.setTo(user.getEmail());
        helper.setSubject("Your new account must be activated before it can be used:");
        helper.addAttachment(file.getOriginalFilename(), file);
        helper.setText("<h2>Hi!  " + user.getUsername() + "</h2>" +
                "<h3> Click this link to activate your new account: \n </h3> " +
                " <a target='_blank' href= http://localhost:4200/confirmedRegistration/" + user.getUsername() + ">Activate", true);
        javaMailSender.send(mimeMessage);
    }
}
