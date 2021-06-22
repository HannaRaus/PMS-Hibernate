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
          <form name="developerForm" method="post" action="/developers">
            <label for="firstName">First name</label>
            <input type="text" id="firstName" name="firstName" placeholder="First name.." />

            <label for="lastName">Last name</label>
            <input type="text" id="lastName" name="lastName" placeholder="Last name.." />

            <label for="sex">Gender</label>
            <select id="sex" name="sex">
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
            </select>

            <label for="salary">Salary</label>
            <input type="number" step="0.01" id="salary" name="salary" placeholder="Salary.." />

            <input type="submit" value="Create" />
          </form>
        </div>
    </body>
</html>
