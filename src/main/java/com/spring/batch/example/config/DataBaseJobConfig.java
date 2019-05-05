package com.spring.batch.example.config;

import com.spring.batch.example.entities.User;
import com.spring.batch.example.mapper.CustomRowMapper;
import com.spring.batch.example.reader.CustomItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adservio on 04/05/2019.
 */

public class DataBaseJobConfig {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private DataSource dataSource;

    /*@Bean
    public ItemReader<User> itemReader() {
        JdbcCursorItemReader jdbcCursorItemReader = new JdbcCursorItemReader();
        jdbcCursorItemReader.setDataSource(dataSource);
        jdbcCursorItemReader.setSql("select id, fistname, lastname, username from user order by id, fistname, lastname, username");
        jdbcCursorItemReader.setRowMapper(new CustomRowMapper());
        return jdbcCursorItemReader;
    }*/


    @Bean
    public ItemReader<User> pagingItemReader() {
        JdbcPagingItemReader jdbcPagingItemReader = new JdbcPagingItemReader();
        jdbcPagingItemReader.setDataSource(dataSource);
        jdbcPagingItemReader.setFetchSize(2);
        jdbcPagingItemReader.setRowMapper(new CustomRowMapper());

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("select id, fistname, lastname, username");
        queryProvider.setFromClause("from user");

        Map<String, Order> stringIntegerMap = new HashMap<>(2);
        stringIntegerMap.put("id", Order.ASCENDING);

        queryProvider.setSortKeys(stringIntegerMap);

        jdbcPagingItemReader.setQueryProvider(queryProvider);

        return jdbcPagingItemReader;
    }

    @Bean
    public ItemWriter<User> itemWriter() {
        return data -> data.forEach(System.out::print);
    }

    @Bean
    public Step stepDataBase() {
        return this.stepBuilderFactory.get("StepDataBase")
                .<User, User>chunk(2)
                .reader(pagingItemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job jobDataBase() {
        return this.jobBuilderFactory.get("JobDataBaseJDBCPagination")
                .start(stepDataBase())
                .build();
    }
}
