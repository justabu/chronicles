<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8"%>
<%@ page import = "chronicles.models.Story"%>
<% Story story = (Story)pageContext.findAttribute("story"); %>

<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/tiny_mce/tiny_mce.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/tinyMCEsetup.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.js"></script>
	 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-validate.js"></script>
	 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/date-extras.js"></script>
	 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-validate-setup.js"></script>
	 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/story-form-setup.js"></script>
	 	<title><%= story.getTitle() %></title>
		<link href="${pageContext.request.contextPath}/css/chronicles.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div id="edit_story_container">
			<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
			<form:form commandName="story" id="story_form" method="PUT">
				<input type="hidden" name="_method" value="PUT"></input>
				<form:input type="hidden" path="id" value="<%= story.getId() %>" />
				<ul>
					<li>
						<label for="title">Title<font color=red>* </font></label>
						<form:input path="title" id="title" type="text" name="title" value="<%= story.getTitle() %>" />
					</li>
					<li>
						<label for="year">Year<font color=red>* </font></label>
						<form:input path="year" id="year" type="text" name="year" value="<%= story.getYear() %>" />
					</li>
					<li>
						<label for="month">Month</label>
						<form:select path="month" id="month" items="${monthOptions}" />
					</li>
					<li>
						<label for="city">City<font color=red>* </font></label>
						<form:input path="city" id="city" maxlength="50" name="city" value="<%= story.getCity() %>" />
					</li>
					<li>
						<label for="content">Story<font color=red>* </font></label>
						<form:textarea path="content" id="content" name="content" rows="20" cols="80" value="<%= story.getContent() %>" />
					</li>
					<div class="right_float">
						<input type="submit" value="Publish" class="button" name="publish" id="publishButton" />
					</div>
				</ul>
			</form:form>
		</div>
		<script type="text/javascript">
 			$('#publishButton').click(function() {
 	    		var content = tinyMCE.activeEditor.getContent(); // get the content
 	    		$('#content').val(content); // put it in the textarea
 			});
		</script>
	</body>
</html>
