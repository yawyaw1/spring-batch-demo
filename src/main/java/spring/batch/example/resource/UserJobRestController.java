package spring.batch.example.resource;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.batch.example.enums.ProjectConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adservio on 26/02/2019.
 */


@RestController
@RequestMapping("/job")
public class UserJobRestController {

    private final Job job;

    private final JobLauncher jobLauncher;


    public UserJobRestController(Job job, JobLauncher jobLauncher) {
        this.job = job;
        this.jobLauncher = jobLauncher;
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<String> runJob(@PathVariable String filename) {

        Map<String, JobParameter> parameterMap = new HashMap<>();
        parameterMap.put(ProjectConstants.JOB_PARAM_FILE_NAME.getValue(), new JobParameter(filename));
        try {

            jobLauncher.run(job, new JobParameters(parameterMap));
        } catch (Exception e) {
            return new ResponseEntity<>("Failure" + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
