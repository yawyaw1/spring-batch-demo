package spring.batch.example.enums;

/**
 * Created by Adservio on 26/02/2019.
 */
public enum ProjectConstants {

    JOB_NAME("user-batch-loader"),
    JOB_PARAM_FILE_NAME("file_name"),
    ITEM_READER_NAME("reader_name"),
    STEP_NAME("step-name");

    private String value;

    ProjectConstants(String value){
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
