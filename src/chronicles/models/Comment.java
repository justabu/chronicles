package chronicles.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="comments")
public class Comment {
	
	private int id;
	private int storyId;
	private int authorId;
	private String content;
	private Date dateAdded;
	private int replyToId;
	private User author;
	

	private List<Comment> replies = new ArrayList<Comment>();
	
	public Comment(int storyId, int authorId, String content, Date dateAdded) {
				this.storyId = storyId;
				this.authorId = authorId;
				this.content = content;
				this.dateAdded = dateAdded;
				
	}

	public Comment() {	}

	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="story_id")
	public int getStoryId() {
		return storyId;
	}
	
	public void setStoryId(int storyId) {
		this.storyId = storyId;
	}
	
	@Column(name="author_user_id")
	public int getAuthorId() {
		return authorId;
	}
	
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	
	@Column(name="content", nullable=false, columnDefinition="text")
	@Size(min=1)
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name="date_added")
	public Date getDateAdded() {
		return dateAdded;
	}
	
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	
	@OneToMany (cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="reply_to_id")
	public List<Comment> getReplies() {
		return replies;
	}
	
	public void setReplies(List<Comment> replies) {
		this.replies = replies;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="author_user_id", insertable= false, updatable=false)
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
	
	public void setReplyToId(int replyToId) {
		this.replyToId = replyToId;
	}
	
	@Column(name="reply_to_id")
	public int getReplyToId() {
		return replyToId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + authorId;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + id;
		result = prime * result + storyId;
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
		Comment other = (Comment) obj;
		if (authorId != other.authorId)
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id != other.id)
			return false;
		if (storyId != other.storyId)
			return false;
		return true;
	}

	public void addReply(Comment reply) {
		reply.setReplyToId(id);
		replies.add(reply);
	}


}
