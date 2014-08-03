package br.com.webServer;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.text.DateFormatter;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.map.ObjectMapper;

@Path("/post")
public class webServer {

	public static int sequencepost;
	public static List<Post> posts = new ArrayList<Post>();
	private String json;
	ObjectMapper mapper = new ObjectMapper();

	@HEAD
	@Produces(MediaType.TEXT_PLAIN)
	public Response getHeadPost() {
		return Response.ok().status(200)
				.header("Server", "Apache Tomcat/7.0.55")
				.header("Content-Type", "application/json")
				.header("Content-Length", posts.size())
				.header("Date", new GregorianCalendar()).build();
	}

	@DELETE
	@Path("/{id}")
	public Response removePost(@PathParam("id") String id) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.status(404).build();
		}
		posts.remove(p);
		return Response.status(200).build();
	}

	@PUT
	@Path("/{id}")
	public Response editPost(@PathParam("id") String id,
			@FormParam("novaMsg") String msg) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.status(404).build();
		}
		p.setMsg(msg);
		return Response.status(200).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response getPost(@PathParam("id") String id) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.noContent().build();
		}
		try {
			return Response.status(200).entity(mapper.writeValueAsString(p))
					.build();
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
	public Response getAllPosts() {
		try {
			json = mapper.writeValueAsString(posts);
		} catch (IOException e) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		return Response.status(200).entity(json).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createPost(@FormParam("msg") String msg) {
		Post newPost = new Post(msg);
		sequencepost++;
		newPost.setId(sequencepost);
		posts.add(newPost);
		return Response.status(200).build();
	}

	@GET
	@Path("/{id}/comment")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommentsById(@PathParam("id") String id) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.status(404).build();
		}
		try {
			return Response.status(200)
					.entity(mapper.writeValueAsString(p.getComments())).build();
		} catch (IOException e) {
		}
		throw new WebApplicationException(Status.NOT_FOUND);
	}

	@POST
	@Path("/{id}/comment")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createComment(@PathParam("id") String id,
			@FormParam("msg") String msg) {
		Post p = searchPostById(id);
		p.addComment(msg);
		return Response.status(200).build();
	}

	@GET
	@Path("/{id}/comment/{sequence}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getComment(@PathParam("id") String id,
			@PathParam("sequence") String sequence) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.status(404).build();
		}
		try {
			return Response.status(200)
					.entity(mapper.writeValueAsString(p.getComment(sequence)))
					.build();
		} catch (IOException e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}

	@DELETE
	@Path("/{id}/comment/{sequence}")
	public Response removeComment(@PathParam("id") String id,
			@PathParam("sequence") String sequence) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.status(404).build();
		}
		p.removeComment(sequence);
		return Response.status(200).build();
	}

	@PUT
	@Path("/{id}/comment/{sequence}")
	public Response editComment(@PathParam("id") String id,
			@PathParam("sequence") String sequence,
			@FormParam("novaMsg") String msg) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.status(404).build();
		}
		p.getComment(sequence).setMsg(msg);
		return Response.status(200).build();
	}
}
