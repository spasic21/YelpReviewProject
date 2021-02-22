package yelpProject.services;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import yelpProject.dto.LocationInfo;
import yelpProject.dto.YelpInfo;
import yelpProject.dto.YelpReview;

@Component
public class YelpService {
	
	public YelpInfo getBusinessReviews(String locationResponse, String reviewResponse) throws ParseException {
		YelpInfo yelpInfo = new YelpInfo();
		
		yelpInfo.setLocationInfo(getLocationInfo(locationResponse));
		yelpInfo.setYelpReviews(getReviews(reviewResponse));
		
		return yelpInfo;
	}
	
	private LocationInfo getLocationInfo(String locationResponse) throws ParseException {
		LocationInfo locationInfo = new LocationInfo();
		JSONParser locationParser = new JSONParser();
		JSONObject locationObject = (JSONObject) locationParser.parse(locationResponse);
		JSONObject locationAddress = (JSONObject) locationObject.get("location");
		
		locationInfo.setLocationName(String.valueOf(locationObject.get("name")));
		locationInfo.setAddress(String.valueOf(locationAddress.get("address1")));
		locationInfo.setCity(String.valueOf(locationAddress.get("city")));
		locationInfo.setState(String.valueOf(locationAddress.get("state")));
		locationInfo.setZipcode(String.valueOf(locationAddress.get("zip_code")));
		locationInfo.setCountry(String.valueOf(locationAddress.get("country")));
		
		return locationInfo;
	}
	
	private List<YelpReview> getReviews(String reviewResponse) throws ParseException{
		List<YelpReview> yelpReviewList = new ArrayList<>();
		
		JSONParser reviewParser = new JSONParser();
		JSONObject reviewObject = (JSONObject) reviewParser.parse(reviewResponse);
		JSONArray yelpReviews = (JSONArray) reviewObject.get("reviews");
		
		for(Object object : yelpReviews) {
			JSONObject o = (JSONObject) object;
			YelpReview yelpReview = new YelpReview();
			JSONObject userInfo = (JSONObject) o.get("user");
			
			yelpReview.setName(String.valueOf(userInfo.get("name")));
			yelpReview.setImageUrl(String.valueOf(userInfo.get("image_url")));
			yelpReview.setRating(Long.parseLong((String.valueOf(o.get("rating")))));
			yelpReview.setReviewContent(String.valueOf(o.get("text")));
		
			yelpReviewList.add(yelpReview);
		}
		
		return yelpReviewList;
	}
}
