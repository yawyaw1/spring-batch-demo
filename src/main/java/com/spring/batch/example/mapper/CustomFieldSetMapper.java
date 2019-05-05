package com.spring.batch.example.mapper;

import com.spring.batch.example.entities.User;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * Created by Adservio on 05/05/2019.
 */
public class CustomFieldSetMapper implements FieldSetMapper<User> {

    @Override
    public User mapFieldSet(FieldSet fieldSet) throws BindException {
        return new User(fieldSet.readLong("id"),
                fieldSet.readString("fistname"),
                fieldSet.readString("lastname"),
                fieldSet.readString("username"));
    }
}
