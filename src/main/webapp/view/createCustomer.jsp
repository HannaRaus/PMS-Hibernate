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
    </head>
    <body>
        <c:import url="/view/header.jsp"/>
        <div>
          <form name="customerForm" method="post" action="/customers">
            <label for="name">Customer name</label>
            <input type="text" id="name" name="name" placeholder="Customer name.." />

            <label for="industry">Customer industry</label>
            <input type="text" id="industry" name="industry" placeholder="Customer industry.." />

            <input type="submit" value="Create" />
          </form>
        </div>
    </body>
</html>
