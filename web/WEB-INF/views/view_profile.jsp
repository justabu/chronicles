<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import = "chronicles.models.User, chronicles.models.Story,chronicles.viewmodels.StoryViewModel"%>
<%@ page import = "java.util.Date, java.util.List, java.util.Set"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv = "Content-Type" content = "text/html; charset=UTF-8">
        <title>My Chronicles</title>
        <link href="${pageContext.request.contextPath}/css/chronicles.css" rel="stylesheet" type="text/css">
    </head>
		<% User user = (User)pageContext.findAttribute("user"); %>
    <body id="manage_profile">
		<div class="profile_container">
        <form action = "/chronicles/profile/edit">
			<div class="profile_content">
			<p class="profile_fullname"><%= user.getFirstName() + " " + user.getLastName() %></p>
           	<ul> 
            <%
			Date dateOfJoining = user.getDateOfJoining();
            if (dateOfJoining != null && dateOfJoining.toString().length() != 0)
                {
            %>    
		                      <li class="profile_lists">Joined: <fmt:formatDate var="dateJoined" value="<%= user.getDateOfJoining() %>" pattern="dd MMMM yyyy" /> 	${dateJoined}</li>
		                      <li class="profile_lists">|</li>

            <%
                }
            %>

            <%
            String office = user.getHomeOffice(); 
            if (office != null && office.length() != 0)
                {
            %>
			<li class="profile_lists">Home Office: <%= user.getHomeOffice() %></li>
			<li class="profile_lists">|</li>
            <%
                }
            %>
			
            <%
			String currentOffice = user.getCurrentOffice() ;
            if (currentOffice != null && currentOffice.length() != 0)
                {
            %>
			<li class="profile_lists">Current Office: <%= user.getCurrentOffice() %></li>
			<li class="profile_lists">|</li>
            <%
                }
            %>
			            
			</ul>
			<%
            String shortDescription = user.getShortDescription();
            if (shortDescription != null && shortDescription.length() != 0)
                {
            %>
			<p class="profile_description"> <%= user.getShortDescription() %></p>
            <%
                }

            %>
			
			<%
			 
			if ((Boolean)pageContext.findAttribute("editable")) {
			%>
            	<p><input type = "submit" value = "Edit Profile" name="editProfile"/></p>
		    <% 
	        }
		    %>
       		</div>
            <p><a href="http://gab.thoughtworks.com/user/<%=user.getUsername() %>" target="_blank"> Contact Details </a></p>
        </form>
        </div>
        <div id="story_list_tabs">
            <ul>
            	<% boolean editable = (Boolean)pageContext.findAttribute("editable"); %>
                <li><a href="#authored_stories_tab">
                <% if (editable) { 
                		out.print("My"); 
                	} else { 
                		out.print(user.getFirstName()+"'s"); 
                	} 
                	%> Stories</a></li>
                <% if (editable) { %>
                    <li><a href="#favourite_stories_tab">My Favourites</a></li>
                <% } %>
            </ul>
            <div class="clearer"></div>
            <div id="authored_stories_tab" name="authored_stories_tab">
            	
            	<%  List<Story> userStories = user.storiesByDescendingDateAdded();
            		StoryViewModel storyViewModel = new StoryViewModel();
            		if (userStories.isEmpty()) {
            	%>
	         	    <div class="content_block">Hey ${user.firstName}, you haven’t told us any stories yet... 
			      		<a href="${pageContext.request.contextPath}/story/new" class="blue home_page_button">
			      			Tell us a new story 
			      		</a>
			      		<div class="clearer"></div>
			      	</div>
            	<% } else { %>
            	
            	<table id="authored_stories">
                	<tr>
	    	    		<th width="40%"> Title </th>
    			    	<th width="20%"> City </th>
		    	    	<th width="15%"> Date of Occurrence </th>
			    	    <th width="15%"> Date Added </th>
	    			    <th width="10%"> Has Been Flagged </th>
    		    	</tr>
            	<%
             	    int i = 0;
    	           	for (Story story : userStories) {
           		        out.println("<tr " + storyViewModel.getTableRowClass(i++) + ">");
        			    out.print("<td width=\"40%\"><a href=\"/chronicles/story/" + story.getId() + "\">"+  story.getTitle() +"</a></td>"); 
    	        		out.print("<td width=\"20%\">" + story.getCity() + "</td>");
            			out.print("<td width=\"15%\">" + storyViewModel.getDateOccurred(story)+ "</td>");
            			out.print("<td width=\"15%\">" + storyViewModel.getFormattedDate(story.getDateAdded()) + "</td>");
            			out.print("<td class=\"center\">" + (story.hasBeenFlagged() ? "<img src=\"/chronicles/images/flag.png\">" : "") + "</td></tr>");
    			       }
				}
           		%>

        	    </table>
            </div>
            <% if ((Boolean)pageContext.findAttribute("editable")) { %>
    		<div id="favourite_stories_tab" name="favourite_stories_tab">
               	<table id="favourite_stories">
        			<tr>
		        		<th width="40%"> Title </th>
        				<th width="10%"> Author </th>
		        		<th width="20%"> City </th>
				        <th width="10%"> Date of Occurrence </th>
        				<th width="10%"> Date Added </th>
		        		<th width="10%"> Has Been Flagged </th>
        			</tr>
                   	<%
	       	            Set<Story> favouriteStories = (Set<Story>) pageContext.findAttribute("favouriteStories");
                   		int i = 0;
	       	            for (Story story : favouriteStories) {
	       	            	if(!story.getDeleted()) {
	                   			out.println("<tr " + storyViewModel.getTableRowClass(i++) + ">");
				            	out.print("<td width=\"40%\"><a href=\"/chronicles/story/" + story.getId() + "\">"+  story.getTitle() +"</a></td>"); 
	            				out.print("<td width=\"10%\"><a href=\"/chronicles/profile/"+story.getAuthor().getUsername()+"\">"+ story.getAuthor().fullName() + "</a></td>");
				            	out.print("<td width=\"20%\">" + story.getCity() + "</td>");
	            				out.print("<td width=\"10%\">" + storyViewModel.getDateOccurred(story)+ "</td>");
				            	out.print("<td width=\"10%\">" + storyViewModel.getFormattedDate(story.getDateAdded()) + "</td>");
				            	out.print("<td class=\"center\">" + (story.hasBeenFlagged() ? "<img src=\"/chronicles/images/flag.png\">" : "") + "</td></tr>");
	       	            	}
			            }
                   	%>
                </table>
           	</div>
           	<% } %>
        </div>
        <script type="text/javascript">
            $(function() {
                $("#story_list_tabs").tabs();
            });
        </script>
    </body>
</html>
