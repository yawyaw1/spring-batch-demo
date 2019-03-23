package spring.batch.example.resource;


import org.springframework.batch.core.JobParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import spring.batch.example.enums.ProjectConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adservio on 26/02/2019.
 */



public class UserJobRestController {


    @GetMapping("/{filename:.+}")
    public ResponseEntity<String> runJob(@PathVariable String filename) {

        Map<String, JobParameter> parameterMap = new HashMap<>();
        parameterMap.put(ProjectConstants.JOB_PARAM_FILE_NAME.getValue(), new JobParameter(filename));


        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
