package spring.batch.example.entities;

import java.io.Serializable;

/**
 * Created by Adservio on 02/03/2019.
 */
public class PatientRecord implements Serializable {

    private String sourceId;
    private String firstName;
    private String middleInitial;
    private String lastname;

    public PatientRecord() {
    }

    public PatientRecord(String sourceId, String firstName, String middleInitial, String lastname) {
        this.sourceId = sourceId;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastname = lastname;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    @Override
    public String toString() {
        return "PatientRecord{" +
                "sourceId='" + sourceId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleInitial='" + middleInitial + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
