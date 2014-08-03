package br.com.webServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.map.ObjectMapper;

@Path("/post")
public class webServer {

	public static int sequencepost;
	public static List<Post> posts = new ArrayList<Post>();
	private String json;
	ObjectMapper mapper = new ObjectMapper();

	@DELETE
	@Path("/{id}")
	public void removePost(@PathParam("id") String id) {
		Post p = searchPostById(id);
		posts.remove(p);
	}

	@PUT
	@Path("/{id}")
	public void editPost(@PathParam("id") String id,
			@FormParam("novaMsg") String msg) {
		Post p = searchPostById(id);
		p.setMsg(msg);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPost(@PathParam("id") String id) {
		Post p = searchPostById(id);
		if (p == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		try {
			return mapper.writeValueAsString(p);
		} catch (IOException e) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
	}

	private Post searchPostById(String id) {
		for (Post p : posts) {
			if (p.getId().toString().equals(id)) {
				return p;
			}
		}
		return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllPosts() {
		try {
			json = mapper.writeValueAsString(posts);
		} catch (IOException e) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		return json;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String createPost(@FormParam("msg") String msg) {
		Post newPost = new Post(msg);
		sequencepost++;
		newPost.setId(sequencepost);
		posts.add(newPost);
		throw new WebApplicationException(Status.OK);
	}

	@GET
	@Path("/{id}/comment")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCommentsById(@PathParam("id") String id) {
		Post p = searchPostById(id);
		try {
			return mapper.writeValueAsString(p.getComments());
		} catch (IOException e) {
		}
		throw new WebApplicationException(Status.NOT_FOUND);
	}

	@POST
	@Path("/{id}/comment")
	@Produces(MediaType.APPLICATION_JSON)
	public String createComment(@PathParam("id") String id,
			@FormParam("msg") String msg) {
		Post p = searchPostById(id);
		p.addComment(msg);
		return Status.ACCEPTED.toString();
	}

	@GET
	@Path("/{id}/comment/{sequence}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getComment(@PathParam("id") String id,
			@PathParam("sequence") String sequence) {
		try {
			return mapper.writeValueAsString(searchPostById(id).getComment(
					sequence));
		} catch (IOException e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}

	@DELETE
	@Path("/{id}/comment/{sequence}")
	public void removeComment(@PathParam("id") String id,
			@PathParam("sequence") String sequence) {
		searchPostById(id).removeComment(sequence);
	}

	@PUT
	@Path("/{id}/comment/{sequence}")
	public void editComment(@PathParam("id") String id,
			@PathParam("sequence") String sequence,
			@FormParam("novaMsg") String msg) {
		searchPostById(id).getComment(sequence).setMsg(msg);
	}
}
