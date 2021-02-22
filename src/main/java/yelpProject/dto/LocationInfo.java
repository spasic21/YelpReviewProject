package yelpProject.dto;

import lombok.Data;

@Data
public class LocationInfo {

	private String locationName;
	private String address;
	private String city;
	private String state;
	private String zipcode;
	private String country;
}
