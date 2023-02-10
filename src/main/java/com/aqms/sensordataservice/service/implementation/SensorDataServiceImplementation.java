package com.aqms.sensordataservice.service.implementation;

import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.aqms.sensordataservice.FileUtil;
import com.aqms.sensordataservice.VO.SensorPlot;
import com.aqms.sensordataservice.config.AppConfig;
import com.aqms.sensordataservice.model.SensorData;
import com.aqms.sensordataservice.repository.SensorDataRepository;
import com.aqms.sensordataservice.service.SensorDataService;

//Service Implementation class that implements the SensorDataService interface
@Service
public class SensorDataServiceImplementation implements SensorDataService {

	// Autowiring the SensorDataRepository to perform database operations
	private SensorDataRepository sensorDataRepository;

	// Autowiring the RestTemplate to call other microservices
	private RestTemplate restTemplate;

	// Autowiring the AppConfig to access the configuration properties
	private AppConfig appConfig;

	// Autowiring the AmazonSNSClient to use SNS service to send notifications
	private AmazonSNSClient amazonSNSClient;

	// Constructor to initialize all the required dependencies
	public SensorDataServiceImplementation(SensorDataRepository sensorDataRepository, RestTemplate restTemplate,
			AppConfig appConfig, AmazonSNSClient amazonSNSClient) {
		super();
		this.sensorDataRepository = sensorDataRepository;
		this.restTemplate = restTemplate;
		this.appConfig = appConfig;
		this.amazonSNSClient = amazonSNSClient;
	}

	// Overridden method to save the SensorData in the database
	@Override
	public SensorData saveSensorData(SensorData sensorData) {
		return sensorDataRepository.save(sensorData);
	}

	// Overridden method to get all the SensorData from the database
	@Override
	public List<SensorData> getSensorDataAll() {
		return (List<SensorData>) sensorDataRepository.findAll();
	}

	// Method to add Safety information to the SensorData
	public SensorData addSafetyInfo(SensorData sensorData) {
		// Calculating the safe value based on the gas levels in the air
		sensorData.setSafeValue((sensorData.getC().add(sensorData.getCo()).add(sensorData.getCo2())
				.add(sensorData.getSo2()).subtract(sensorData.getO2())).divide(FileUtil.divider, RoundingMode.CEILING));
		// Setting the safety based on the calculated safe value
		sensorData.setSafety(sensorData.getSafeValue().compareTo(FileUtil.threshold) == 1 ? false : true);
		return sensorData;
	}

	// Method to check whether the sensor is valid or not
	@Override
	public void validSensor(String sensorid) throws InternalServerError {
		// Calling the SensorPlot microservice to retrieve the details of the sensor
		restTemplate.getForObject("http://SENSOR-PLOT/SensorPlot/find/" + sensorid, SensorPlot.class);
	}

	// Method to send notifications if the air quality is not safe
	@Override
	public void notifySafety(SensorData sensorData) {
		// Preparing the request to send the notification
		PublishRequest publishRequest = new PublishRequest(appConfig.getSnsEndpoint(),
				safetyMessage(sensorData.getSensorid()), "Notification: Air Quality Alert");
		// Sending the notification
		amazonSNSClient.publish(publishRequest);

	}

	// Helper method to prepare the message for the notification
	private String safetyMessage(String string) {
		SensorPlot sensor = restTemplate.getForObject("http://SENSOR-PLOT/SensorPlot/find/" + string, SensorPlot.class);
		return "Dear Employee! /n This is an Alert for you,/n The Room number:" + sensor.getRoom() + "\nThe Floor:"
				+ sensor.getFloor()
				+ "\nHas Detected the air quality values being above than the threshold\nKindly follow the safety precautions";
	}
    //Method add subscriber subscription to SNS
	@Override
	public String addSubscribe(String email) {
		SubscribeRequest request = new SubscribeRequest(appConfig.getSnsEndpoint(), "email", email);
		amazonSNSClient.subscribe(request);
		return email + "  requested for subscription";
	}

}
