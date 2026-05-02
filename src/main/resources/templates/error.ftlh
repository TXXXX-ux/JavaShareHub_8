<#import "layout.ftlh" as main>
<@main.layout title="Ошибка">

    <div class="alert alert-danger text-center mt-5">
        <h1 class="display-1">${status!}</h1>
        <h2>${reason!}</h2>
        <p class="lead">${message!}</p>
        <hr>
        <p>Ссылка, вызвавшая ошибку: <strong>${details!}</strong></p>
        <a href="/" class="btn btn-outline-danger mt-3">Вернуться на главную</a>
    </div>

</@main.layout>