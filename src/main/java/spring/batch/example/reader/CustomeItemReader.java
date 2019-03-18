package spring.batch.example.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import spring.batch.example.entities.Transaction;

public class CustomeItemReader implements ItemReader<Transaction> {
    Logger logger = LogManager.getLogger(CustomeItemReader.class);

    @Override
    public Transaction read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        logger.info("Read file .... !");

        // flat file item reader (using an csv extractor)
        FlatFileItemReader reader = new FlatFileItemReader();
        //setting resource and line mapper
        reader.setResource(new ClassPathResource("input/record.csv"));
        reader.setLineMapper(new DefaultLineMapper() {
            {
                //default line mapper with a line tokenizer and a field mapper
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[]{"userId", "username","transactionDate","amount"});
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper() {
                    {
                        setTargetType(Transaction.class);
                    }
                });
            }
        });
        return null;
    }
}
