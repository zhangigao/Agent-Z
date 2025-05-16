package org.zhj.agentz.infrastructure.email;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {
    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.smtp.username}")
    private String username;

    @Value("${mail.smtp.password}")
    private String password;


    private String verificationSubject = "Agent-Z 验证码";


    private String verificationTemplate = "您的验证码是:%s 有效期10分钟，请勿泄露给他人";

    public void sendVerificationCode(String to, String code) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(verificationSubject);
            message.setText(String.format(verificationTemplate, code));

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("发送邮件失败: " + e.getMessage(), e);
        }
    }
}