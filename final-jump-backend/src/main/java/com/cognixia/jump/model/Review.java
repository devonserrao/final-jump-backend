package com.cognixia.jump.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Id;

import com.sun.istack.NotNull;

//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Makes sure our data loads fast enough without getting error of unable to serialize fast enough!
@Entity
public class Review implements Serializable {

	 private static final long serialVersionUID = 1L;
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Integer id;
	
	 @ManyToOne
	 @JoinColumn(name = "user_id", referencedColumnName = "id")
	 private User user;
	
	 @ManyToOne
	 @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
	 private Restaurant restaurant;
	
	 @NotNull
	 private Integer rating;
	
	 private String comment;
	
	 private LocalDate createdOn;
	 
	 public Review() {
	     this(-1, 0, "N/A", LocalDate.of(2021, 1, 1));
	 }
	 
	 public Review(Integer id, Integer rating, String comment, LocalDate createdOn) {
		super();
		this.id = id;
		this.rating = rating;
		this.comment = comment;
		this.createdOn = createdOn;
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
