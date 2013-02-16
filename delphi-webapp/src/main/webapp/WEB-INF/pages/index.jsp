<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html>
<head>
  <title>Delphi</title>
  <jsp:include page="/WEB-INF/pages/fragments/headerResources.jsp"/>
</head>
<body>
  <div id="header">
    <jsp:include page="/WEB-INF/pages/fragments/header.jsp"/>
  </div>
  
  <div id="main">
    <c:set var="user" value="${sessionScope.presentationUser}"/>
    
    <c:choose>
	  <c:when test="${not empty user}">
	    <jsp:include page="/WEB-INF/pages/fragments/loggedInContent.jsp"/>	
	  </c:when>
	  <c:otherwise>		  
	    <jsp:include page="/WEB-INF/pages/fragments/loggedOutContent.jsp"/>
	  </c:otherwise>
    </c:choose>
  </div>
  
  <div id="footer">
    <jsp:include page="/WEB-INF/pages/fragments/footer.jsp"/>
  </div>
</body>
</html>
