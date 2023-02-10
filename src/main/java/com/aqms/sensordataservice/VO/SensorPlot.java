package com.aqms.sensordataservice.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

// POJO for the sensorPlot for getting and saving the data that we are getting from the sensor Plot service
//It represents the data related to a particular sensor
//@Data: Lombok annotation that generates boilerplate code for the POJO
//@AllArgsConstructor: Lombok annotation to generate a constructor with all the fields as arguments
//@Builder: Lombok annotation to generate a builder pattern for the POJO
//@NoArgsConstructor: Lombok annotation to generate a no-arg constructor for the POJO

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SensorPlot {
	private Long sensorid;
	private int floor;
	private int room;
}
