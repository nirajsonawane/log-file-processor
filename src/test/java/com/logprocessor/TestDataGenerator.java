package com.logprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logprocessor.model.LogMessage;


public class TestDataGenerator {
	
public static void main(String[] args) throws JsonProcessingException {
	
	ObjectMapper mapper = new ObjectMapper();
	LogMessage message = LogMessage.builder().host("Host").id("123").state("State").build();
	
	/*String jsonInString = mapper.writeValue(w, value);
	
	System.out.println(jsonInString);*/
}	
	

}
