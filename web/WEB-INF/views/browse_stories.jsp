<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*,chronicles.models.Story,chronicles.models.User,java.text.SimpleDateFormat,chronicles.viewmodels.StoryViewModel;" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>List of all stories</title>
		<link href="${pageContext.request.contextPath}/css/chronicles.css" rel="stylesheet" type="text/css">
	</head>

	<body id="browse_stories">
		<div class="content_block">
		<div id="browse_stories_header">
 		<% 	String query = (String)pageContext.findAttribute("query");
 			if(query==null){ 
 		%>
			<div>List of all stories</div>
		<% } else {	%>
			<div>Search Results</div>
		<% } %>
		</div>
			<table>
				<tr>
				</tr>
				<tr>
					<td>
			 			<h1>Search By: </h1>
	 				</td>
					<td>	
						<form action="/chronicles/search/story">
							<% 
								if(query!=null) { 
							%>
					 			<input type="text" name="query" value="<%= query %>" id="keyword_query"/>
					 		<% } else {	%>
					 			<input type="text" name="query" value="" id="keyword_query"/>
					 		<% } %>
					 			<input type="submit" value="Keyword" id="keyword_search"/>
				 		</form>
					</td>		
		 		</tr>
	 		</table>
	 		
	 		<%@include file="stories_table.jsp" %>
	 		
			
		</div>
	</body>
</html>