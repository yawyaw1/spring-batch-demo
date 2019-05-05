package com.spring.batch.example.config;

import com.spring.batch.example.entities.User;
import com.spring.batch.example.mapper.CustomFieldSetMapper;
import com.spring.batch.example.repository.UserRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by Adservio on 05/05/2019.
 */

public class FlatFileJobConfig {


    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private UserRepository userRepository;


   @Bean
   public FlatFileItemReader<User> userFlatFileItemReader(){
       FlatFileItemReader<User> userFlatFileItemReader = new FlatFileItemReader<>();
       userFlatFileItemReader.setLinesToSkip(1);
       userFlatFileItemReader.setResource(new ClassPathResource("user.csv"));

       DefaultLineMapper<User> lineMapper = new DefaultLineMapper<>();

       DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
       tokenizer.setNames("id","fistname","lastname","username");
       tokenizer.setDelimiter(",");

       lineMapper.setLineTokenizer(tokenizer);
       lineMapper.setFieldSetMapper(new CustomFieldSetMapper());
       lineMapper.afterPropertiesSet();

       userFlatFileItemReader.setLineMapper(lineMapper);

       return userFlatFileItemReader;
   }

    @Bean
    public ItemWriter<User> userItemWriter(){
        return users->users.forEach(System.out::print);
    }

    @Bean
    public Step stepFlatFile(){
        return this.stepBuilderFactory.get("Step flat file")
                .<User,User>chunk(2)
                .reader(userFlatFileItemReader())
                .writer(userItemWriter())
                .build();
    }

    @Bean
    public Job job(){
        return this.jobBuilderFactory.get("Job Flat file resource aware")
                .start(stepFlatFile())
                .build();
    }



}
