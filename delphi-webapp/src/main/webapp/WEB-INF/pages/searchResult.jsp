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
    <c:choose>
      <c:when test="${source == 'guardian'}">
        <jsp:include page="fragments/guardianSearchResult.jsp"/>
      </c:when>
      <c:when test="${source == 'twitter'}">
        <jsp:include page="fragments/twitterSearchResult.jsp"/>
      </c:when>
      <c:when test="${source == 'youtube'}">
        <jsp:include page="fragments/youTubeSearchResult.jsp"/>
      </c:when>
      <c:when test="${source == 'flickr'}">
       <jsp:include page="fragments/flickrSearchResult.jsp"/>
      </c:when>
      <c:otherwise>
        <jsp:include page="fragments/rssSearchResult.jsp"/>
      </c:otherwise>
    </c:choose>
	
	<c:if test="${not empty requestScope.contents}">
	  <div class="instantNotificationButtons">
	    <button id="sendEmailButton">Send Email</button>&nbsp;&nbsp;<c:if test="${not empty sessionScope.presentationUser.userPreference.mobileNumber}"><button id="sendSmsButton">Send SMS</button></c:if>
	    <div id="notificationStatusId"></div>
	  </div>  
	</c:if>
	
	<div class="homePageLink"><a href="/">Home Page</a>&nbsp;&nbsp;&nbsp;<a href="/search/form">Perform Another Search</a></div>
  </div>
  
  <div id="footer">
    <jsp:include page="/WEB-INF/pages/fragments/footer.jsp"/>
  </div>
  
  <script type="text/javascript">
  $(document).ready(function() {
		$("#sendEmailButton").click(function() {
			$("#sendEmailButton").attr('disabled', 'disabled');
			$("#notificationStatusId").text('');
			
			$.post("/instantNotification", {
				source : '${requestScope.source}',
				keyword : '${requestScope.keyword}',
				notificationType : 0
			}, function(data) {
				data = $.trim(data);
				$("#notificationStatusId").append(data);
			});		
		});
		
		$("#sendSmsButton").click(function() {
			$("#sendSmsButton").attr('disabled', 'disabled');
			$("#notificationStatusId").text('');
			
			$.post("/instantNotification", {
				source : '${requestScope.source}',
				keyword : '${requestScope.keyword}',
				notificationType : 1
			}, function(data) {
				data = $.trim(data);
				$("#notificationStatusId").append(data);
			});		
		});
	});
  </script>
</body>
</html>
