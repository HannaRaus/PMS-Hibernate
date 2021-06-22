<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>PMS</title>
        <script src="https://kit.fontawesome.com/1121c369ff.js" crossorigin="anonymous"></script>
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/table.css" TITLE="style" />
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/style.css" TITLE="style" />
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/button.css" TITLE="style" />
    </head>
    <body>
        <c:import url="/view/header.jsp"/>
        <c:set var="customer" value="${customer}" />
        <div class="container">
        	<table>
        		<thead>
        			<tr>
        				<th>id</th>
        				<th>name</th>
        				<th>industry</th>
        				<th>companies</th>
        				<th>projects</th>
        				<th></th>
        			</tr>
        		</thead>
        		<tbody>
                     <tr>
                         <td>${customer.id}</td>
                         <td>${customer.name}</td>
                         <td>${customer.industry}</td>
                         <td>${customer.companies}</td>
                         <td>${customer.projects}</td>
                         <td> <a href="">
                                 <button>Update</button>
                              </a>
                         </td>
                     </tr>
        		</tbody>
        	</table>
        </div>
    </body>
</html>
