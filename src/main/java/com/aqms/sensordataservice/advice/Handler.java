package com.aqms.sensordataservice.advice;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.aqms.sensordataservice.FileUtil;
import com.aqms.sensordataservice.controller.SensorDataController;
import com.aqms.sensordataservice.exception.ResourceNotFoundException;


//This is a RestControllerAdvice class which is used to handle exceptions that can occur during the execution of a RESTful API.
@RestControllerAdvice
public class Handler {

 // Logger instance to log the exceptions
 Logger log = FileUtil.getLogger(SensorDataController.class);

 // Exception handler for handling invalid input data
 @ResponseStatus(HttpStatus.BAD_REQUEST)
 @ExceptionHandler(MethodArgumentNotValidException.class)
 public Map<String, String> handleInvalidInput(MethodArgumentNotValidException ex) {

     // HashMap to store error messages
     Map<String, String> errorMap = new HashMap<>();

     // Iterating over all the field errors
     ex.getBindingResult().getFieldErrors().forEach(error -> {
         // Adding error message for the field
         errorMap.put(error.getField(), error.getDefaultMessage());
     });
     // Logging the error messages
     log.info("Bad Data given with the following errors:\n\n" + errorMap);

     // Return the error messages
     return errorMap;

 }

 // Exception handler for handling resource not found exception
 @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
 @ExceptionHandler(ResourceNotFoundException.class)
 public Map<String, String> handleResourceNotFoundException(ResourceNotFoundException ex) {
     // HashMap to store error message
     Map<String, String> errorMap = new HashMap<>();
     errorMap.put("errorMessage", ex.getMessage());
     // Logging the error message
     log.info("No Data found with the following errors:\n\n" + errorMap);
     // Return the error message
     return errorMap;

 }

 // Exception handler for handling internal server error from SensorPlot
 @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
 @ExceptionHandler(InternalServerError.class)
 public Map<String, String> handleInternalServerError(InternalServerError ex) {
     // HashMap to store error message
     Map<String, String> errorMap = new HashMap<>();
     errorMap.put("Error", "Occured in SensorPlot");
     // Return the error message
     return errorMap;
 }

	// Bad Request When a value cannot be contained in the required data type
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Map<String, String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("errorMessage", ex.getMessage());
		log.info("Not Readable Exception with the following errors:\n\n" + errorMap);
		return errorMap;
	}

	// Generic Exception Handler
	
	
	  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	  @ExceptionHandler(Exception.class) public String
	  genericExceptionHandler(Exception ex) { log.info("Generic Exception"); return
	  "Generic Exception: Request Failed"; }
	 

}
