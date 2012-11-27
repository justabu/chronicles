<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/tiny_mce/tiny_mce.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/tinyMCEsetup.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-validate.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/date-extras.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-validate-setup.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/story-form-setup.js"></script>
        <meta http-equiv = "Content-Type" content = "text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/chronicles.css" rel="stylesheet" type="text/css">
        <title>Add a story</title>
    </head>

    <body id="add_story">
        <div id="edit_story_container">
            <form:form commandName="story" id="story_form" action="/chronicles/story">
                <ul>
                    <li>
						<label for="title">Title<font color=red>* </font></label>
						<form:input path="title" id="title" maxlength="255" />
						<div class="clearer"></div>
					</li>
					<li>
						<div class="left_float">
                        	<label for="month">Month</label>
                        	<form:select path="month" id="month" items="${monthOptions}" name="month" />
						</div>
						<div class="right_float year_container">
							<label for="year">Year<font color=red>* </font></label>
                        	<form:input path="year" id="year" />
						</div>
						<div class="clearer"></div>
                    </li>
                    <li>
                        <label for="city">City<font color=red>* </font></label>
                        <form:input path="city" id="city" maxlength="50"/>
                    </li>
                    <li>
                        <label for="content">Story<font color=red>* </font></label>
                        <form:textarea path="content" id="content" name="content" />
                    </li>
					<li>
						<div class="right_float">
                        	<input type="submit" value="Publish" class="button" name="publish" id="publishButton" />
						</div>
						<div class="clearer"></div>
                    </li>
                </ul>
            </form:form>
        </div>
        <script type="text/javascript">
            $('#publishButton').click(function() {
                var content = tinyMCE.activeEditor.getContent(); // get the content
                $('#content').val(content);                      // put it in the textarea
                $("#story_form").validate();
			});
        </script>
    </body>
</html>