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
        User user = new User();
        user.setId(fieldSet.readLong("id"));
        user.setFistname(fieldSet.readString("fistname"));
        user.setLastname(fieldSet.readString("lastname"));
        user.setUsername(fieldSet.readString("username"));
        return user;
    }
}
