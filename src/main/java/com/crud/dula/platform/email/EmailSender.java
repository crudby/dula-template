package com.crud.dula.platform.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

/**
 * @author crud
 * @date 2023/9/25 20:32
 */
@Slf4j
@Component
public class EmailSender {


    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;


    /**
     * 邮件发送
     *
     * @param emailDTO emailDTO
     */
    public void simpleEmail(EmailDTO emailDTO) {
        //创建简单邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        //谁发的
        message.setFrom(sender);
        //谁要接收
        message.setTo(emailDTO.getTos());
        //邮件标题
        message.setSubject(emailDTO.getSubject());
        //邮件内容
        message.setText(emailDTO.getContent());
        javaMailSender.send(message);
    }

    /**
     * 邮件发送
     *
     * @param emailDTO emailDTO
     */
    public void htmlEmail(EmailDTO emailDTO) {
        MimeMessage mimeMailMessage = this.javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMailMessage, true, "utf-8");
            messageHelper.setFrom(sender);
            messageHelper.setTo(emailDTO.getTos());
            messageHelper.setSubject(emailDTO.getSubject());
            messageHelper.setText(emailDTO.getContent(), true);
            javaMailSender.send(mimeMailMessage);

        } catch (javax.mail.MessagingException e) {
            log.error("邮件发送异常：", e);
        }
    }
}
