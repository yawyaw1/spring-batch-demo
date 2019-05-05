package com.spring.batch.example.config;

import com.spring.batch.example.reader.CustomItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adservio on 01/05/2019.
 */

public class FakeDataJobConfig {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;


    @Bean
    public CustomItemReader customItemReader() {
        List<String> strings = new ArrayList<>();
        strings.add("One"+"\n");
        strings.add("Two"+"\n");
        strings.add("Three"+"\n");
        strings.add("Four"+"\n");
        return new CustomItemReader(strings);
    }

    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("step1")
                .<String, String>chunk(2)
                .reader(customItemReader())
                .writer(list -> list.forEach(System.out::print))
                .build();
    }

    @Bean
    public Job jobItemReader() {
        return this.jobBuilderFactory.get("jobItemReader")
                .start(step1())
                .build();
    }






}
