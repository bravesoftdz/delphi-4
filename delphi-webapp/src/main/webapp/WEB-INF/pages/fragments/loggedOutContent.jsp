<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="dp" uri="http://delphi-itec800.appspot.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="content">
  <h3>Welcome !</h3>
  <p>The portal service allows you to save a number of tags and then search for relevant content
  based on those tags.</p>
  
  <p>You may also subscribe to receive regular updates on your Inbox or Mobile device.</p>
  
  <p>At present, content can be fetched from the following sources : </p>
  
  <ul>
    <li>The Guardian</li>
    <li>Youtube</li>
    <li>Twitter</li>
    <li>Flickr</li>
    <li>Google News</li>
    <li>Yahoo News</li>
    <li>Bing News Search</li>
  </ul>
  
</div>
<div id="login">
  <h3>Login</h3>
  
  <dp:getUrl var="loginUrl" destinationUrl="/login" type="login"/>
      
  <c:if test="${not empty loginUrl}">
    <p>Click <a href="${loginUrl}">here</a> to login</p>
  </c:if>  
</div>