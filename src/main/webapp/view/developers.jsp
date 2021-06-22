<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>PMS</title>
        <script src="https://kit.fontawesome.com/1121c369ff.js" crossorigin="anonymous"></script>
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/table.css" TITLE="style" />
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/style.css" TITLE="style" />
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/tooltip.css" TITLE="style" />
    </head>
    <body>
        <c:import url="/view/header.jsp"/>
        <div class="container">
        	<table>
        		<thead>
        			<tr>
        				<th>id</th>
        				<th>first Name</th>
        				<th>last Name</th>
        				<th>sex</th>
        				<th>salary</th>
        				<th>skills</th>
        				<th>projects</th>
        				<th></th>
        			</tr>
        		</thead>
        		<tbody>
                 <c:forEach var="developer" items="${developers}">
                     <tr>
                         <td>${developer.id}</td>
                         <td>${developer.firstName}</td>
                         <td>${developer.lastName}</td>
                         <td>${developer.sex}</td>
                         <td>${developer.salary}</td>
                         <td>
                             <div class="tooltip">${developer.skills.size()}
                               <span class="tooltiptext">${developer.skills}</span>
                             </div>
                         </td>
                         <td>
                             <div class="tooltip">${developer.projects.size()}
                               <span class="tooltiptext">${developer.projects}</span>
                             </div>
                         <td> <a href="">
                                 <button>Update</button>
                              </a>
                         </td>
                     </tr>
                 </c:forEach>
        		</tbody>
        	</table>
        </div>
    </body>
</html>
