package com.spring.batch.example.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Adservio on 07/03/2019.
 */
public class CustomItemReader implements ItemReader {

    private List<String> data;

    public CustomItemReader(List<String> data) {
        this.data = data;
    }

    @Override
    public Object read() throws Exception {
        System.out.println("Item reader launched... !");
        if (!CollectionUtils.isEmpty(data)) {
            data.forEach(System.out::print);
        }
        return null;
    }
}
