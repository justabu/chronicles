package chronicles.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="inappropriate_flags")
public class InappropriateFlag {
	
	private int id;
	private int storyId;
	private int userId;
	
    public InappropriateFlag() {}
   
	public InappropriateFlag(int storyId, int userId) {
		this.storyId = storyId;
		this.userId = userId;
	}
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="story_id")
	public int getStoryId(){
		return this.storyId;
	}
	
	public void setStoryId(int storyId){
		this.storyId = storyId;
	}
	
	@Column(name="user_id")
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getUserId() {
		return userId;
	}
}
