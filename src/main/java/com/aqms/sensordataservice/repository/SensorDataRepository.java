package com.aqms.sensordataservice.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.aqms.sensordataservice.model.SensorData;

@Repository
public class SensorDataRepository {
	
	// Autowiring the DynamoDBMapper to interact with the database
	@Autowired
	DynamoDBMapper mapper;

	// Method to save the sensor data in the database
	public SensorData save(SensorData sensorData) {
		// Saving the sensor data in the database
		mapper.save(sensorData);
		
		// Returning the saved sensor data
		return sensorData;
	}

	// Method to fetch all the sensor data from the database
	public List<SensorData> findAll() {
		// Fetching all the sensor data from the database using scan expression
		return mapper.scan(SensorData.class, new DynamoDBScanExpression());
	}
}