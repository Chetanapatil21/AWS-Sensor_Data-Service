package com.aqms.sensordataservice.exception;

/// ResourceNotFoundException.java

/**

Custom exception class to handle resource not found scenario in the application.
*/
public class ResourceNotFoundException extends RuntimeException {

// Default serial version id
private static final long serialVersionUID = 1L;

// Resource name, field name, and field value
private String resourceName;
private String fieldName;
private Object fieldValue;

/**

Constructor that takes in the resource name, field name, and field value,
and creates a customized error message for resource not found scenario.
@param resourceName Name of the resource
@param fieldName Name of the field
@param fieldValue Value of the field
*/
public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
super(String.format("%s not found with %s: %s", resourceName, fieldName, fieldValue));
this.resourceName = resourceName;
this.fieldName = fieldName;
this.fieldValue = fieldValue;
}
// Getters for resource name, field name, and field value
public String getResourceName() {
return resourceName;
}
public String getFieldName() {
return fieldName;
}
public Object getFieldValue() {
return fieldValue;
}
}