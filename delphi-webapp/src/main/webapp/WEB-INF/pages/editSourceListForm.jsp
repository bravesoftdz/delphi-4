<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
    <h3>Sources</h3>
    <form action="/preference/edit/sources/save" method="POST" class="sourceForm">
      <c:forEach var="sourceName" items="${fn:split(predefinedSourceNames, ' ')}">
        <c:choose>
          <c:when test="${fn:contains(selectedSourceNames, sourceName)}">
            <input type="checkbox" name="sourceName" value="${sourceName}" checked="checked"/> ${sourceName} <br/>
          </c:when>
          <c:otherwise>
            <input type="checkbox" name="sourceName" value="${sourceName}"/> ${sourceName} <br/>
          </c:otherwise>
        </c:choose> 
      </c:forEach>
      
      <input type="submit" value="Submit" class="submitButton"/>
    </form>
    
    <div class="homePageLink"><a href="/">Home Page</a></div>
  </div>
  
  <div id="footer">
    <jsp:include page="/WEB-INF/pages/fragments/footer.jsp"/>
  </div>
</body>
</html>