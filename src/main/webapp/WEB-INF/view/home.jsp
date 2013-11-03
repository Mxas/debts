<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>WIX.COM</title>
</head>
<body>

<form:form method="post" action="add" commandName="category">
 
    <table>
    <tr>
        <td>Category name: </td>
        <td><form:input path="description" /></td>
    </tr>
    <tr>
        <td>Category parent: </td>
        <td>
           <form:select  path="parentId" >
               <form:option value="" label="--- Select ---"/>
               <c:forEach items="${list}" var="cat">
                 <form:option value="${cat.id}" label="${cat.description}"/>
               </c:forEach>
           </form:select>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" value="Add"/>
        </td>
    </tr>
</table> 
</form:form>


<h3>Categories</h3>
<c:if  test="${!empty list}">
	<table class="data">
	<tr>
	    <th>Name</th>
	    <th>Parent</th>
	    <th>&nbsp;</th>
	</tr>
	<c:forEach items="${list}" var="cat">
	    <tr>
	        <td>${cat.description} </td>
	        <td>${cat.parent.description} </td>
	        <td><a href="delete/${cat.id}">delete</a></td>
	    </tr>
	</c:forEach>
	</table>
</c:if>

<h3>Categories Recursive</h3>
<c:if  test="${!empty listRecursive}">
	<c:forEach items="${listRecursive}" var="cat">
	    <div>
	        <c:forEach begin="1" end="${cat.indent}" step="1" > - </c:forEach>
	        ${cat.description} 
	    </div>
	</c:forEach>
</c:if>

<h3>Categories Iterative</h3>
<c:if  test="${!empty listIterative}">
	<c:forEach items="${listIterative}" var="cat">
	    <div>
	        <c:forEach begin="1" end="${cat.indent}" step="1" > - </c:forEach>
	        ${cat.description} 
	    </div>
	</c:forEach>
</c:if>

</body>