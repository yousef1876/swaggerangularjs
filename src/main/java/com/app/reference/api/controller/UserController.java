package com.app.reference.api.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import com.google.common.collect.ImmutableList;

import com.app.reference.api.bean.XmlGate;
import com.app.reference.api.bean.SecretCodeBean;

@RestController
@RequestMapping("/usr")
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class);

   
    
    
    @PostMapping(value = "/secret" )
    public ResponseEntity<XmlGate> secret(@RequestBody SecretCodeBean body)
    {
    	String postData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
    			+"\n"+"<xml_gate><auth><login>admin@admin.com</login><password>sql</password><one_time>true</one_time></auth><command>GET_VERIFICATION_CODE</command><arguments><login>"+body.getMobile()+"</login></arguments></xml_gate>";
        
        RestTemplate restTemplate =  new RestTemplate();
        //Create a list for the message converters
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
           /* if (converter instanceof MarshallingHttpMessageConverter) {
           	 
           	 XStreamMarshaller marshaller = new XStreamMarshaller();
        	    MarshallingHttpMessageConverter marshallingConverter = new MarshallingHttpMessageConverter(marshaller);
        	    marshallingConverter.setSupportedMediaTypes(ImmutableList.of(new MediaType("text", "xml", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)));
            }*/
            
            if (converter instanceof StringHttpMessageConverter) {
           	 
               	StringHttpMessageConverter marshallingConverter = new StringHttpMessageConverter();
        	        marshallingConverter.setSupportedMediaTypes(ImmutableList.of(new MediaType("text", "xml", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)));
            }
        }
        
        
        //Add the message converters to the restTemplate
        restTemplate.setMessageConverters(converters);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.TEXT_XML);
        HttpEntity<String> request = new HttpEntity(postData, headers);
   
        String response = restTemplate.postForEntity("http://94.127.209.167:8888/prbt_xgate/xml_gate", request, String.class).getBody();
     
        List<String> arr = toknize(response , "\n\t");
        
        String str = null;
        
        for(String s : arr){
       	 
       	 str += " "+ s;
        }
        
        /*SecretCodeModel model = new SecretCodeModel();
		 model.setValue(str);
		 model.setId(System.currentTimeMillis());
		 model.setMobileNumber(body.getMobile());
		 model.setValue(body.getMobile());
		 model.setResponse(str);
		 secCodeRepository.save(model);*/
		
		 
		
		 
		 
        XmlGate gate = new XmlGate();
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
