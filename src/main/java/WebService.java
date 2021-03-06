package main.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.sun.jersey.api.view.Viewable;

/**
 * Web Service simples de posts e comentários
 */
@Path("/post")
public class WebService {

	public static int sequencepost;
	private String json;
	public static List<Post> posts = new ArrayList<Post>();
	public static ObjectMapper mapper = new ObjectMapper();
	public static final String FILE = "posts.json";

	// Recurso: post (coleção)
	/**
	 * Método HEAD para o GET de todos os posts
	 */
	@HEAD
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response getHeadPost() {
		Map<String, String> atrr = new HashMap<String, String>();
		atrr.put("numPosts", String.valueOf(posts.size()));
		try {
			return Response
					.ok()
					.status(200)
					.header("Content-Length",
							mapper.writeValueAsBytes(posts).length)
					.entity(mapper.writeValueAsString(atrr))
					.header("Content-Type", "application/json").build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Método GET para todos os Posts
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
	 * Método GET para todos os Posts
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getAllPostsHTML() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("posts", posts.toString().replaceAll("\\[|\\,|\\]", ""));
		return Response.ok(new Viewable("/posts", map)).build();
	}

	/**
	 * Métodos POST para postar um novo post com uma {@code msg}
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createPost(@FormParam("msg") String msg,
			@FormParam("author") String author) {
		Post newPost = new Post(msg, author);
		sequencepost++;
		newPost.setId(sequencepost);
		posts.add(newPost);
		return Response.status(200).build();
	}

	// Recurso: post (entidade individual)

	/**
	 * Método DELETE para um post com o {@code id}.
	 */
	@DELETE
	@Path("/{id}")
	public Response removePost(@PathParam("id") String id) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.serverError().status(404).build();
		}
		posts.remove(p);
		return Response.status(200).build();
	}

	/**
	 * Método PUT que modifica o conteúdo de um post a partir de seu {@code id},
	 * com uma {@code msg}.
	 */
	@PUT
	@Path("/{id}")
	public Response editPost(@PathParam("id") String id,
			@FormParam("novaMsg") String msg) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.serverError().status(404).build();
		}
		p.setMsg(msg);
		return Response.status(200).build();
	}

	/**
	 * Método GET de um único post apartir de seu {@code id}.
	 */
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response getPost(@PathParam("id") String id) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.serverError().status(404).build();
		}
		try {
			return Response.status(200).entity(mapper.writeValueAsString(p))
					.build();
		} catch (IOException e) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
	}

	/**
	 * Método HEAD para o GET de um post pelo {@code id}
	 */
	@HEAD
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response getHeadByPostId(@PathParam("id") String id) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.status(404).build();
		}
		try {
			return Response
					.ok()
					.status(200)
					.header("Content-Type", "application/json")
					.header("Content-Length",
							mapper.writeValueAsBytes(p).length).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Recurso: comment (coleção)

	/**
	 * Método HEAD para o GET de todos os comentários pelo {@code id} do post
	 */
	@HEAD
	@Path("/{id}/comment")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response getHeadCommentsByPostId(@PathParam("id") String id) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.serverError().status(404).build();
		}
		try {
			return Response
					.ok()
					.status(200)
					.header("Content-Type", "application/json")
					.header("Content-Length",
							mapper.writeValueAsBytes(p.getComments()).length)
					.build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Método GET de todos os comentários a partir do {@code id} do post.
	 */
	@GET
	@Path("/{id}/comment")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommentsById(@PathParam("id") String id) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.serverError().status(404).build();
		}
		try {
			return Response.status(200)
					.entity(mapper.writeValueAsString(p.getComments())).build();
		} catch (IOException e) {
		}
		throw new WebApplicationException(Status.NOT_FOUND);
	}

	/**
	 * Método POST de um novo comentário com uma {@code msg}, em um post com o
	 * {@code id}
	 */
	@POST
	@Path("/{id}/comment")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createComment(@PathParam("id") String id,
			@FormParam("msg") String msg, @FormParam("author") String author) {
		Post p = searchPostById(id);
		p.addComment(msg, author);
		return Response.status(200).build();
	}

	// Recurso: comment (entidade)
	/**
	 * Método HEAD para o GET de um comentário pelo {@code id} do post e o
	 * {@code idComment}.
	 */
	@HEAD
	@Path("/{id}/comment/{sequence}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response getHeadCommentByPostId(@PathParam("id") String id,
			@PathParam("sequence") String idComment) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.serverError().status(404).build();
		}
		try {
			return Response
					.ok()
					.status(200)
					.header("Content-Type", "application/json")
					.header("Content-Length",
							mapper.writeValueAsBytes(p.getComment(idComment)).length)
					.build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Post searchPostById(String id) {
		// Post post = posts.stream().filter(p ->
		// p.getId().toString().equals(id))
		// .findFirst().get();
		for (Post p : posts) {
			if (p.getId().toString().equals(id)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Método GET de um comentário especifico de numeração {@code sequence} no
	 * post com {@code id}.
	 */
	@GET
	@Path("/{id}/comment/{sequence}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getComment(@PathParam("id") String id,
			@PathParam("sequence") String sequence) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.serverError().status(404).build();
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
	 * Método DELETE de um comentário especifico de numeração {@code sequence}
	 * no post com o {@code id}.
	 */
	@DELETE
	@Path("/{id}/comment/{sequence}")
	public Response removeComment(@PathParam("id") String id,
			@PathParam("sequence") String sequence) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.serverError().status(404).build();
		}
		p.removeComment(sequence);
		return Response.status(200).build();
	}

	/**
	 * Método PUT de um comentário, modificando a {@code msg} do mesmo no post
	 * que tem como {@code id} e o comentário sequenciado por {@code sequence}.
	 */
	@PUT
	@Path("/{id}/comment/{sequence}")
	public Response editComment(@PathParam("id") String id,
			@PathParam("sequence") String sequence,
			@FormParam("novaMsg") String msg) {
		Post p = searchPostById(id);
		if (p == null) {
			return Response.serverError().status(404).build();
		}
		p.getComment(sequence).setMsg(msg);
		return Response.status(200).build();
	}
}
