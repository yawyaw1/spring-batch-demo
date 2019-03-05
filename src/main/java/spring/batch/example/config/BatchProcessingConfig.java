package spring.batch.example.config;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * Created by Adservio on 25/02/2019.
 */


@Component
@EnableBatchProcessing
public class BatchProcessingConfig implements BatchConfigurer {

    private JobRepository jobRepository;
    private JobLauncher jobLauncher;
    private JobExplorer jobExplorer;


    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private DataSource dataSource;

    @Override
    public JobRepository getJobRepository() throws Exception {
        return null;
    }

    @Override
    public PlatformTransactionManager getTransactionManager() throws Exception {
        return this.platformTransactionManager;
    }

    @Override
    public JobLauncher getJobLauncher() throws Exception {
        return null;
    }

    @Override
    public JobExplorer getJobExplorer() throws Exception {
        return null;
    }

    protected JobLauncher createJobLauncher() throws Exception {
        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepository);
        simpleJobLauncher.afterPropertiesSet();

        return simpleJobLauncher;
    }

    protected JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDataSource(this.dataSource);
        jobRepositoryFactoryBean.setTransactionManager(getTransactionManager());
        jobRepositoryFactoryBean.afterPropertiesSet();

        return jobRepositoryFactoryBean.getObject();
    }

    @PostConstruct
    protected void afterPropertiesSet() throws Exception {
        this.jobRepository = createJobRepository();
        this.jobLauncher = createJobLauncher();
        JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
        jobExplorerFactoryBean.setDataSource(this.dataSource);
        jobExplorerFactoryBean.afterPropertiesSet();
        this.jobExplorer = jobExplorerFactoryBean.getObject();
    }
}
