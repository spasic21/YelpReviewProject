package yelpProject.controllers;

import java.util.Arrays;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import yelpProject.dto.YelpInfo;
import yelpProject.services.YelpService;

@RestController
public class YelpController {
	
	 @Autowired
	 RestTemplate restTemplate;
	 
	 @Autowired
	 YelpService yelpService;
	 
	 String apiKey = "INSERT KEY HERE";
	 
	 @GetMapping("/{id}/reviews")
	 public YelpInfo getBusinesses(@PathVariable String id) throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(apiKey);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity <String> entity = new HttpEntity<String>(headers);
		String locationResponse = restTemplate.exchange("https://api.yelp.com/v3/businesses/" + id, HttpMethod.GET, entity, String.class).getBody();
		String reviewResponse =  restTemplate.exchange("https://api.yelp.com/v3/businesses/" + id + "/reviews", HttpMethod.GET, entity, String.class).getBody();
		
		return yelpService.getBusinessReviews(locationResponse, reviewResponse);
	 }
	 
	 @GetMapping("/")
	 public String HelloApplication() {
		 return "Yelp Review Application!";
	 }
}
