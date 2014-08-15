<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<head></head>
<body>
	<h1>(TODO API Spec)</h1>
	<h3>Recursos, URIs e métodos disponíveis</h3>
	<h4>Recurso: Task (coleção)</h4>
	<ul>
		<li><code>HEAD /task</code></li>
		<li><code>POST /task</code></li>
		<ul>
			<li><code>PARAMETRO title</code></li>
		</ul>
		<li><code>GET /task</code></li>
	</ul>
	<h4>Recurso: Task (entidade individual)</h4>
	<ul>
		<li><code>GET /task/:id</code></li>
		<li><code>HEAD /task/:id</code></li>
		<li><code>PUT /task/:id</code></li>
		<ul>
			<li><code>PARAMETRO newTitle</code></li>
		</ul>
		<li><code>DELETE /task/:id</code></li>
	</ul>
</body>
</html>