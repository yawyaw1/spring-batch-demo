package spring.batch.example.config;


public class BatchGlobalConfig /*implements BatchConfigurer */{

    /*@Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobExplorer jobExplorer;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Override
    public JobRepository getJobRepository() throws Exception {
        return this.jobRepository;
    }

    @Override
    public PlatformTransactionManager getTransactionManager() throws Exception {
        return this.platformTransactionManager;
    }

    @Override
    public JobLauncher getJobLauncher() throws Exception {
        return this.jobLauncher;
    }

    @Override
    public JobExplorer getJobExplorer() throws Exception {
        return this.jobExplorer;
    }


    public JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDataSource(dataSource());
        jobRepositoryFactoryBean.setTransactionManager(platformTransactionManager);
        jobRepositoryFactoryBean.afterPropertiesSet();

        return jobRepositoryFactoryBean.getObject();
    }


    public JobLauncher createJobLauncher() throws Exception {
        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepository);
        simpleJobLauncher.afterPropertiesSet();
        return simpleJobLauncher;
    }


    public JobExplorer createJobExplorer() throws Exception {
        JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
        jobExplorerFactoryBean.setDataSource(dataSource());
        jobExplorerFactoryBean.afterPropertiesSet();
        return jobExplorerFactoryBean.getObject();
    }


    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        this.jobRepository = createJobRepository();
        this.jobExplorer = createJobExplorer();
        this.jobLauncher = createJobLauncher();
    }

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
*/
}
