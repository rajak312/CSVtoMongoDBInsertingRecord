package com.nt.listener;

import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JobMonitoringListener implements JobExecutionListener {
	
	private long start ,end;
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		start=System.currentTimeMillis();
		System.out.println("job is about to start @ : "+new Date());
		
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		end=System.currentTimeMillis();
		System.out.println("job completed at :: "+new Date());
		System.out.println("JOb Execution time :: "+(end-start)+"ms");
		System.out.println("Job Completion Status :: "+jobExecution.getStatus());
		
	}

}
