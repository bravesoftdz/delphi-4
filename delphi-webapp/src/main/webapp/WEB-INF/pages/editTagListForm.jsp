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
    <div id="tags">
      <h3>Keywords</h3>
      
      <div class="list">
        <ul id="taglist">
          <c:forEach var="tag" items="${fn:split(selectedTagNames, '|')}">
            <c:if test="${not empty tag}">
              <li title="${tag}"><span>${tag}</span>&nbsp;&nbsp;<img src="/resources/gfx/delete-icon.png" class="tag-remove-button" width="15" height="15"/></li>
            </c:if>
          </c:forEach>
        </ul>
      </div>
      
      <div id="tagform">
        Keyword <input type="text" name="tag" id="tagfield"/>&nbsp;<input type="button" value="Add Keyword" id="tagbutton"/>
      </div> 
     
      <div><span id="tagErrId" class="error"></span></div>
      <div class="homePageLink"><a href="/">Home Page</a></div> 
    </div>
  </div>
  
  <div id="footer">
    <jsp:include page="/WEB-INF/pages/fragments/footer.jsp"/>
  </div>
  
  <script type="text/javascript">
    $(document).ready(function() {
    	removeHandler = function() {
    		var tagName = $(this).parent().attr("title");
    		
    		$.post("/preference/edit/tags/remove", {
				tagName : tagName
			}, function(data) {
				data = $.trim(data);				
			});	
    		
			$(this).parent().remove();
    	};
    	
    	$(".tag-remove-button").click(removeHandler);
    	
    	$("#tagfield").keyup(function(event) {
    	  	  if (event.keyCode == 13) {
    	  	    $("#tagbutton").click();
    	  	  }
    	});
    	  	
   	  	$("#tagbutton").click(function() {
   	  		$("#tagErrId").text('');
   	  		
   	  		var tag = $("#tagfield").val();
   	  		
   	  		if (tag != '') {
   	  			$.post("/preference/edit/tags/add", {
   	  				tag : tag
   	  			}, function(data) {
   	  				data = $.trim(data);
   	  				
   	  				if (data != "success") {
   	  					$("#tagErrId").text(data);
   	  					return;
   	  				}
   	  				
   	  				$("#taglist").append("<li title='" + tag + "'><span>" + tag + "</span>&nbsp;&nbsp;<img src='/resources/gfx/delete-icon.png' class='tag-remove-button' width='15' height='15'/></li>");
   	  				$("#taglist").on('click', 'li > img.tag-remove-button', removeHandler);
   	  			});
   	  		} else if (tag == '') {
   	  			$("#tagErrId").text("Please sepcify your keyword");
   	  		}
   	  		
   	  		$("#tagfield").val("");
   	  	});
    });
  </script>
</body>
</html>