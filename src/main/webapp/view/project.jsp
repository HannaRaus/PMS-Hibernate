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
        <c:set var="project" value="${project}" />
        <div class="container">
        	<table>
        		<thead>
        			<tr>
        				<th>id</th>
        				<th>name</th>
        				<th>description</th>
        				<th>cost</th>
        				<th>date</th>
        				<th>customers</th>
        				<th>companies</th>
        				<th>developers</th>
        				<th></th>
        			</tr>
        		</thead>
        		<tbody>
                     <tr>
                         <td>${project.id}</td>
                         <td>${project.name}</td>
                         <td>${project.description}</td>
                         <td>${project.cost}</td>
                         <td>${project.date}</td>
                         <td>${project.customers}</td>
                         <td>${project.companies}</td>
                         <td>${project.developers}</td>
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
