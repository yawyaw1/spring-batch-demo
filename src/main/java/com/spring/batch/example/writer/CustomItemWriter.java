package com.spring.batch.example.writer;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * Created by Adservio on 07/03/2019.
 */
public class CustomItemWriter implements ItemWriter {
    @Override
    public void write(List list) throws Exception {
        System.out.println("Item writer launched... !");

    }
}
