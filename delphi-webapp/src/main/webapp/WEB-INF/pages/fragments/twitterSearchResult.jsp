<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contentList" value="${requestScope.contents}" />

<h3>Twitter Search Result</h3>

<c:if test="${not empty contentList}">
  <ul class="searchResult">
    <c:forEach var="content" items="${contentList}">
      <li>
        <div class="tweet">
          <div class="image">
            <a href="${content.authorUrl}">
              <img src="${content.authorPicUrl}" width="60" height="60"/>
            </a>
          </div>
          <div class="content">
            <div class="author">
              <span class="authorname">${content.authorName}</span> on <span class="time">${content.publishDate}</span>
            </div>
            <div class="text">
              <a href="${content.url}">${content.title}</a>
            </div>
          </div>
          <div class="empty">&#160;</div>
        </div>
      </li>
	</c:forEach>
  </ul>
</c:if>