<%@ page language="java" pageEncoding="UTF-8" %>
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
    
    <c:if test="${not empty user}">
      <c:set var="preference" value="${user.userPreference}"/>
      
      <c:choose>
        <c:when test="${preference.frequency == 1}">
          <c:set var="frequency" value="Once a Day"/>
        </c:when>
        <c:when test="${preference.frequency == 2}">
          <c:set var="frequency" value="Twice a Day"/>
        </c:when>
        <c:when test="${preference.frequency == 3}">
          <c:set var="frequency" value="Three Times a Day"/>
        </c:when>
      </c:choose>
      
      <c:choose>
        <c:when test="${preference.notificationType == 0}">
          <c:set var="notificationType" value="Email"/>
        </c:when>
        <c:when test="${preference.notificationType == 1}">
          <c:set var="notificationType" value="SMS"/> 
        </c:when>
        <c:when test="${preference.notificationType == 2}">
          <c:set var="notificationType" value="Email and SMS"/>
        </c:when>
      </c:choose>
      
      <h3 class="preference">Current Preferences</h3>
      <div class="preferences">
        <table class="sample">
          <tr><td>Frequency</td><td>${frequency}</td></tr>
          <tr><td>Number of Results per Source</td><td>${preference.maxPerTag}</td></tr>
          <tr><td>Notification Enabled</td><td><c:out value="${preference.notificationEnabled ? 'Yes' : 'No'}"/></td></tr>
          <tr><td>Mobile Number</td><td>${preference.mobileNumber}</td></tr>
          <tr><td>Notification Type</td><td>${notificationType}</td></tr>
        </table>
      </div>
    </c:if>
    
    <div class="homePageLink"><a href="/">Home Page</a>&nbsp;&nbsp;<a href="/preference/edit/form">Edit Preferences</a></div>
  </div>
  
  <div id="footer">
    <jsp:include page="/WEB-INF/pages/fragments/footer.jsp"/>
  </div>
</body>
</html>

