<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>

<head>
<title>First Web Application</title>
</head>

<body>
	<%-- Here are the list of your todos:
	${todos} --%>
	Hi, ${name} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='../../logout.do'>logout</a>
	<BR/>
	Your Name is : ${name}<br/>
	Your Role is : ${role}
	<br/>
	<c:if test="${page != 'home'}" >
		JSP : ${page }
	</c:if>
	<c:if test="${page == 'home' }">
		<h2>Follow this <a href='../${url}/${url}list.do'>link</a>.</h2>
	</c:if>
	
</body>

</html>