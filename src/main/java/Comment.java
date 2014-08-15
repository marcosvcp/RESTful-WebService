package main.java;

public class Comment {

	private Integer sequence;
	private String msg;
	private String author;

	public Comment(String msg, Integer sequence, String author) {
		this.setMsg(msg);
		this.sequence = sequence;
		this.author = author;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	@Override
	public String toString() {
		return "<li>" + "\n" + this.msg + "\n</br><b>Author: " + this.author
				+ "</b></br></li>";
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
