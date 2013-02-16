<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contentList" value="${requestScope.contents}" />

<c:choose>
  <c:when test="${source == 'googleNews'}">
    <h3>Search Result from Google News</h3>
  </c:when>
  <c:when test="${source == 'bing'}">
    <h3>Search Result from Bing</h3>
  </c:when>
  <c:when test="${source == 'yahoo'}">
    <h3>Search Result from Yahoo News</h3>
  </c:when>
  <c:otherwise>
    <h3>Search Result</h3>
  </c:otherwise>
</c:choose>

<c:if test="${not empty contentList}">
  <ul class="searchResult">
    <c:forEach var="content" items="${contentList}">
	  <li>
        <div class="news">
          <div class="title"><a href="${content.url}">${content.title}</a></div>
          <c:if test="${source != 'googleNews'}">
            <div class="description">${content.description}</div>
          </c:if>
          <div class="publishDate"><span class="publishLabel">published on : </span><span class="time">${content.publishDate}</span></div>
        </div>
      </li>
	</c:forEach>
  </ul>
</c:if>