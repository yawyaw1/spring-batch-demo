package com.spring.batch.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.batch.item.ResourceAware;
import org.springframework.core.io.Resource;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by Adservio on 10/03/2019.
 */

@Data
@NoArgsConstructor @ToString
@Entity(name = "User")
@Table(name = "USER")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "FISTNAME")
    private String fistname;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "USERNAME")
    private String username;

    public User(Long id, String firstname, String lastname,String username){
        this.id=id;
        this.fistname=firstname;
        this.lastname=lastname;
        this.username=username;
    }

}
