package com.spring.batch.example.config;

import com.spring.batch.example.entities.User;
import com.spring.batch.example.mapper.CustomFieldSetMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

/**
 * Created by Adservio on 05/05/2019.
 */

@Configuration
public class WriterToDBJobConfig {


    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    public ItemReader<User> userItemReader() {
        FlatFileItemReader<User> userFlatFileItemReader = new FlatFileItemReader<>();
        userFlatFileItemReader.setLinesToSkip(1);
        userFlatFileItemReader.setResource(new ClassPathResource("user.csv"));

        DefaultLineMapper<User> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames("id", "fistname", "lastname", "username");

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new CustomFieldSetMapper());
        lineMapper.afterPropertiesSet();
        userFlatFileItemReader.setLineMapper(lineMapper);
        return userFlatFileItemReader;
    }

    @Bean
    public ItemWriter<User> userItemWriter() {
        JdbcBatchItemWriter<User> userJdbcBatchItemWriter = new JdbcBatchItemWriter<>();
        userJdbcBatchItemWriter.setDataSource(dataSource);
        userJdbcBatchItemWriter.setSql("insert into user(id,fistname,lastname,username) values(:id, :fistname, :lastname, :username)");
        userJdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        userJdbcBatchItemWriter.afterPropertiesSet();
        return userJdbcBatchItemWriter;

    }

    @Bean
    public Step stepWriterDb() {
        return this.stepBuilderFactory.get("step writer db")
                .<User, User>chunk(2)
                .reader(userItemReader())
                .writer(userItemWriter())
                .build();
    }

    @Bean
    public Job jobWriterDb() {
        return this.jobBuilderFactory.get("job writer db")
                .start(stepWriterDb())
                .build();
    }

}

