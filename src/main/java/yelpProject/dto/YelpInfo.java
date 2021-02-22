package yelpProject.dto;

import java.util.List;

import lombok.Data;

@Data
public class YelpInfo {

	private LocationInfo locationInfo;
	private List<YelpReview> yelpReviews;
}
