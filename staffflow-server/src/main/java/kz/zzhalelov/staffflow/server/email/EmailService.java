package kz.zzhalelov.staffflow.server.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendContractSignedEmail(String to, String employeeName, Long contractId) {
        String subject = "Трудовой договор подписан";
        String text = String.format("""
                Здравствуйте, %s!
                
                Ваш трудовой договор (ID: %d) успешно подписан.
                Добро пожаловать в команду!
                
                С уважением, HR-отдел.
                """, employeeName, contractId);

        sendEmail(to, subject, text);
    }

    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, false);
            mailSender.send(message);
        } catch (MessagingException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}