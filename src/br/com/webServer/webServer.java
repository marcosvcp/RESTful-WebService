package br.com.webServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

/**
 * Web Service simples de posts e coment�rios
 */
@Path("/post")
public class WebServer {

	public static int sequencepost;
	public static List<Post> posts = new ArrayList<Post>();
	private String json;
	ObjectMapper mapper = new ObjectMapper();

	// Recurso: post (cole��o)
	/**
	 * M�todo HEAD para o GET de todos os posts
	 */
	@HEAD
	@Produces(MediaType.TEXT_PLAIN)
	public Response getHeadPost() {
		return Response.ok().status(200)
				.header("Content-Type", "application/json")
				.header("Content-Length", posts.toString().length()).build();
	}

	/**
	 * M�todo GET para todos os Posts
	 */
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

	/**
	 * M�todos POST para postar um novo post com uma {@code msg}
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createPost(@FormParam("msg") String msg) {
		Post newPost = new Post(msg);
		sequencepost++;
		newPost.setId(sequencepost);
		posts.add(newPost);
		return Response.status(200).build();
	}

	// Recurso: post (entidade individual)

	/**
	 * M�todo DELETE para um post com o {@code id}.
	 */
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

	/**
	 * M�todo PUT que modifica o conte�do de um post a partir de seu {@code id},
	 * com uma {@code msg}.
	 */
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

	/**
	 * M�todo GET de um �nico post apartir de seu {@code id}.
	 */
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response getPost(@PathParam("id") String id) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.status(404).build();
		}
		try {
			return Response.status(200).entity(mapper.writeValueAsString(p))
					.build();
		} catch (IOException e) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
	}

	/**
	 * M�todo HEAD para o GET de um post pelo {@code id}
	 */
	@HEAD
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getHeadByPostId(@PathParam("id") String id) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.status(404).build();
		}
		return Response.ok().status(200)
				.header("Content-Type", "application/json")
				.header("Content-Length", p.toString().length()).build();
	}

	// Recurso: comment (cole��o)

	/**
	 * M�todo HEAD para o GET de todos os coment�rios pelo {@code id} do post
	 */
	@HEAD
	@Path("/{id}/comment}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getHeadCommentsByPostId(@PathParam("id") String id) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.status(404).build();
		}
		return Response.ok().status(200)
				.header("Content-Type", "application/json")
				.header("Content-Length", p.getComments().toString().length())
				.build();
	}

	/**
	 * M�todo GET de todos os coment�rios a partir do {@code id} do post.
	 */
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

	/**
	 * M�todo POST de um novo coment�rio com uma {@code msg}, em um post com o
	 * {@code id}
	 */
	@POST
	@Path("/{id}/comment")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createComment(@PathParam("id") String id,
			@FormParam("msg") String msg) {
		Post p = searchPostById(id);
		p.addComment(msg);
		return Response.status(200).build();
	}

	// Recurso: comment (entidade)
	/**
	 * M�todo HEAD para o GET de um coment�rio pelo {@code id} do post e o
	 * {@code idComment}.
	 */
	@HEAD
	@Path("/{id}/comment/{sequence}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getHeadCommentByPostId(@PathParam("id") String id,
			@PathParam("sequence") String idComment) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.status(404).build();
		}
		return Response
				.ok()
				.status(200)
				.header("Content-Type", "application/json")
				.header("Content-Length",
						p.getComment(idComment).toString().length()).build();
	}

	private Post searchPostById(String id) {
		for (Post p : posts) {
			if (p.getId().toString().equals(id)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * M�todo GET de um coment�rio especifico de numera��o {@code sequence} no
	 * post com {@code id}.
	 */
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

	/**
	 * M�todo DELETE de um coment�rio especifico de numera��o {@code sequence}
	 * no post com o {@code id}.
	 */
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

	/**
	 * M�todo PUT de um coment�rio, modificando a {@code msg} do mesmo no post
	 * que tem como {@code id} e o coment�rio sequenciado por {@code sequence}.
	 */
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
