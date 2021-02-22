package yelpProject.dto;

import lombok.Data;

@Data
public class YelpReview {

	/**
	 * Your JSON should include the reviewer’s name, avatar image URL, location, rating and review content.
	 */
	
	private String name;
	private String imageUrl;
	private String location;
	private Long rating;
	private String reviewContent;
}
