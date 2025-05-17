package br.com.movieflix.movieflix.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String emailApi;


    public void sendVerificationEmail(String to, int code) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Confirmação de E-mail - MovieFlix");
        helper.setFrom(emailApi);

        String htmlContent = """
            <html>
              <body style="font-family: Arial, sans-serif; background-color: #f2f2f2; padding: 20px;">
                <div style="max-width: 600px; margin: auto; background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);">
                  <h2 style="color: #333;">Bem-vindo ao MovieFlix!</h2>
                  <p style="font-size: 16px; color: #555;">Seu código de verificação é:</p>
                  <div style="text-align: center; margin: 20px 0;">
                    <span style="font-size: 28px; background-color: #007bff; color: white; padding: 12px 24px; border-radius: 6px; display: inline-block;">
                      %s
                    </span>
                  </div>
                  <p style="font-size: 14px; color: #999;">Se você não solicitou este código, apenas ignore este e-mail.</p>
                  <p style="font-size: 14px; color: #999;">Equipe MovieFlix</p>
                </div>
              </body>
            </html>
            """.formatted(code);

        helper.setText(htmlContent, true);

        javaMailSender.send(message);
    }
}
