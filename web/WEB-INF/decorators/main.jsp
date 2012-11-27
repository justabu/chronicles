<%@ page language = "java" contentType = "text/html; charset=UTF-8" isELIgnored = "false" pageEncoding = "UTF-8"%>
<%@ taglib prefix = "form" uri = "http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ page import = "chronicles.models.User" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.corners.min.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/smoothness/jquery-ui-1.8.2.custom.css" />

	    <script type="text/javascript">
		    $(document).ready( function(){
			    try{
				$(".blueButton").corners("10px");
			    }catch(err)
			    {				    
			    }
		    });
		</script>
        
        <title><decorator:title /></title>
		<link href="${pageContext.request.contextPath}/css/chronicles.css" rel="stylesheet" type="text/css"/>
		<decorator:head />
    </head>
    <body id="<decorator:getProperty property='body.id'/>">
    
	<div id="page">
		<div id="header">
			<div id="headerContent">
				<% User user = (User)session.getAttribute("user"); %>
				<a href="/chronicles/home" id="logoLink"><img src="/chronicles/images/logo.png" alt="ThoughtWorks Chronicles"></a>
			    <div id="sessionControls">
				    <a href="/chronicles/profile" id="loggedInUser"><%= user.fullName() %></a>
					<div class="clearer"></div>
				</div>
			</div>

 			<div id="topMenu">
	  			<div class="top_menu_items">
		  			<ul>
						<li><a href="/chronicles/home" id="home_link">Home</a></li>
	    				<li><a href="/chronicles/story/new" id="add_story_link">Add A Story</a></li>
		    	 		<li><a href="/chronicles/story" id="browse_stories_link">Browse Stories</a></li>
	    				<li><a href="/chronicles/profile" id="manage_profile_link">My Chronicles</a></li>
	  				</ul>
					<%@include file='/WEB-INF/views/basic_search_form.jsp' %>
					<div class="clearer"></div>
	 			</div>
 			</div>
 		</div>
  
  		<div id="page_content">
  			<div id="message"><p>${message}</p></div>
			<decorator:body />
  		</div>
	</div>
</body>
</html>
