<% 
	List<Story> stories = (List<Story>) pageContext.findAttribute("stories");
	if(stories.size()>0) { 
%>

<table id="browse_stories_table" cellspacing="0">
	<tr class="recent_stories_border">
		<th> Title </th>
		<th> Author </th>
		<th> City </th>
		<th> Date of Occurrence </th>
		<th> Date Added </th>
		<th> Flag </th>
	</tr>
<% } else { %>
<%@include file="/WEB-INF/views/empty_search_results.jsp" %>
<% } %>
<% 
	StoryViewModel storyViewModel = new StoryViewModel();

	for(int i=0 ; i< stories.size();i++) {
		Story currentStory = stories.get(i);
			if(!currentStory.getDeleted()) {
			    out.println("<tr " + storyViewModel.getTableRowClass(i) + ">");
				out.print("<td><a href=\"/chronicles/story/" + currentStory.getId() + "\">"+  currentStory.getTitle() +"</a></td>"); 
				out.print("<td><a href=\"/chronicles/profile/"+currentStory.getAuthor().getUsername()+"\">"+ currentStory.getAuthor().fullName() + "</a></td>");
				out.print("<td>" + currentStory.getCity() + "</td>");
				out.print("<td>" + storyViewModel.getDateOccurred(currentStory)+ "</td>");
				out.print("<td>" + storyViewModel.getFormattedDate(currentStory.getDateAdded()) + "</td>");
				out.print("<td class=\"center\">" + (currentStory.hasBeenFlagged() ? "<img src=\"/chronicles/images/flag.png\">" : "") + "</td></tr>");
			}
		}
%>
</table>