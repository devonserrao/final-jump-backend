package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import io.swagger.annotations.ApiModelProperty;

//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Makes sure our data loads fast enough without getting error of unable to serialize fast enough!
@Entity
public class Restaurant implements Serializable {
	
	 private static final long serialVersionUID = 1L;
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @ApiModelProperty(notes = "The id the database will generate using auto incrementing for the restaurant.")
	 private Integer id;
	
	 @NotNull
	 @ApiModelProperty(notes = "The name of the restaurant.")
	 private String name;
	
	 @NotNull
	 @ApiModelProperty(notes = "The restaurant's street address.")
	 private String address;
	
	 @NotNull
	 @ApiModelProperty(notes = "A description of the restaurant, including the type of food served and hours of operation.")
	 private String description;
	
	 @NotNull // maybe can be null if we dont want a image for restaurant
	 @ApiModelProperty(notes = "The URL of an uploaded image for the restaurant.")
	 private String imageSrc;
	
	 @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	 @ApiModelProperty(notes = "The restaurant's reviews, submitted by users.")
	 private List<Review> reviews;
	
	 public Restaurant() {
	     this(-1, "N/A", "N/A", "N/A", "N/A", new ArrayList<Review>());
	 }

	public Restaurant(Integer id, String name, String address, String description, String imageSrc, List<Review> reviews) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.imageSrc = imageSrc;
		this.reviews = reviews;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", address=" + address + ", description=" + description
				+ ", imageSrc=" + imageSrc + ", reviews=" + reviews + "]";
	}
	 
	public String toJson() {

		return "{\"id\" : " + id 
				+ ", \"name\" : \"" + name 
				+ "\"" + ", \"address\" : \"" + address 
				+ "\"" + ", \"description\" : \"" + description
				+ "\"" + ", \"imageSrc\" : \"" + imageSrc
				+ "\"" + ", \"reviews\" : " + reviews
			    + "}";
	} 

 

}