package com.spring.batch.example.processor;

import org.springframework.batch.item.ItemProcessor;

/**
 * Created by Adservio on 07/03/2019.
 */
public class CustomItemProcessor implements ItemProcessor {
    @Override
    public Object process(Object o) throws Exception {
        System.out.println("Item processor launched... !");
        return null;
    }
}
