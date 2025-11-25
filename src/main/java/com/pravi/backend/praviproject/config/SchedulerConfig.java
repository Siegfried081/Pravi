package com.pravi.backend.praviproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    // Não precisa de nada aqui dentro, só a anotação já habilita o @Scheduled
}
