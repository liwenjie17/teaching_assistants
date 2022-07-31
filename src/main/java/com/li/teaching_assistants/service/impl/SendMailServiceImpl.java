package com.li.teaching_assistants.service.impl;

import com.li.teaching_assistants.service.SendMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class SendMailServiceImpl implements SendMailService {
    @Autowired
    JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    @Override
    public boolean sendMail(String email, String code) {
        try {
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            //邮箱发送方
            messageHelper.setFrom(from);
            //主题
            messageHelper.setSubject("智慧教辅平台邮箱验证");
            //接收方
            messageHelper.setTo(email);
            //邮件内容
            messageHelper.setText("【智慧教辅平台】你的验证码为："+code+"，有效时间为5分钟。（若不是本人操作，可忽略该条邮件)");
            //发送
            this.mailSender.send(mimeMessage);
            log.error(email+"发送邮件成功，验证码为"+code);

        } catch (MessagingException e) {
            log.error(email+"发送邮件失败，请检查邮箱是否正确");
            return false;
        }

        return true;
    }
}
