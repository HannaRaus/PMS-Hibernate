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
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/form.css" TITLE="style" />
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/checkbox.css" TITLE="style" />
    </head>
    <body>
        <c:import url="/view/header.jsp"/>
        <div>
            <form method="post" action="update">
                <input type="hidden" name="id" value='${company.id}' />
                <label for="name">Company name</label>
                <input type="text" id="name" name="name" value="<c:out value='${company.name}' />" />
                <label for="country">Company headquarters</label>
                <input type="text" id="country" name="headquarters" value="<c:out value='${company.headquarters}'/>" />

                <label for="customers">Customers</label>
                <div class="check">
                    <ul id="customers" class="ks-cboxtags">
                        <c:forEach var="customer" items="${customers}">
                        <li>
                          <input type="checkbox" id="customer${customer.id}" value="${customer.id}">
                          <label for="customer${customer.id}">${customer.name}</label>
                        </li>
                        </c:forEach>
                    </ul>
                </div>

                <label for="customers">Projects</label>
                <div class="check">
                    <ul id="projects" class="ks-cboxtags">
                        <c:forEach var="project" items="${projects}">
                        <li>
                          <input type="checkbox" id="project${project.id}" value="${project.id}">
                          <label for="project${project.id}">${project.name}</label>
                        </li>
                        </c:forEach>
                    </ul>
                </div>

                <input type="submit" value="Update" />
            </form>
        </div>
    </body>
</html>
