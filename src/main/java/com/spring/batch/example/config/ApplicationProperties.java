package com.spring.batch.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Adservio on 03/03/2019.
 */

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Batch batch=new Batch();

    public Batch getBatch() {
        return batch;
    }

    public static class Batch{
        private String inputPath="c:/platFile/patient-demo/test.csv";

        public String getInputPath() {
            return inputPath;
        }

        public void setInputPath(String inputPath) {
            this.inputPath = inputPath;
        }
    }
}
