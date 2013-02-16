<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="dp" uri="http://delphi-itec800.appspot.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="user" value="${sessionScope.presentationUser}"/>

<div id="content">
  <h3>Welcome, <c:out value="${user.nickName}"/> !</h3>
  
  <c:set var="userPreference" value="${user.userPreference}"/>
  
  <div class="tags">
    <h4>Keywords</h4>
    <c:choose>
	  <c:when test="${not empty userPreference.tags}">
	    <ul>
	      <c:forEach var="tag" items="${userPreference.tags}">
	        <li><c:out value="${tag.keyword}"/></li>
	      </c:forEach>    
	    </ul>
	  </c:when>
	  <c:otherwise>
	    <div>You have not selected any keywords yet</div>
	  </c:otherwise>
	</c:choose>    
  </div>
  
  <div class="sources">
    <H4>Sources</H4>
    <c:choose>
      <c:when test="${not empty userPreference.sources}">
        <ul>
          <c:forEach var="source" items="${userPreference.sources}">
            <li><c:out value="${source.name}"/></li>
          </c:forEach>
        </ul>
      </c:when>
      <c:otherwise>
        <div>You have not selected any sources yet</div>
      </c:otherwise>
    </c:choose>
  </div>
</div>


<div id="login"> 
  <p><a href="/preference/view">View Preferences</</a></p>
  <p><a href="/preference/edit/form">Edit Preferences</</a></p>
  <p><a href="/search/form">Search</</a></p>  
  <p><a href="/preference/edit/sources/form">Edit Sources</</a></p>  
  <p><a href="/preference/edit/tags/form">Edit Keywords</</a></p>
  
  <dp:getUrl var="logoutUrl" type="logout" destinationUrl="/logout"/> 
  <p><a href="${logoutUrl}">Logout</a></p>
</div>
