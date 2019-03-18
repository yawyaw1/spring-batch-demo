package spring.batch.example.config;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import spring.batch.example.dlimitation.CustomDelimitedAggregator;
import spring.batch.example.dlimitation.CustomFieldExtractor;
import spring.batch.example.entities.Transaction;
import spring.batch.example.prosessor.CustomeItemProcessor;
import spring.batch.example.reader.CustomeItemReader;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableBatchProcessing
public class JobConfigBis {

    Logger logger = LogManager.getLogger(JobConfigBis.class);

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Value("input/record.csv")
    private Resource inputFile;

    @Value("file:xml/output.xml")
    private Resource outputFile;

    @Bean
    public JobRepository createJobRepository(DataSource dataSource) throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setTransactionManager(platformTransactionManager);
        jobRepositoryFactoryBean.setDataSource(dataSource);
        jobRepositoryFactoryBean.afterPropertiesSet();
        return jobRepositoryFactoryBean.getObject();
    }

    @Bean
    public Job createJob(JobBuilderFactory jobBuilderFactory, Step step) {
        logger.info("Starting job ...");
        return jobBuilderFactory.get("test-job")
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();
    }

    @Bean
    public Step createStep(StepBuilderFactory stepBuilderFactory, ItemReader<Transaction> reader, ItemProcessor<Transaction,
            Transaction> processor, ItemWriter<Transaction> writer) {
        logger.info("Starting step ...");

        return stepBuilderFactory.get("test-step").<Transaction, Transaction>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader itemReader() {

        logger.info("Starting extract data from fiel.csv ...");
        return new CustomeItemReader();
    }

    @Bean
    public ItemProcessor itemProcessor() {
        return new CustomeItemProcessor();
    }

    @Bean
    public ItemWriter<Transaction> itemWriter() {


        logger.info("Starting publishing data from fiel.csv ...");

        FlatFileItemWriter writer = new FlatFileItemWriter();
        writer.setResource(new ClassPathResource("output/record.csv"));
        BeanWrapperFieldExtractor fieldExtractor = new CustomFieldExtractor();
        fieldExtractor.setNames(new String[]{"userId", "username", "transactionDate", "amount"});
        DelimitedLineAggregator delLineAgg = new CustomDelimitedAggregator();
        delLineAgg.setDelimiter(",");
        delLineAgg.setFieldExtractor(fieldExtractor);
        writer.setLineAggregator(delLineAgg);
        return writer;


        /*
        JdbcBatchItemWriter writer = new JdbcBatchItemWriter();
        writer.setSql("INSERT INTO pojo (id, description) VALUES (:id, :description)");
        writer.setDataSource(dataSource);
        writer.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider());
        return writer;*/
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    public DataSource h2DataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:h2:mem:testdb");
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUsername("sa");
        dataSource.setPassword("sa");
        Properties properties = new Properties();
        properties.setProperty("spring.jpa.database-platform", "org.hibernate.dialect.H2Dialect");
        dataSource.setConnectionProperties(properties);
        return dataSource;
    }





    /*@Bean
    public ItemReader<Transaction> itemReader()
            throws UnexpectedInputException, ParseException {
        FlatFileItemReader<Transaction> reader = new FlatFileItemReader<Transaction>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        String[] tokens = { "username", "userid", "transactiondate", "amount" };
        tokenizer.setNames(tokens);
        reader.setResource(inputFile);
        DefaultLineMapper<Transaction> lineMapper =
                new DefaultLineMapper<Transaction>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new FieldSetMapper<Transaction>() {
            public Transaction mapFieldSet(FieldSet fieldSet) throws BindException {
                Transaction transaction = new Transaction();
                transaction.setUserId(fieldSet.readInt(0));
                transaction.setUsername(fieldSet.readString(1));
                transaction.setAmount(fieldSet.readDouble(2));
                transaction.setTransactionDate(fieldSet.readDate(3));
                return transaction;
            }
        });
        reader.setLineMapper(lineMapper);
        return reader;
    }*/




/*
    public LineMapper<Transaction> lineMapper() {
        DefaultLineMapper<Transaction> defaultLineMapper = new DefaultLineMapper<Transaction>();
        defaultLineMapper.setFieldSetMapper(new FieldSetMapper<Transaction>() {
            public Transaction mapFieldSet(FieldSet fieldSet) throws BindException {
                return new Transaction(fieldSet.readString(0), fieldSet.readInt(1), fieldSet.readDate(2), fieldSet.readDouble(3));
            }
        });
        defaultLineMapper.setLineTokenizer(new DelimitedLineTokenizer());
        return defaultLineMapper;
    }*/
}
