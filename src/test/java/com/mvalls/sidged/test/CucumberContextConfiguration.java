package com.mvalls.sidged.test;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import cucumber.api.java.Before;
	
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
public class CucumberContextConfiguration  {

    @Before
    public void setup_cucumber_spring_context(){
        // Dummy method so cucumber will recognize this class as glue
        // and use its context configuration.
    }
} 