package com.mvalls.sidged.batch;

import java.time.LocalDateTime;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.mvalls.sidged.services.DesertorService;

@Component
public class DesertorBatch {

	private static final Logger LOGGER = LoggerFactory.getLogger(DesertorBatch.class);
	private ThreadPoolTaskScheduler taskScheduler;
	private final DesertorService desertorService;
	
	@Autowired
	public DesertorBatch(DesertorService desertorService) {
		this.desertorService = desertorService;
	}
	
	@PostConstruct
	public void postConstruct() {
		taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.initialize();
		taskScheduler.schedule(this.getTask(), new CronTrigger("0 5 0 * * ?", TimeZone.getTimeZone("GMT-3:00")));
	}
	
	private Runnable getTask() {
		return new Runnable() {
			
			@Override
			public void run() {
				long start = System.currentTimeMillis();
				LOGGER.info("Running batch at {}", LocalDateTime.now());
				DesertorBatch.this.desertorService.getDesertorsAndSendEmail();
				long end = System.currentTimeMillis();
				LOGGER.info("Batch ran in {} ms", end-start);
			}
		};
	}

}
