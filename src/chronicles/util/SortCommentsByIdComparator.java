package chronicles.util;

import java.util.Comparator;

import chronicles.models.Comment;

public class SortCommentsByIdComparator implements Comparator<Comment> {

	@Override
	public int compare(Comment commentOne, Comment commentTwo) {
		return commentOne.getId()-commentTwo.getId();
	}

}
