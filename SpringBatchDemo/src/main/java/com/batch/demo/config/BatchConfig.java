package com.batch.demo.config;

import com.batch.demo.data.Car;
import com.batch.demo.data.DummyCar;
import com.batch.demo.listener.JobCompletionListener;
import com.batch.demo.service.Processor;
import com.batch.demo.service.Reader;
import com.batch.demo.service.Writer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Slf4j
@Configuration
public class BatchConfig {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private Processor processor;

    private Writer writer;

    @Bean
    public Job processJob() {
        return jobBuilderFactory.get("processJob")
                .incrementer(new RunIdIncrementer()).listener(listener())
                .flow(orderStep1()).end().build();
    }

    @Bean
    public Step orderStep1() {
        return stepBuilderFactory.get("orderStep1").<Car, DummyCar> chunk(1)
                .reader(getReader(0, 0)).processor(processor)
                .writer(writer).taskExecutor(taskExecutor()).build();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobCompletionListener();
    }

    @Bean
    @StepScope
    public Reader getReader(@Value("#{jobParameters[count]}") final int count,
                            @Value("#{jobParameters[start]}") final int start) {
        return new Reader(count, start);
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        log.info(" max pool size :::" + String.valueOf(taskExecutor.getMaxPoolSize()));
        log.info(" core pool size :::" + String.valueOf(taskExecutor.getCorePoolSize()));
        taskExecutor.setCorePoolSize(100);
        return taskExecutor;
    }

    @Autowired
    public BatchConfig(JobBuilderFactory jobBuilderFactory,
                       StepBuilderFactory stepBuilderFactory,
                       Processor processor,
                       Writer writer) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.processor = processor;
        this.writer = writer;
    }
}
