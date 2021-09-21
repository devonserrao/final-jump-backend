package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;

import javax.persistence.Id;

import com.sun.istack.NotNull;


//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Makes sure our data loads fast enough without getting error of unable to serialize fast enough!
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum RoleType {
        CUSTOMER, ADMIN
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleType role;
    
    @NotNull
    private String username;
    
    @NotNull
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews;

    public User() {
        this(-1, "N/A", RoleType.CUSTOMER, "N/A", "N/A", new ArrayList<Review>());
    }

	public User(Integer id, String name, RoleType role, String username, String password, List<Review> reviews) {
		super();
		this.id = id;
		this.name = name;
		this.role = role;
		this.username = username;
		this.password = password;
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

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", role=" + role + ", username=" + username + ", password="
				+ password + ", reviews=" + reviews + "]";
	}
	
	public String toJson() {

		return "{\"id\" : " + id 
				+ ", \"name\" : \"" + name 
				+ "\"" + ", \"role\" : \"" + role 
				+ "\"" + ", \"username\" : \"" + username
				+ "\"" + ", \"password\" : \"" + password
				+ "\"" + ", \"reviews\" : " + reviews
			    + "}";
	} 

}
