package chronicles.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;

import chronicles.util.StoryComparatorByDateAdded;

@Entity
@Table(name = "users")
public class User {
	private int id;
	private String username;
	private String firstName;
	private String lastName;
	private String shortDescription;
	private String homeOffice;
	private String currentOffice;
	private Date dateOfJoining;
	private Set<Story> favouriteStories = new HashSet<Story>();
	private Set<Story> stories = new HashSet<Story>();

	public User(String name, String firstName, String lastName) {
		this.username = name;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public User() {
	}

	@Id
	@GeneratedValue
	@Column(name = "id")
	public int getId() {
		return this.id;
	}

	@Column(name = "username", nullable = false)
	@Size(min = 1, max = 30)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String fullName() {
		return getFirstName() + " " + getLastName();
	}

	@Column(name = "firstname", nullable = true)
	@Size(min = 1, max = 255, message = "No first name")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "lastname", nullable = true)
	@Size(min = 1, max = 255, message = "No last name")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "shortdescription", nullable = true)
	@Size(min = 0, max = 2000)
	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	@Column(name = "homeoffice", nullable = true)
	@Size(min = 0, max = 255)
	public String getHomeOffice() {
		return homeOffice;
	}

	public void setHomeOffice(String homeOffice) {
		this.homeOffice = homeOffice;
	}

	@Column(name = "currentoffice", nullable = true)
	@Size(min = 0, max = 255)
	public String getCurrentOffice() {
		return currentOffice;
	}

	public void setCurrentOffice(String currentOffice) {
		this.currentOffice = currentOffice;
	}

	@Column(name = "dateofjoining", nullable = true)
	@Past
	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@JoinColumn(name = "author_user_id", insertable = true, updatable = false)
	public Set<Story> getStories() {
		return stories;
	}

	public void setStories(Set<Story> stories) {
		this.stories = stories;
	}

	

	public Boolean hasAddedToFavourite(int storyId) {
		for (Story story : favouriteStories) 
			if(story.getId() == storyId)
				return true;
		return false;
	}

	public Boolean hasMinimumDetails() {
		if (checkValidName(this.firstName) && checkValidName(this.lastName)) 
			return true;
		return false;
	}

	private Boolean checkValidName(String name) {
		return !(name == null || name.matches("^\\s*$"));
	}

	public void addFavouriteStories(Story story) {
		favouriteStories.add(story);
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "favourites", 
			joinColumns = { @JoinColumn(name = "user_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "story_id") }
	)
	public Set<Story> getFavouriteStories() {
		return favouriteStories;
	}

	public void setFavouriteStories(Set<Story> favouriteStories) {
		this.favouriteStories = favouriteStories;
	}
	
	public List<Story> storiesByDescendingDateAdded() {
		List<Story> sortedStories = new ArrayList<Story>(stories);
		Collections.sort(sortedStories, new StoryComparatorByDateAdded());
		return sortedStories;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
