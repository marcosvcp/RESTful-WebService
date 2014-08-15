package main.java;

import java.util.ArrayList;
import java.util.List;

public class Post {

	private Integer id;
	private String msg;
	private List<Comment> comments;
	private String author;

	public Post(String msg, String author) {
		this.comments = new ArrayList<Comment>();
		this.msg = msg;
		this.author = author;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void addComment(String msg, String author) {
		this.comments.add(new Comment(msg, this.comments.size() + 1, author));
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Post)) {
			return false;
		}
		Post otherPost = (Post) obj;
		return otherPost.getMsg().equals(this.msg)
				&& otherPost.getAuthor().equals(this.getAuthor());
	}

	public Comment getComment(String sequence) {
		for (Comment c : this.comments) {
			if (c.getSequence().toString().equals(sequence)) {
				return c;
			}
		}
		return null;
	}

	public void removeComment(String sequence) {
		Comment c = getComment(sequence);
		comments.remove(c);
	}

	@Override
	public String toString() {
		return "<li>" + "\n" + this.msg + "\n</br><b>Author: " + author
				+ "</b></br>" + "<p><br><ul>" + getComments()
				+ "</ul></p></li>";
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
