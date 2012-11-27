
<%
    int flagCount = (Integer)pageContext.findAttribute("flagCount");
    String plural = (flagCount == 1 ? "" : "s");
%>
<% if((Boolean)pageContext.findAttribute("userHasFlaggedStory")) { %>
<form:form action="${story.id}/inappropriate_flag" method="delete">
    <p>
		<input type="image" src="${pageContext.request.contextPath}/images/flags_up.png"
		    width="50" height="40" alt="Unflag this story"
		    title="Unflag this story" id="inappropriate" />
		<span id="flag_message">${flagCount} user<%= plural %> flagged this story as inappropriate</span>
	</p>
</form:form>
<% } else { %>
<form:form action="${story.id}/inappropriate_flag" method="post">
    <p>
		<input type="image" src="${pageContext.request.contextPath}/images/flags_down.png"
		    width="50" height="40" alt="Flag this story"
		    title="Flag this story" id="inappropriate" />
		<span id="flag_message">${flagCount} user<%= plural %> flagged this story as inappropriate</span>
	</p>
</form:form>
<% } %>
