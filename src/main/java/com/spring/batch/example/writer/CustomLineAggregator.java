package com.spring.batch.example.writer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.batch.example.entities.User;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.batch.item.file.transform.LineAggregator;

/**
 * Created by Adservio on 08/05/2019.
 */
public class CustomLineAggregator implements LineAggregator<User> {

    ObjectMapper objectMapper=new ObjectMapper();

    @Override
    public String aggregate(User user) {
        try {
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}
