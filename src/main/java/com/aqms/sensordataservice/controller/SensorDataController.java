package com.aqms.sensordataservice.controller;
//Importing required libraries

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aqms.sensordataservice.FileUtil;
import com.aqms.sensordataservice.model.SensorData;
import com.aqms.sensordataservice.service.SensorDataService;

import jakarta.validation.Valid;

//Annotation to define this class as a REST controller
@RestController
//Defining the base mapping for the endpoints in this class
@RequestMapping("/sensorData")
public class SensorDataController {

	// Controller level logger instance
	Logger logger = FileUtil.getLogger(SensorDataController.class);

	// Instance of SensorDataService
	private SensorDataService sensorDataservice;

	// Constructor to initialize the SensorDataService instance
	public SensorDataController(SensorDataService sensorDataservice) {
		super();
		this.sensorDataservice = sensorDataservice;
	}

	// Endpoint to post the sensor data
	@PostMapping("post")
	// Indicating the return type is a response body
	@ResponseBody
	public SensorData saveSensorData(@RequestBody @Valid SensorData sensorData) {
		// Logging the action
		logger.info("Verifying the Sensor ID is valid or not");
		
		// Calling the method to validate the sensor ID
		sensorDataservice.validSensor(sensorData.getSensorid());
		
		// Logging the action
		logger.info("Verified the Sensor ID is valid");
		
		// Logging the action
		logger.info("Sending data that is posted for safety calculation");
		
		// Calling the method to calculate safety information
		sensorData = sensorDataservice.addSafetyInfo(sensorData);
		
		// Logging the action
		logger.info("Calculation Completed");
		
		// Logging the action and the data
		logger.info("SensorData is being saved with data:\n" + sensorData);
		
		// Checking the safety information
		if(!sensorData.getSafety()) {
			
			// Logging the action
			logger.info("Sending SMS");
			
			// Calling the method to notify safety information
			sensorDataservice.notifySafety(sensorData);
		}
		
		// Calling the method to save the sensor data
		return sensorDataservice.saveSensorData(sensorData);
	}

	// Endpoint to add email subscription
	@GetMapping("/sub/{email}")
	// Indicating the return type is a response body
	@ResponseBody
	public String addSub(@PathVariable String email) {
		logger.info("Requesting Subscription for "+ email);
		return sensorDataservice.addSubscribe(email);

	}

	// Getting all the data from the stream
	@GetMapping("/all")
	@ResponseBody
	public List<SensorData> getSensorDataAllControl() {
		logger.info("Getting all sensorData data");
		return sensorDataservice.getSensorDataAll();
	}

}
