package org.cyk.springcloudstreammq;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Random;
import java.util.function.Supplier;

@SpringBootApplication
public class SpringCloudStreamMqApplication {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private static final String[] data = new String[] {
            "abc1", "abc2", "abc3",
            "abc4",
    };

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudStreamMqApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    /**
     * 分区消息:
     * 方法返回一个函数，这个函数每次调用都会从 data 中随机选择一个字符串，
     * 生成一个带有分区键(partitionKey)的消息，并将这个消息返回.
     */
    @Bean
    public Supplier<Message<?>> generate() {
        return () -> {
            String value = data[RANDOM.nextInt(data.length)];
            System.out.println("Sending: " + value);
            return MessageBuilder.withPayload(value)
                    .setHeader("partitionKey", value)
                    .build();
        };
    }

}
