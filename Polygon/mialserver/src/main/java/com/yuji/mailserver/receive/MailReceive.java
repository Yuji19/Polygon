package com.yuji.mailserver.receive;

import com.rabbitmq.client.Channel;
import com.yuji.polygon.entity.MailConstants;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @className: MailReceive
 * @description:
 * @author: yuji
 * @create: 2021-01-02 21:31
 **/

@Component
public class MailReceive {

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.from}")
    String from;

    @RabbitListener(queues = MailConstants.MAIL_QUEUE_NAME)
    public void handler(Message message, Channel channel) throws IOException {
        Map obj = (Map) message.getPayload();
        MessageHeaders headers = message.getHeaders();
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(from);
        mail.setTo((String) obj.get("to"));
        mail.setCc((String) obj.get("cc"));
        mail.setSubject("流程签审提醒");
        String content = obj.get("name")+":\n"+"你有一个流程"+obj.get("flowNo")+"待签审，请点击进入\n"
                +"链接";
        mail.setText(content);
        javaMailSender.send(mail);
        //消息已被消费
        channel.basicAck(tag,false);
    }
}
