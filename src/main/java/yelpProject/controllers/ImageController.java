package yelpProject.controllers;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature.Type;

import yelpProject.services.DetectFaces;

@RestController
public class ImageController {
	
	@Autowired 
	private ResourceLoader resourceLoader;

	@Autowired 
	private CloudVisionTemplate cloudVisionTemplate;

	  /**
	   * This method downloads an image from a URL and sends its contents to the Vision API for label
	   * detection.
	   *
	   * @param imageUrl the URL of the image
	   * @param map the model map to use
	   * @return a string with the list of labels and percentage of certainty
	   */
	@GetMapping("/extractLabels")
	public Map<String, Float> extractLabels() {
		String imageUrl = "https://s3-media1.fl.yelpcdn.com/photo/dpjr4H1jDTgPtvLhPjIOAg/o.jpg";
		
		AnnotateImageResponse response =
	    this.cloudVisionTemplate.analyzeImage(
	        this.resourceLoader.getResource(imageUrl), Type.LABEL_DETECTION);
	
		Map<String, Float> imageLabels =
				response
				.getLabelAnnotationsList()
				.stream()
				.collect(
					Collectors.toMap(
	                EntityAnnotation::getDescription,
	                EntityAnnotation::getScore,
	                (u, v) -> {
	                  throw new IllegalStateException(String.format("Duplicate key %s", u));
	            },
	            LinkedHashMap::new));
	
//		map.addAttribute("annotations", imageLabels);
//		map.addAttribute("imageUrl", imageUrl);
	
//		return new ModelAndView("result", map);
		
		return imageLabels;
	}
	
	  @GetMapping("/extractText")
	  public String extractText() {
		  String imageUrl = "https://s3-media1.fl.yelpcdn.com/photo/dpjr4H1jDTgPtvLhPjIOAg/o.jpg";
		  
		  String textFromImage =
				  this.cloudVisionTemplate.extractTextFromImage(this.resourceLoader.getResource(imageUrl));
		  return "Text from image: " + textFromImage;
	  }
	  
	  @GetMapping("/detectFaces")
	  public Map<String, String> detectFaces() throws IOException {
		  String imageUrl = "https://s3-media1.fl.yelpcdn.com/photo/dpjr4H1jDTgPtvLhPjIOAg/o.jpg";
		  
		  return DetectFaces.detectFaces(imageUrl);
	  }
}
