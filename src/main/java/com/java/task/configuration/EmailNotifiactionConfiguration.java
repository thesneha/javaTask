package com.java.task.configuration;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:emailNotification.properties")
public class EmailNotifiactionConfiguration {

    @Value("${sendgrid.api.key}")
     private String sendGridAPIKey;

    @Bean
    public SendGrid getSendGrid()
    {
        return new SendGrid(sendGridAPIKey);
    }


}





