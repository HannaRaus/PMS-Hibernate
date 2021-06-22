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
          <form name="projectForm" method="post" action="/projects">
            <label for="name">Project name</label>
            <input type="text" id="name" name="name" placeholder="Project name.." />

            <label for="description">Project industry</label>
            <input type="text" id="description" name="description" placeholder="Project description.." />

            <label for="cost">Project cost</label>
            <input type="number" step="0.01" id="cost" name="cost" placeholder="Project cost.." />

            <input type="submit" value="Create" />
          </form>
        </div>
    </body>
</html>
