<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contentList" value="${requestScope.contents}" />

<h3>Guardian Search Result</h3>

<c:if test="${not empty contentList}">
  <ul class="searchResult">
    <c:forEach var="content" items="${contentList}">
      <li>
        <div class="news">
          <div class="title"><a href="${content.url}">${content.title}</a></div>
          <div class="publishDate"><span class="publishLabel">published on : </span><span class="time">${content.publishDate}</span></div>
        </div>
      </li>
    </c:forEach>
  </ul>
</c:if>