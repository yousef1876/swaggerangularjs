package com.app.reference.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.app.reference.api.bean.RegisterBean;
import com.app.reference.api.bean.XmlGate;




@RestController
@RequestMapping(value = "/register")
public class RegisterController {
	 @PostMapping(value = "/registration" )
	    public ResponseEntity<XmlGate> registration(@RequestBody RegisterBean body) throws InstantiationException, IllegalAccessException, JAXBException, XMLStreamException
	    {
	    	String postData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
	    			+"\n"+"<xml_gate><auth><login>admin@admin.com</login><password>sql</password><one_time>true</one_time></auth><command>REGISTER</command><arguments><login>"+body.getMobile()+"</login><verification_code>"+body.getCode()+"</verification_code></arguments></xml_gate>";
	        RestTemplate rt = new RestTemplate();
	        RestTemplate restTemplate =  new RestTemplate();
	        //Create a list for the message converters
	        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
	        //Add the String Message converter
	        messageConverters.add(new StringHttpMessageConverter());
	        messageConverters.add(new Jaxb2RootElementHttpMessageConverter()); 
	        
	        //Add the message converters to the restTemplate
	        restTemplate.setMessageConverters(messageConverters);


	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(org.springframework.http.MediaType.TEXT_XML);;
	        HttpEntity<String> request = new HttpEntity<String>(postData, headers);
	   
	        String response = restTemplate.postForEntity("http://94.127.209.167:8888/prbt_xgate/xml_gate", request, String.class).getBody();
	        /*XmlGate bean = parseXml(response,XmlGate.class.newInstance());*/
	        List<String> arr = toknize(response , "\n\t");
	        XmlGate gate = new XmlGate();
	        String sr = null;
	        for(String s : arr){
	       	 sr +=" "+s; 
	        }
	        
	        /*RegistrationModel model = new RegistrationModel();
	        
	        model.setId(System.currentTimeMillis());
	        model.setMobileNumber(body.getMobile());
	        model.setValue(body.getCode());
	        model.setResponse(sr);
	        registrationRepository.save(model);*/
	 		 
	        
	        if(!arr.isEmpty()){
	       	 gate.setStatus(arr.get(2));
				 gate.setCode(arr.get(3));
				 gate.setDescription(arr.get(4));
				 return new ResponseEntity<>(gate,HttpStatus.OK);
	       	 /*String first = arr.get(0);
	       	 String second = arr.get(1);
	       	 
	       	 if(!second.equalsIgnoreCase("")&&second !=null){
	       		 List<String> list = toknize(second , "\n\t");
	       		 
	       		 if(!list.isEmpty()){
	       			 gate.setStatus(list.get(0));
	       			 gate.setCode(list.get(1));
	       			 gate.setDescription(list.get(2));
	       			 return new ResponseEntity<>(gate,HttpStatus.OK);
	       		 } 		 
	       	 }*/
	      
	        }
	        
	        
	        
	        return new ResponseEntity<>(gate,HttpStatus.OK);
	    }
	 
	 private List<String> toknize(String response , String split){
	   	 
	   	 StringTokenizer tok = new StringTokenizer(response,split);
	        String first = null;
	        String second = null;
	        List<String> strs = new ArrayList<>();
	        
	        while(tok.hasMoreTokens()){
	        	 
	        strs.add(tok.nextToken()); 	 
	       	 
	        }
	        return strs;
	    }
}
