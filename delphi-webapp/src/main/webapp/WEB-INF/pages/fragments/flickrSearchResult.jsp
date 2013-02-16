<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contentList" value="${requestScope.contents}" />

<c:if test="${not empty contentList}">
  <ul class="searchResult">
    <c:forEach var="content" items="${contentList}">
      <li>
        <div class="flickr-photo">
          <div class="image">
            <img src="${content.url}" width="500"/>
          </div>
          <div class="title">
            <a href="${content.url}">${content.title}</a>
          </div>
        </div>     
      </li>
	</c:forEach>
  </ul>
</c:if>