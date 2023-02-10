package com.aqms.sensordataservice.service;

import java.util.List;
import com.aqms.sensordataservice.model.SensorData;
/**

Interface to define the SensorDataService methods
*/
public interface SensorDataService {

/**

Method to save the sensor data
@param sensor The SensorData object to be saved
@return The saved SensorData object
*/
SensorData saveSensorData(SensorData sensor);
/**

Method to retrieve all sensor data
@return List of SensorData objects
*/
List<SensorData> getSensorDataAll();
/**

Method to add the safety information to the sensor data
@param sensor The SensorData object to which safety information is added
@return The SensorData object with added safety information
*/
SensorData addSafetyInfo(SensorData sensor);
/**

Method to notify the safety status to the subscribers
@param sensorData The SensorData object whose safety status needs to be notified
*/
void notifySafety(SensorData sensorData);
/**

Method to add the email address to the subscribers list
@param email The email address to be added as a subscriber
@return The email address added as a subscriber
*/
String addSubscribe(String email);
/**

Method to validate the sensor id
@param sensorid The sensor id to be validated
*/
void validSensor(String sensorid);
}


