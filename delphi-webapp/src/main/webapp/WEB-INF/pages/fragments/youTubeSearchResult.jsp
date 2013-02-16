<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contentList" value="${requestScope.contents}" />

<h3>YouTube Search Result</h3>

<c:if test="${not empty contentList}">
  <ul class="searchResult">
    <c:forEach var="content" items="${contentList}" varStatus="status">
      <c:choose>
        <c:when test="${status.last}">
          <c:set var="className" value="youtube-video last-video"/>
        </c:when>
        <c:otherwise>
          <c:set var="className" value="youtube-video"/>
        </c:otherwise>
      </c:choose>
      
      <div class="${className}">
         <div class="title">
           <a href="${content.url}">${content.title}</a>
         </div>
         <div class="author">
           Published by : <span class="authorname"><a href="${content.authorUrl}">${content.authorName}</a></span> on <span class="time">${content.publishDate}</span>
         </div>
         <div class="embedded-video">
           <iframe class="youtube-player" type="text/html" width="450" height="270" src="http://www.youtube.com/embed/${content.id}" frameborder="0">
           </iframe>
         </div>
      </div>     
	</c:forEach>
  </ul>
</c:if>