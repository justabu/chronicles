<%@ page language = "java" contentType = "text/html; charset=UTF-8" isELIgnored = "false" pageEncoding = "UTF-8"%>
<%@ taglib prefix = "form" uri = "http://www.springframework.org/tags/form"%>
<%@ page import="java.util.*,chronicles.models.Story,chronicles.models.User,chronicles.viewmodels.StoryViewModel;" %>
<%User user = (User)session.getAttribute("user"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Chronicles</title>
		<script type="text/javascript">
		    $(document).ready( function(){
				$(".content_block").corners("10px");
				$(".home_page_button").corners("10px");
		    });
		</script>
    </head>
    <body id="home">
		<div class="content_block">
			<h2>Welcome, <%=user.getFirstName() %>!</h2>
			<p>ThoughtWorks Chronicles is our very own perennial campfire - a place for you
		    to share stories about ThoughtWorks' culture.  Here's your chance to immortalise yourself in the archives of our history.  Go ahead.</p>
			<a href="${pageContext.request.contextPath}/story/new" class="blue home_page_button">
				Tell us a new story
			</a>
			<div class="clearer"></div>
		</div>
		
		<div class="content_block recent_stories">
			<h2>Recently Added Stories</h2>
			<%@include file="stories_table.jsp" %>
			<a href="${pageContext.request.contextPath}/story" class="orange home_page_button">
				Browse more stories
			</a>
			<div class="clearer"></div>
		</div>
	</body>
</html>