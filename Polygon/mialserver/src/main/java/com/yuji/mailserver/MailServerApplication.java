package com.yuji.mailserver;

import com.yuji.polygon.entity.MailConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @className: MailServerApplication
 * @description:
 * @author: yuji
 * @create: 2021-01-02 21:29
 **/

@SpringBootApplication
public class MailServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailServerApplication.class, args);
    }

    /**
     * 创建队列
     * @return
     */
    @Bean
    Queue queue() {
        return new Queue(MailConstants.MAIL_QUEUE_NAME);
    }

    /**
     * 创建交换机
     * @return
     */
    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(MailConstants.MAIL_EXCHANGE_NAME);
    }

    /**
     * 队列绑定交换机
     * @return
     */
    @Bean
    Binding bindingQueue(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(MailConstants.MAIL_ROUTING_KEY_NAME);
    }
}
