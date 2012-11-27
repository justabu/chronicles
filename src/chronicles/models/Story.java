package chronicles.models;

import java.util.ArrayList;
import java.util.Calendar;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;

import chronicles.util.SortCommentsByIdComparator;

@Entity
@Table(name = "stories")
public class Story {

	private int id;
	private String title;
	private String year;
	private String content;
	private String month;
	private boolean isDeleted;
	private Date dateAdded;
	private String city;
	private User author;
	private Set<Tag> tags = new HashSet<Tag>();
	private Set<InappropriateFlag> inappropriateFlags = new HashSet<InappropriateFlag>();
	private Set<Comment> comments = new HashSet<Comment>();

	@ManyToMany(mappedBy = "favouriteStories")
	private Set<User> users;

	public Story() {
	}

	public Story(String title, String year, String content, User author,
			String month, String city) {
		this.title = title;
		this.year = year;
		this.content = content;
		this.author = author;
		this.month = month;
		this.isDeleted = false;
		this.city = city;
	}

	@Id
	@GeneratedValue
	@Column(name = "id")
	public int getId() {
		return this.id;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "author_user_id", insertable = true, updatable = true)
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "title", nullable = false)
	@Size(min = 1)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "year", nullable = false)
	@DecimalMin(value = "1963")
	@DecimalMax(value = "2010")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "content", nullable = false, columnDefinition = "text")
	@Size(min = 1)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "month")
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Column(name = "deleted", nullable = false)
	public boolean getDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean delete) {
		this.isDeleted = delete;
	}

	@Column(name = "dateAdded")
	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	@Column(name = "city", nullable = false)
	@Size(min = 1)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Boolean isOwnedBy(User user) {
		return user.getId() == author.getId();
	}

	public void setTags(Set<Tag> listOfTags) {
		this.tags = listOfTags;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@JoinColumn(name = "story_id", insertable = true, updatable = false)
	public Set<Tag> getTags() {
		return tags;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@JoinColumn(name = "story_id", insertable = true, updatable = false)
	public Set<InappropriateFlag> getInappropriateFlags() {
		return inappropriateFlags;
	}

	public void setInappropriateFlags(Set<InappropriateFlag> inappropriateFlags) {
		this.inappropriateFlags = inappropriateFlags;
	}

	public boolean valid() {
		return checkValidYearAndTextFields() && !checkMonthInFuture();
	}

	private boolean checkValidYearAndTextFields() {
		Validator validator = Validation.buildDefaultValidatorFactory()
				.getValidator();
		Set<ConstraintViolation<Story>> constraintViolations = validator
				.validate(this);

		return constraintViolations.isEmpty();
	}

	public boolean checkMonthInFuture() {
		if (Integer.parseInt(year) == currentYear()) {
			Calendar cal = Calendar.getInstance();
			return parseMonthAsInteger(month) > cal.get(Calendar.MONTH);
		}
		return false;
	}

	private int parseMonthAsInteger(String wantedMonth) {
		List<String> months = new ArrayList<String>();
		months.add("January");
		months.add("February");
		months.add("March");
		months.add("April");
		months.add("May");
		months.add("June");
		months.add("July");
		months.add("August");
		months.add("September");
		months.add("October");
		months.add("November");
		months.add("December");
		for (int i = 0; i < months.size(); i++) {
			String iteratedMonth = months.get(i);
			if (wantedMonth.equals(iteratedMonth)) {
				return i;
			}
		}
		return 0;
	}

	private int currentYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}

	public void addInappropriateFlag(User user) {
		inappropriateFlags
				.add(new InappropriateFlag(this.getId(), user.getId()));
	}

	public Boolean isFlaggedBy(User user) {
		for (InappropriateFlag inappropriateFlag : inappropriateFlags) {
			if (inappropriateFlag.getUserId() == user.getId()) {
				return true;
			}
		}
		return false;
	}

	public boolean hasBeenFlagged() {
		return inappropriateFlags.size() > 0;
	}

	public void removeInappropriateFlag(User user) {
		if (isFlaggedBy(user))
			inappropriateFlags.remove(findInappropriateFlagByUser(user));
	}

	private InappropriateFlag findInappropriateFlagByUser(User user) {
		for (InappropriateFlag inappropriateFlag : inappropriateFlags) {
			if (inappropriateFlag.getUserId() == user.getId()) {
				return inappropriateFlag;
			}
		}
		return null;
	}

	public void merge(Story submittedStory) {
		setCity(submittedStory.getCity());
		setTitle(submittedStory.getTitle());
		setYear(submittedStory.getYear());
		setMonth(submittedStory.getMonth());
		setAuthor(submittedStory.getAuthor());
		setContent(submittedStory.getContent());
	}

	public void addTag(String string) {
		Tag newTag = new Tag(this.getId(), string);
			if(!tags.contains(newTag)){ 
				tags.add(newTag);
			}
	}
	
	public String commaSeparatedTags() {
		TagListCreator tagListCreator = new TagListCreator();
		return tagListCreator.createAlphabeticalCommaSeparatedTagsString(new ArrayList<Tag>(tags));
	}

	public Integer inappropriateFlagCount() {
		return inappropriateFlags.size();
	}

	public InappropriateFlag getInappropriateFlag(User user) {
		for (InappropriateFlag flag : inappropriateFlags) {
			if (flag.getUserId() == user.getId()) {
				return flag;
			}
		}
		return null;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "story_id")
	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public void addComment(Comment comment) {
		comments.add(comment);
	}

	public void deleteComment(Comment comment) {
		comments.remove(comment);
	}

	public List<Comment> fetchDirectComments() {
		List<Comment> directComments = new ArrayList<Comment>();
		for (Comment comment : getComments()) {
			if (comment.getReplyToId() == 0)
				directComments.add(comment);
		}

		Collections.sort(directComments, new SortCommentsByIdComparator());
		return directComments;

	}


	public void addTags(ArrayList<String> validTagsList) {
		for (String tag: validTagsList){
			addTag(tag);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Story other = (Story) obj;
		if (id != other.id)
			return false;
		return true;
	}

	


}