package spring.batch.example.writer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import spring.batch.example.entities.Transaction;

import java.util.List;

public class CustomeItemWriter implements ItemWriter<Transaction> {

    Logger logger = LogManager.getLogger(CustomeItemWriter.class);

    public void write(List<? extends Transaction> list) throws Exception {
        logger.info("Write a output result !");
    }
}
