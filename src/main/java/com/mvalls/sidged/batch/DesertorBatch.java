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

import com.mvalls.sidged.core.services.DesertorService;

/**
 * 
 * @author Marcelo Valls
 * 
* This file is part of SIDGED-Backend.
* 
* SIDGED-Backend is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* SIDGED-Backend is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with SIDGED-Backend.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
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
