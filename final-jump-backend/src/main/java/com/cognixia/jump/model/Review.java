package com.cognixia.jump.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import io.swagger.annotations.ApiModelProperty;

//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Makes sure our data loads fast enough without getting error of unable to serialize fast enough!
@Entity
public class Review implements Serializable {

	 private static final long serialVersionUID = 1L;
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @ApiModelProperty(notes = "The id the database will generate using auto incrementing for the review.")
	 private Integer id;
	
	 @ManyToOne
	 @JoinColumn(name = "user_id", referencedColumnName = "id")
	 @JsonIgnoreProperties("reviews")
	 @ApiModelProperty(notes = "The user who created the review.")
	 private User user;
	
	 @ManyToOne
	 @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
	 @JsonIgnoreProperties("reviews")
	 @ApiModelProperty(notes = "The restaurant referenced in the review.")
	 private Restaurant restaurant;
	
	 @NotNull
	 @ApiModelProperty(notes = "The user's rating of the restaurant, from 1 to 5 stars.")
	 private Integer rating;
	
	 @ApiModelProperty(notes = "An optional written review of the restaurant.")
	 private String comment;
	
	 @ApiModelProperty(notes = "The date the review was created.")
	 private LocalDate createdOn;
	 
	 public Review() {
	     this(-1, 0, "N/A", LocalDate.of(2021, 1, 1), new User(), new Restaurant());
	 }
	 
	 public Review(Integer id, Integer rating, String comment, LocalDate createdOn, User user, Restaurant restaurant) {
		super();
		this.id = id;
		this.rating = rating;
		this.comment = comment;
		this.createdOn = createdOn;
		this.user = user;
		this.restaurant = restaurant;
	 }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDate getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", user=" + user + ", restaurant=" + restaurant + ", rating=" + rating
				+ ", comment=" + comment + ", createdOn=" + createdOn + "]";
	}
	 
	 

}
