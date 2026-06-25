package com.ocs.portal.svc.role.security;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Slf4j
@Service
public class EmailSender {

    @Value("${spring.mail.host}")
    private String host_prop;
    @Value("${spring.mail.password}")
    private String password_prop;

    @Value("${spring.mail.port}")
    private Integer port_prop;

    @Value("${spring.mail.username}")
    private String username_prop;

    private void sendEmail(String to, String subject, String contentUser, String content) throws MessagingException {

        // Konfigurasi SMTP server
        final String username = username_prop;
        final String password = password_prop;
        final String host = host_prop;
        final int port = port_prop;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Buat session
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Compose pesan
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));

        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);


        String personalizedHtml = template.replace("[Nama Penerima]", " " + contentUser + " ").replace("[Passwordbaru]", " [ " + content + " ] ");
        message.setContent(personalizedHtml, "text/html; charset=utf-8");

        // Kirim
        Transport.send(message);
        System.out.println("Email berhasil dikirim ke: " + to);
    }


    @Async
    public void send(String to, String subject, String contentUser, String content) throws MessagingException {
        sendEmail(to, subject, contentUser, content);
//        ExecutorService executor = Executors.newFixedThreadPool(10);
//        executor.submit(() -> {
//            log.info("Running in thread pool");
//            // background process
//            try {
//
//            } catch (MessagingException e) {
//                log.info("error thread"+e.getMessage());
//                throw new RuntimeException(e);
//            }
//
//        });
    }


    String template = """
            <!DOCTYPE html>
            <html>
            <head>
              <meta charset="UTF-8">
              <title>OCS Password</title>
              <style>
                body {
                  font-family: Arial, sans-serif;
                  background-color: #f4f4f4;
                  margin: 0;
                  padding: 0;
                }
            
                .email-container {
                  max-width: 600px;
                  margin: 30px auto;
                  background-color: #ffffff;
                  border-radius: 8px;
                  overflow: hidden;
                  box-shadow: 0 0 10px rgba(0,0,0,0.1);
                }
            
                .email-header {
                  background-color: #2b7a78;
                  color: #ffffff;
                  padding: 20px 30px;
                  text-align: center;
                }
            
                .email-body {
                  padding: 30px;
                  color: #333333;
                }
            
                .email-footer {
                  background-color: #def2f1;
                  padding: 20px 30px;
                  text-align: center;
                  font-size: 12px;
                  color: #555;
                }
            
                .btn {
                  display: inline-block;
                  background-color: #3aafa9;
                  color: white;
                  padding: 12px 20px;
                  text-decoration: none;
                  border-radius: 4px;
                  margin-top: 20px;
                }
            
                .btn:hover {
                  background-color: #2b7a78;
                }
            
                @media screen and (max-width: 600px) {
                  .email-body, .email-header, .email-footer {
                    padding: 20px !important;
                  }
                }
              </style>
            </head>
            <body>
            
              <div class="email-container">
                <div class="email-header">
                  <h2>Sinorita</h2>
                </div>
            
                <div class="email-body">
                  <h3>Halo, [Nama Penerima]</h3>
                  <p>Terima kasih telah menggunakan layanan kami. Kami ingin mengonfirmasi bahwa permintaan Anda telah diterima dan sedang dalam proses.</p>
            
                  <p>
                      Berikut adalah Password baru anda:
                      <strong><span style="display: inline-block; background: #f1f1f1; padding: 8px 12px; border-radius: 4px; font-family: monospace; user-select: all;">
                      [Passwordbaru]
                      </span></strong>,
                      mohon untuk segera ganti Password anda.
                  </p>
            
                  <p>Jika Anda memiliki pertanyaan atau membutuhkan bantuan, jangan ragu untuk menghubungi tim kami.</p>
            
                  <!--<a href="https://www.example.com" class="btn">Lihat Detail</a>-->
            
                  <p style="margin-top: 30px;">Salam hormat,</p>
                  <p><strong>Tim Layanan Pelanggan</strong><br>
                  Sinorita</p>
                </div>
            
                <div class="email-footer">
                  Email ini dikirim secara otomatis, mohon untuk tidak membalas. <br>
                  &copy; 2025 Sinorita Indonesia. All rights reserved.
                </div>
              </div>
            
            </body>
            </html>
            """;
}
