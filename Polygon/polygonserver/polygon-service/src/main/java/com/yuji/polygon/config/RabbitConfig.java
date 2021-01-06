package com.yuji.polygon.config;

import com.yuji.polygon.entity.MailConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @className: RabbitConfig
 * @description: TODO
 * @author: yuji
 * @create: 2021-01-05 10:32:14
 */

@Configuration
public class RabbitConfig {

    private final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    @Autowired
    CachingConnectionFactory cachingConnectionFactory;

    @Bean
    RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        //消息发送到交换机时调用
        rabbitTemplate.setConfirmCallback( (correlationData, ack, cause) -> {
            String msgId = correlationData.getId();
            if (ack){
                logger.info(msgId+" 消息发送成功");
            }else {
               logger.info(msgId+" 消息发送失败");
            }
        }  );

        /**
         * Mandatory:交换机无法根据自身类型和路由键找到一个符合条件的队列时的处理方式
         * true: RabbitMQ会调用Basic.Return命令将消息返回给生产者,即调用returnCallback
         * false: RabbitMQ直接丢弃消息
         */
        rabbitTemplate.setMandatory(true);

        /**
         * 消息从交换机到队列失败时调用
         * message: 生产者发送的消息
         */
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routeingKey) -> {
            logger.info(message.getMessageProperties().getMessageId()+" 消息发送失败");
            //TODO 消息没有到达queue时的处理逻辑
        });

        return rabbitTemplate;
    }


    @Bean
    Queue mailQueue(){
        return new Queue(MailConstants.MAIL_QUEUE_NAME,true);
    }

    @Bean
    DirectExchange mailExchange(){
        return new DirectExchange(MailConstants.MAIL_EXCHANGE_NAME,true,false);
    }

    @Bean
    Binding bindingMailQueue(){
        return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(MailConstants.MAIL_ROUTING_KEY_NAME);
    }
}
