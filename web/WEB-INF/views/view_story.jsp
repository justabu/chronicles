<%@page import="chronicles.repository.UserRepository"%>
<%@ page language = "java" contentType = "text/html; charset=UTF-8" isELIgnored = "false" pageEncoding = "UTF-8"%>

<%@ taglib prefix = "form" uri = "http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ 
	page import = "
		chronicles.models.Story,
		chronicles.models.User,
		chronicles.models.Tag,
		chronicles.models.Comment,
		chronicles.viewmodels.StoryViewModel,
		java.util.List"
%>

	StoryViewModel storyViewModel= new StoryViewModel();

<c:set var="story" value="${story}"/>
<c:set var="user" value="${story.author}"/>

<html>
    <head>
        <link href="${pageContext.request.contextPath}/css/chronicles.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-validate.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-validate-setup.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/comment_form_setup.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.textarea-expander.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $(".comment_textarea").corners("10px");
                $(".comment_display_area").corners("10px");
                $("#content").TextAreaExpander(); 
                $("#create_tag_form").validate();
             });
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${story.title}</title>
    </head>

    <body>
		<div id="view_container">
        
			<div id = "inappropriate_flag_container">
				<%@include file="view_story_resources/inappropriate_flags.jsp" %>
 			</div>
 			
            <div id="story_container">
            	<%@include file="view_story_resources/story.jsp" %>
 			</div>

		<div id="story_footer">
<%
if ((Boolean)pageContext.findAttribute("allowEdit"))
    {
%>
		<div id="delete_edit_controls">
			<form action="${pageContext.request.contextPath}/story/${story.id}/edit" method="get">
				<input type="submit" value="Edit" class="button" id="edit"/>
			</form>
			<form action="/chronicles/story/${story.id}" method="post">
				<input type="hidden" name="_method" value="delete"/>
            	<input type="submit" value="Delete" class="button" id="delete"
                	onclick = "return confirm('Are you sure you want to delete your story?');" />
        	</form>
		</div>

<%
    }
%>

    <div id="tag_controls">
        <h2>Topics Tagged</h2>
        
      
      <% String stringTags = (String)pageContext.findAttribute("tags"); %> 
      <%= stringTags %>
      
      <% Story story = (Story) pageContext.findAttribute("story");
      	 String tagAction = "/chronicles/story/"+story.getId() +"/tag"; %>
      	 <h3>(seperate multiple tags with comma)</h3>

        <form id = "create_tag_form" action = "<%= tagAction %>" method = "POST" >
            <input name="commaSeparatedTags" id = "commaSeparatedTags" class="required" />
            <input type = "submit" value = "Add Tag" name = "add_tag" id="addTag" />
        </form>
      <% String tagMessage = request.getParameter("tagMessage");
      if(tagMessage != null) out.println(tagMessage); %> 
    </div>

     <% String favouriteAction = "/chronicles/story/${story.id}/favourite"; %>
     <%
         if((Boolean)pageContext.findAttribute("currentStoryAlreadyUserFavourite") == true ) {
      %>
      	<div id="favourite">
      		<p>
      			<img src="${pageContext.request.contextPath}/images/favourite.png" width="50" height="40" />
      			One of my favourites
      		</p>
      	</div>		
		    <% } else { %>
	 		<form:form commandName="story" id="add_favourites" method="post" action="<%= favouriteAction %>">
        	 	<input type = "submit" value = "Add to Favourites" class = "submit" id="favourite" />
     		</form:form>
	  		<% } %>
			<div class="clearer"></div>
		</div>
		<div id="comment_container">
			<% String commentAction = "/chronicles/story/" + story.getId() + "/comment" ;%>
        <form:form commandName="comment" id="comment_form" action="<%= commentAction %>">
        <ul>
        	<li>
        		<form:input path="storyId" id="storyId" type="HIDDEN" value="<%= story.getId()%>"/>
        	</li>
          	<li>
          	    <div>
          		<div class="comment_textarea" id="contentDiv">
        			<form:textarea path="content" id="content" rows="1" cols="110" class="comment_textbox expand" />
        			<div id="error" style="display:none"></div>
        		</div>        		
        		<div class="post_comment_float">
            		<input type="submit" value="Post Comment" id="blue_submit_button" name="publish" />
				</div>
				<div class="clearer"></div>
				</div>
			</li>
			<li>
				<div>
					<% int noOfComments = story.getComments().size(); %>
					<% if(noOfComments==1) { %>
						1 Comment
					<% }else{ %>
						<%= noOfComments %> Comments
					<% } %>
				 </div>
			</li>			
	    </ul>
        </form:form>
        <br/>
        <% 
		  List<Comment> directComments = story.fetchDirectComments(); 
          for(Comment displayComment : directComments){
          	out.println("<div>");
        %>
		<div style="width:70%;">
        	<fmt:formatDate var="dateOfComment" value="<%= displayComment.getDateAdded() %>" pattern="HH:mm | dd MMM yyyy" />                	 
            <div style="float: left;"><a href="${pageContext.request.contextPath}/profile/<%=displayComment.getAuthor().getUsername()%>"><%= displayComment.getAuthor().fullName() %></a></div> 	
            <div style="float: right;">${dateOfComment}</div>
            <div class="clearer"></div>
        </div>
        <div class="comment_display_area">
			<%= displayComment.getContent().replace("\r","<br/>") %>        
        </div>
        <%
           	out.println("</div><br/><br/>");
          }
        %>    
		</div>
  </body>
</html>