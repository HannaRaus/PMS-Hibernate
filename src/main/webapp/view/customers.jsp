<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>PMS</title>
        <script src="https://kit.fontawesome.com/1121c369ff.js" crossorigin="anonymous"></script>
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/style.css" TITLE="style" />
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/table.css" TITLE="style" />
        <style>
        a:link {
          color: #003559;
          background-color: transparent;
          text-decoration: none;
        }

        a:visited {
          color: #efcbcb;
          background-color: transparent;
          text-decoration: none;
        }

        a:hover {
          color: red;
          background-color: transparent;
          text-decoration: underline;
        }

        a:active {
          background-color: transparent;
          text-decoration: underline;
        }
        </style>
    </head>
    <body>
        <c:import url="/view/header.jsp"/>
        <div class="container">
        	<table>
        		<thead>
        			<tr>
        				<th>id</th>
        				<th>name</th>
        				<th>industry</th>
        				<th>companies</th>
        				<th>projects</th>
        			</tr>
        		</thead>
        		<tbody>
                 <c:forEach var="customer" items="${customers}">
                     <tr>
                         <td>${customer.id}</td>
                         <td class="link"> <a href="/customers/findById?id=${customer.id}">${customer.name}</a></td>
                         <td>${customer.industry}</td>
                         <td>${customer.companies.size()}</td>
                         <td>${customer.projects.size()}</td>
                     </tr>
                 </c:forEach>
        		</tbody>
        	</table>
        </div>
    </body>
</html>
/*

