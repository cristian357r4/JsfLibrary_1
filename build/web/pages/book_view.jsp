<%-- 
    Document   : book_view
    Created on : 28.03.2017, 22:54:41
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            long bookId = -1;
            if (request.getParameter("index") != null)
                bookId = Long.valueOf(request.getParameter("index"));


            String referer = request.getHeader("referer");

        %>
        <p><a href="<%=referer%>">< < вернуться</a></p>
        <iframe src="<%=request.getContextPath()%>/PdfContent?index=<%=bookId%>" width="100%" height="650" alt="Попробуйте в другом браузере"></iframe>
    </body>
</html>
