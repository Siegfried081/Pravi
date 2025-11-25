package com.pravi.backend.praviproject.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarEmail(String para, String assunto, String mensagem) {

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(para);
        email.setSubject(assunto);
        email.setText(mensagem);

        mailSender.send(email);
    }
}
