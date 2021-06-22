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
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/button.css" TITLE="style" />
    </head>
    <body>
        <c:import url="/view/header.jsp"/>
        <div class="container">
        	<table>
        		<thead>
        			<tr>
        				<th>id</th>
        				<th>name</th>
        				<th>headquarters</th>
        				<th>customers</th>
        				<th>projects</th>
        				<td colspan="2" align="center"></td>
        			</tr>
        		</thead>
        		<tbody>
                 <c:forEach var="company" items="${companies}">
                     <tr>
                         <td>${company.id}</td>
                         <td>${company.name}</td>
                         <td>${company.headquarters}</td>
                         <td>
                             <div class="tooltip">${company.customers.size()}
                               <span class="tooltiptext">${company.customers}</span>
                             </div>
                         </td>
                         <td>
                             <div class="tooltip">${company.projects.size()}
                               <span class="tooltiptext">${company.projects}</span>
                             </div>
                         </td>
                         <td> <a href="/companies/findById?id=${company.id}">
                                 <button>Details</button>
                              </a>
                         </td>
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
