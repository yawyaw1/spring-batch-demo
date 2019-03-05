package spring.batch.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StringUtils;
import spring.batch.example.entities.PatientRecord;
import spring.batch.example.enums.ProjectConstants;
import spring.batch.example.exception.JobParametersInvalideException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Adservio on 26/02/2019.
 */

@Configuration
public class JobConfig {


    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private ApplicationProperties applicationProperties;

    @Bean
    JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;

    }

    public Job job(Step step) throws Exception {
        return this.jobBuilderFactory.get(ProjectConstants.JOB_NAME.getValue())
                .validator(validator())
                .start(step)
                .build();
    }

    @Bean
    public Step step(ItemReader<PatientRecord> itemReader) throws Exception {
        return this.stepBuilderFactory.get(ProjectConstants.STEP_NAME.getValue())
//                .<PatientRecord, PatientRecord>chunk(2)
//                .reader(itemReader)
//                .processor(processor())
//                .writer(writer())
                .tasklet((stepContribution, chunkContext) ->
                {
                    System.out.println("Hello world !");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    private ItemProcessor<? super PatientRecord, ? extends PatientRecord> processor() {
        return null;
    }

    /*@Bean
    @StepScope
    public FlatFileItemReader<PatientRecord> reader() {
        return new FlatFileItemReaderBuilder<PatientRecord>().name(ProjectConstants.ITEM_READER_NAME.getValue())
                .resource(new FileSystemResource(Paths.get()))
                .linesToSkip(1)
                .lineMapper(lineMapper())
                .build();
    }*/

    @Bean
    public LineMapper<PatientRecord> lineMapper() {
        DefaultLineMapper<PatientRecord> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setFieldSetMapper(fieldSet -> new PatientRecord(fieldSet.readString(0), fieldSet.readString(1),
                fieldSet.readString(2), fieldSet.readString(3)));
        defaultLineMapper.setLineTokenizer(new DelimitedLineTokenizer());
        return defaultLineMapper;
    }

    @Bean
    JobParametersValidator validator() {
        return jobParameters -> {
            String filename = jobParameters.getString(ProjectConstants.JOB_PARAM_FILE_NAME.getValue());
            if (StringUtils.isEmpty(filename)) {
                throw new JobParametersInvalideException("Invalide name of file" + filename);
            }
            try {
                Path file = Paths.get(applicationProperties.getBatch()
                        .getInputPath() + File.separator + filename);
                if (Files.notExists(file) || !Files.isReadable(file)) {
                    throw new Exception("File did not exist or unreadable !");
                }

            } catch (Exception e) {
                throw new JobParametersInvalideException("The input path and the filename need to be a valide parameters");
            }
        };
    }


}
