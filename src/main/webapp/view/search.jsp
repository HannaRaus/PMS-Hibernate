<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>PMS</title>
        <meta charset="UTF-8">
        <script src="https://kit.fontawesome.com/1121c369ff.js" crossorigin="anonymous"></script>
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/search.css" TITLE="style" />
    </head>
    <body>
        <div class="cover">
          <form  class="flex-form">
            <label for="from">
            </label>
            <input type="number" placeholder="Enter project id">
            <input type="submit" value="Search">
          </form>
        </div>
    </body>
</html>