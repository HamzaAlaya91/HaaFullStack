package com.project;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.project.Controllers.ProviderController;

@SpringBootApplication
public class HaaFullApplication  extends SpringBootServletInitializer{

	public static void main(String[] args) {
		new File(ProviderController.uploadDirectory).mkdir();
		SpringApplication.run(HaaFullApplication.class, args);
	}

}
