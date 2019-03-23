package spring.batch.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import spring.batch.example.processor.CustomItemProcessor;
import spring.batch.example.reader.CustomItemReader;
import spring.batch.example.writer.CustomItemWriter;

/**
 * Created by Adservio on 07/03/2019.
 */


public class InitiateBatchConfig {

    @Bean
    public ItemReader itemReader() {
        return new CustomItemReader();
    }

    @Bean
    public ItemProcessor itemProcessor() {
        return new CustomItemProcessor();
    }

    @Bean
    public ItemWriter itemWriter() {
        return new CustomItemWriter();
    }

    @Bean
    public Job job(JobBuilderFactory factory, Step step) {
        return factory.get("Job1").start(step).build();
    }

    @Bean
    public Step step(StepBuilderFactory stepBuilderFactory,
                     ItemWriter writer, ItemReader reader, ItemProcessor processor) {
        return stepBuilderFactory.get("Step1").chunk(10)
                .reader(reader).processor(processor).writer(writer).build();

    }

    /*@Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb");
        dataSource.setUsername("sa");
        dataSource.setPassword("sa");
        Properties properties = new Properties();
        properties.setProperty("spring.jpa.database-platform","org.hibernate.dialect.H2Dialect");
        dataSource.setConnectionProperties(properties);
        return dataSource;
    }*/
}
