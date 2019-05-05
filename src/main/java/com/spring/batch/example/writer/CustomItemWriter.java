package com.spring.batch.example.writer;

import com.spring.batch.example.entities.User;
import com.spring.batch.example.repository.UserRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Adservio on 07/03/2019.
 */
public class CustomItemWriter implements ItemWriter<User> {

    @Override
    public void write(List<? extends User> list) throws Exception {

        list.forEach(System.out::print);

    }
}
