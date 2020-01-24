package com.mvalls.sidged.test;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mvalls.sidged.App;
import com.mvalls.sidged.config.DatabaseConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {App.class, DatabaseConfig.class})
@TestPropertySource(locations = {"classpath:application.properties"})
@DirtiesContext
@Ignore
public class BaseTest {

}
