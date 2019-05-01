package com.spring.batch.example.resource;


import com.spring.batch.example.enums.ProjectConstants;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adservio on 26/02/2019.
 */



@RestController
public class UserJobRestController {

    JobExecution jobExecution;


    @GetMapping("/{filename:.+}")
    public ResponseEntity<String> runJob(@PathVariable String filename) {

        Map<String, JobParameter> parameterMap = new HashMap<>();
        parameterMap.put(ProjectConstants.JOB_PARAM_FILE_NAME.getValue(), new JobParameter(filename));


        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
