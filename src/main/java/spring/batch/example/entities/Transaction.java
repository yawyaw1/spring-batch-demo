package spring.batch.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@Data
@NoArgsConstructor @AllArgsConstructor @ToString
public class Transaction {

    private String userId;
    private String username;
    private String transactionDate;
    private String amount;

}
