package com.nt.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.nt.listener.JobMonitoringListener;
import com.nt.model.Employee;
import com.nt.processor.EmployeeInfoItemProcessor;

@EnableBatchProcessing
@Configuration
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobFactory;
	@Autowired
	private StepBuilderFactory stepFactory;
	@Autowired
	private JobMonitoringListener listener;
	@Autowired
	private EmployeeInfoItemProcessor processor;
	@Autowired
	private DataSource ds;
	
	@Bean(name="ffir")
	public FlatFileItemReader<Employee> createffir(){
		FlatFileItemReader<Employee> reader=new FlatFileItemReader<Employee>();
		reader.setResource(new ClassPathResource("employee_info.csv"));
		DefaultLineMapper<Employee> lineMapper=new DefaultLineMapper<Employee>();
		DelimitedLineTokenizer tokenizer=new DelimitedLineTokenizer();
		
		tokenizer.setDelimiter(",");
		tokenizer.setNames("empno","ename","addrs","salary");
		BeanWrapperFieldSetMapper< Employee> mapper=new BeanWrapperFieldSetMapper<>();
		mapper.setTargetType(Employee.class);
		lineMapper.setFieldSetMapper(mapper);
		lineMapper.setLineTokenizer(tokenizer);
		
		reader.setLineMapper(lineMapper);
		
		return reader;
	}
	
	@Bean(name="jciw")
	public JdbcBatchItemWriter< Employee> createjciwriter(){
		JdbcBatchItemWriter<Employee> writer=new JdbcBatchItemWriter<Employee>();
		writer.setDataSource(ds);
		writer.setSql("insert into emp_info values(:empno,:ename,:eaddrs,:salary,:grosssalary,:netsalary");
		BeanPropertyItemSqlParameterSourceProvider<Employee> sourceProvider=new BeanPropertyItemSqlParameterSourceProvider<Employee>();
		writer.setItemSqlParameterSourceProvider(sourceProvider);
		return writer;
	}
	
	@Bean(name="step1")
	public Step createStep1() {
		return stepFactory.get("step1")
				.<Employee,Employee>chunk(5)
				.reader(createffir())
				.processor(processor)
				.writer(createjciwriter())
				.build();
	}
	
	
	@Bean("job1")
	public Job createJob() {
		return jobFactory.get("job1")
				.listener(listener)
				.incrementer(new RunIdIncrementer())
				.start(createStep1())
				.build();
	}
}
