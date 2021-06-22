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
        <c:set var="skill" value="${skill}" />
        <div class="container">
        	<table>
        		<thead>
        			<tr>
        				<th>id</th>
        				<th>branch</th>
        				<th>level</th>
        				<th>developers</th>
        				<th></th>
        			</tr>
        		</thead>
        		<tbody>
                     <tr>
                         <td>${skill.id}</td>
                         <td>${skill.branch}</td>
                         <td>${skill.level}</td>
                         <td>${skill.developers}</td>
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
