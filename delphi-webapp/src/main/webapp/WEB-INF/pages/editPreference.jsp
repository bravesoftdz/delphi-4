<%@ page language="java" pageEncoding="UTF-8" %>
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
    <c:set var="user" value="${sessionScope.presentationUser}"/>
    
    <c:if test="${not empty user}">
      <c:set var="preference" value="${user.userPreference}" />
      
      <h3 class="preference">Edit Preferences</h3>
      <div class="preferences">
        <form action="/preference/edit/save" method="post" class="editPreference" onsubmit="return validatePreferenceForm(this);">
          <label>Frequency: </label>
          <select id="frequencyId" name="frequency">
            <c:forEach var="idx" begin="1" end="3" step="1">
              <option value="${idx}">${idx == 1 ? "Once a Day" : (idx == 2 ? "Twice a Day" : "Three Times a Day")}</option>
            </c:forEach>
          </select>
          <br/>
        
          <c:set var="maxPerTag" value="${preference.maxPerTag}"/>
          
          <label>Number of Results per Source: </label>
          <select id="maxPerTagId" name="maxPerTag">
            <c:forEach var="idx" begin="1" end="5" step="1">
              <c:choose>
                <c:when test="${maxPerTag == idx}">
                  <option value="${idx}" selected="selected">${idx}</option>
                </c:when>
                <c:otherwise>
                  <option value="${idx}">${idx}</option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
          <br/>
        
          <label>Notification Enabled: </label>
          <c:choose>
            <c:when test="${preference.notificationEnabled}">
              <input type="checkbox" id="notificationEnabledId" name="notificationEnabled" checked="checked"/>
            </c:when>
            <c:otherwise>
              <input type="checkbox" id="notificationEnabledId" name="notificationEnabled"/>
            </c:otherwise>
          </c:choose>
          <br/>
          
          <label>Mobile Number: </label>
          <input type="text" name="mobileNumber" value="${preference.mobileNumber}"/>&nbsp;<span id="mobileErrMsg">&nbsp;</span>
          <br/> 
        
          <label>Notification Type: </label>
          <select id="notificationTypeId" name="notificationType">
            <c:forEach var="idx" begin="0" end="2" step="1">
              <option value="${idx}">${idx == 0 ? "Email" : (idx == 1 ? "SMS" : "Both")}</option>
            </c:forEach>
          </select>
          <br/>
          
          <input type="submit" value="Submit" class="submitButton"/>
        </form> 
      </div>
    </c:if>
    
    <div class="homePageLink"><a href="/">Home Page</a></div>
  </div>
  
  <div id="footer">
    <jsp:include page="/WEB-INF/pages/fragments/footer.jsp"/>
  </div>
  
  <c:set var="frequency" value="${preference.frequency}"/>
  <c:set var="maxPerTag" value="${preference.maxPerTag}"/>
  <c:set var="notificationType" value="${preference.notificationType}"/>
  
  <script type="text/javascript">
    $(document).ready(function() {
    	$("#frequencyId").val("${frequency}");
    	$("#maxPerTag").val("${maxPerTagId}");
    	$("#notificationTypeId").val("${notificationType}");
    });
    
    function validatePreferenceForm(form) {
    	document.getElementById('mobileErrMsg').innerHTML = '';
    	var mobileNo = form['mobileNumber'].value;
    	
    	if (mobileNo == null || mobileNo == '') {
    		return true;
    	}
    	
    	if (mobileNo.length != 11){
    		document.getElementById('mobileErrMsg').innerHTML = 'Invalid Mobile Number';
    		return false;
    	}
    	
    	var result = /^\d{11}$/.test(mobileNo);   	
    	
    	if (!result) {
    		document.getElementById('mobileErrMsg').innerHTML = 'Invalid Mobile Number';
    	}
    	
    	return result;
    }
  </script>
</body>
</html>

