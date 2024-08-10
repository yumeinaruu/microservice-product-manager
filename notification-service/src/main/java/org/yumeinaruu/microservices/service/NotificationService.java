package org.yumeinaruu.microservices.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.yumeinaruu.microservices.order.event.OrderPlacedEvent;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    @Value("${spring.mail.username}")
    private String fromEmail;

    private String[] cc = {"stas.lisavoy@icloud.com"};
    private final JavaMailSender mailSender;

    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent) {
        log.info("Got message from kafka topic: {}", orderPlacedEvent);
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(fromEmail);
            helper.setTo("stas.lisavoy@icloud.com");
            helper.setCc(cc);
            helper.setSubject("test");
            helper.setText(orderPlacedEvent.toString());
            mailSender.send(mimeMessage);
            log.info("message successfully sent");
        } catch (Exception e) {
            log.info("message not sent");
            log.error(e.toString());
        }
    }
}

