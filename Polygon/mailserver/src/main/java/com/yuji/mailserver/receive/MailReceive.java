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
    public void handler(Message message, Channel channel) {

        Map obj = (Map) message.getPayload();
        MessageHeaders headers = message.getHeaders();
        //代表了 RabbitMQ 向Channel 投递的这条消息的唯一标识 ID，是一个单调递增的正整数，delivery tag 的范围仅限于 Channel
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try{
            //消息已被消费
            channel.basicAck(tag,false);
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(from);
            mail.setTo((String) obj.get("to"));
            mail.setCc((String) obj.get("cc"));
            mail.setSubject("流程提醒");
            String content = obj.get("name")+":\n"+"  流程"+obj.get("flowNo")+"已到你处，请点击进入\n"
                    +"链接";
            mail.setText(content);
            javaMailSender.send(mail);

        }catch (IOException e){
            //消费失败的消息重新放入队列中
            try {
                channel.basicNack(tag,false,true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
//
    }
}
