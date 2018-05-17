package com.batch.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/batch")
public class BatchController {

    private JobLauncher jobLauncher;

    private Job processJob;

    @GetMapping(value = "/start/{count}/{start}")
    public String startBatch(@PathVariable("count") long count,
                             @PathVariable("start") long start) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .addLong("count", count) // 10000
                .addLong("start", start) //31017
                .toJobParameters();
        log.info("starting job...");
        jobLauncher.run(processJob, jobParameters);
        return "Batch job has been invoked";
    }

    @Autowired
    public BatchController(JobLauncher jobLauncher,
                           Job processJob) {
        this.jobLauncher = jobLauncher;
        this.processJob = processJob;
    }
}
