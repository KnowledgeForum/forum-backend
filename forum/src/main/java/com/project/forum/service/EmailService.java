package com.project.forum.service;

import com.project.forum.exception.CustomException;
import com.project.forum.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendEmail(String toEmail, String title, String text) {
        SimpleMailMessage message = createEmailForm(toEmail, title, text);

        try {
            javaMailSender.send(message);
        } catch (RuntimeException e) {
            log.debug("EmailService.sendEmail exception occur toEmail : {}, title: {}, text : {}", toEmail, title, text);
            throw new CustomException(ErrorCode.ERROR_EMAIL_SENDER);
        }

    }

    // 발신 이메일 데이터 세팅
    private SimpleMailMessage createEmailForm(String toEmail, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);

        return message;
    }

}
