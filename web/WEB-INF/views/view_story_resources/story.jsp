<h2>${story.title}</h2>

<div class="story_meta_info">
    <p>
	    Posted by 
	    <span class="name">
	    	<a href="${pageContext.request.contextPath}/profile/${user.username}">${userFullName}</a>
	    </span>
	    on <fmt:formatDate value="${story.dateAdded}" pattern="dd MMMM yyyy"/>
	</p>

    <c:set var="month" value="${story.month == null ? '' : story.month}" />

    <p>Event date: ${month}${story.year}</p>
    <p> City: ${story.city}</p>
</div>

<div class="story_content">
   ${story.content}
</div>