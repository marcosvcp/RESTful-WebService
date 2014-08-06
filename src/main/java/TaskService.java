package main.java;

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

/**
 * Web Service simples de Tasks
 */
@Path("/task")
public class TaskService {
	public static int sequenceTask;
	public static List<Task> tasks = new ArrayList<Task>();

	/**
	 * Método HEAD para o GET de todos as tasks
	 */
	@HEAD
	@Produces(MediaType.TEXT_PLAIN)
	public Response getHeadTask() {
		return Response.ok().status(200)
				.header("Content-Type", "application/json")
				.header("Content-Length", tasks.toString().length()).build();
	}

	/**
	 * Método GET para todas as tasks
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllTasks() {
		try {
			String json = WebService.mapper.writeValueAsString(tasks);
			return Response.status(200).entity(json).build();
		} catch (IOException e) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
	}

	/**
	 * Método POST para postar uma nova task com um novo {@code title}
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTask(@FormParam("title") String title) {
		Task newTask = new Task(title);
		sequenceTask++;
		newTask.setSequencial(sequenceTask);
		tasks.add(newTask);
		return Response.status(200).build();
	}

	private Task searchTaskById(String id) {
		for (Task t : tasks) {
			if (String.valueOf(t.getSequencial()).equals(id)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * Método DELETE para uma task com o {@code id}.
	 */
	@DELETE
	@Path("/{id}")
	public Response removeTask(@PathParam("id") String id) {
		Task t = searchTaskById(id);
		if (t == null) {
			return Response.serverError().status(404).build();
		}
		tasks.remove(t);
		return Response.status(200).build();
	}

	/**
	 * Método PUT que modifica o conteúdo de uma task a partir de seu {@code id}
	 * , com uma {@code newTitle}.
	 */
	@PUT
	@Path("/{id}")
	public Response editTask(@PathParam("id") String id,
			@FormParam("newTitle") String title) {
		Task t = searchTaskById(id);
		if (t == null) {
			return Response.serverError().status(404).build();
		}
		t.setTitle(title);
		return Response.status(200).build();
	}

	/**
	 * Método GET de uma única task apartir de seu {@code id}.
	 */
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response getTask(@PathParam("id") String id) {
		Task t = searchTaskById(id);
		if (t == null) {
			return Response.serverError().status(404).build();
		}
		try {
			return Response.status(200)
					.entity(WebService.mapper.writeValueAsString(t)).build();
		} catch (IOException e) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
	}

	/**
	 * Método HEAD para o GET de um post pelo {@code id}
	 */
	@HEAD
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getHeadByTaskId(@PathParam("id") String id) {
		Task t = searchTaskById(id);
		if (t == null) {
			return Response.serverError().status(404).build();
		}
		return Response.ok().status(200)
				.header("Content-Type", "application/json")
				.header("Content-Length", t.toString().length()).build();
	}
}
