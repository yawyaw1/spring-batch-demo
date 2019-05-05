package com.spring.batch.example.config;

import com.spring.batch.example.entities.User;
import com.spring.batch.example.writer.CustomItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Adservio on 05/05/2019.
 */

public class FakeItemWriterJobConfig {


    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public ListItemReader<User> userListItemReader() {
        List<User> users = new ArrayList<>();

        for (Long i = 0L; i < 1000; i++) {
            users.add(new User(i, "", "", ""));
        }
        return new ListItemReader<>(users);
    }

    @Bean
    public ItemWriter<User> userItemWriter() {
        return new CustomItemWriter();
    }

    @Bean
    public Step stepWriter() {
        return this.stepBuilderFactory.get("Step writer")
                .<User, User>chunk(10)
                .reader(userListItemReader())
                .writer(userItemWriter())
                .build();
    }

    @Bean
    public Job jobWriter(){

        return this.jobBuilderFactory.get("Job writer")
                .start(stepWriter())
                .build();
    }
}
