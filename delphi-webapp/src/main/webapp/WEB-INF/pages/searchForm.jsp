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
    <h3>Search for Content</h3>
    
    <form action="/search/result" method="GET">
      <table>
        <tr><td><label>Search For: </label></td><td><input type="text" name="keyword"/></td></tr>
        <tr><td><label>Source: </label></td>
            <td>
               <select name="sourceName">
                 <option value="guardian">The Guardian</option>
                 <option value="twitter">Twitter</option>
        		 <option value="yahoo">Yahoo News</option>
        		 <option value="youtube">YouTube</option>
        		 <option value="flickr">Flickr</option>
                 <option value="googleNews">Google News</option>
        		 <option value="bing">Bing News Search</option>
               </select>
             </td>
         </tr>
      </table>
            
      <input type="submit" value="Submit" class="submitButton"/>
    </form>
    
    <div class="homePageLink"><a href="/">Home Page</a></div>
  </div>
  
  <div id="footer">
    <jsp:include page="/WEB-INF/pages/fragments/footer.jsp"/>
  </div>
</body>
</html>
