package spring.batch.example.prosessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

public class CustomeItemProcessor implements ItemProcessor {

    Logger logger = LogManager.getLogger(CustomeItemProcessor.class);

    @Override
    public Object process(Object o) throws Exception {

        logger.info("processor... !");

        return null;
    }
}
