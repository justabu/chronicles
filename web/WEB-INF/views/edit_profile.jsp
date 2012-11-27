<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8"%>
<%@ taglib prefix = "form" uri = "http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ page import = "chronicles.models.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-validate.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/date-extras.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-validate-setup.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/profile-form-setup.js"></script>
        
        
        <title>Edit Profile</title>
    </head>
    <body>
    	<c:if test="${shouldDisplayEmptyFieldMessage}">
    		<div class="content_block">
    			<p>Hey stranger,</p>
    			<p>As you use Chronicles, you'll be leaving your mark by writing stories, posting comments, and updating your profile. To start things off, please tell us who you are.</p>
    		</div>
    	</c:if>
        <h1>Profile for ${user.username}</h1>
        <form:form commandName="user" id="profile_form" method="put" action="/chronicles/profile">
        	<form:input type="hidden" path="id" />
            <table>
                <tr>
                    <td>First Name<span class="important">*</span>:</td>
                    <td><form:input path="firstName" id="firstName" maxlength="255" /></td>
                </tr>
                <tr>
                    <td>Last Name<span class="important">*</span>:</td>
                    <td><form:input path="lastName" id="lastName" maxlength="255" /></td>
                </tr>
                <tr>
                    <td>Short Description:</td>
                    <td><form:textarea path="shortDescription" id="shortDescription" rows="3" cols="20" /></td>
                </tr>
                <tr>
                	<td>Home Office:</td>
                    <td><form:input path="homeOffice" maxlength="255" /></td>
                </tr>
                <tr>
                    <td>Current Office:</td>
                    <td><form:input path="currentOffice" maxlength="255" /></td>
                </tr>
                <tr>
                    <td>Date of Joining (dd/mm/yyyy):</td>
                    <fmt:formatDate var="dateJoined" value="${user.dateOfJoining}" pattern="dd/MM/yyyy" />
                    <td><form:input path="dateOfJoining" value="${dateJoined}" maxlength="10" id="dateOfJoining" /></td>
                </tr>
                <tr>
                    <td>
                        <span style="float: right;">
                        	<input type="submit" value="Save Changes" class="button" name="save" id="saveButton" />
                        </span>
                    </td>
                </tr>
            </table>
        </form:form>
        <script type="text/javascript">
 			$('#saveButton').click(function() {
 	    		$("#profile_form").validate();
 			});
		</script>
    </body>
</html>
