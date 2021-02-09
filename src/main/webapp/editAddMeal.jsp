<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>${param.action == 'save' ? 'Create meal' : 'Edit meal'}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${param.action == 'save' ? 'Create meal' : 'Edit meal'}</h2>
    <hr>
    <section>
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
        <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="id" value="${meal.id}">
            <dl>
                <dt>Дата/Время:</dt>
                <dd><input type="datetime-local" name="date" value=${meal.dateTime}></dd>
            </dl>
            <dl>
                <dt>Описание:</dt>
                <dd><input type="text" name="description" size="50" value="${meal.description}"></dd>
            </dl>
            <dl>
                <dt>Калории:</dt>
                <dd><input type="number" name="calories" size="50" value="${meal.calories}"></dd>
            </dl>
            <button type="submit">Save</button>
            <button onclick="window.history.back()">Back</button>
        </form>
    </section>
    <jsp:include page="fragments/footer.jsp"/>
</body>
</html>