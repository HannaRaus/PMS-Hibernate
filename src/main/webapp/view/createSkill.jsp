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
          <form name="skillForm" method="post" action="/skills">

            <label for="branch">Branch</label>
            <select id="branch" name="branch">
                <option value="JAVA">Java</option>
                <option value="CPLUS">C++</option>
                <option value="CSHARP">C#</option>
                <option value="JS">JS</option>
            </select>

            <label for="level">Level</label>
            <select id="level" name="level">
                <option value="JUNIOR">Junior</option>
                <option value="MIDDLE">Middle</option>
                <option value="SENIOR">Senior</option>
            </select>

            <input type="submit" value="Create" />
          </form>
        </div>
    </body>
</html>
