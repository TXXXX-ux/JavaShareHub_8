<#macro layout title>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>${title}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
        <div class="container">
            <a class="navbar-brand" href="/">JavaShareHub</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav ms-auto">

                    <#if !SPRING_SECURITY_CONTEXT??>
                        <li class="nav-item"><a class="nav-link" href="/login">Вход</a></li>
                        <li class="nav-item"><a class="nav-link" href="/register">Регистрация</a></li>

                    <#else>
                        <li class="nav-item"><a class="nav-link" href="/profile">Мой профиль</a></li>
                        <li class="nav-item">
                            <form action="/logout" method="post" class="d-inline">
                                <#if _csrf??>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </#if>
                                <button type="submit" class="btn btn-link nav-link">Выйти</button>
                            </form>
                        </li>
                    </#if>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">
        <#nested>
    </div>

    </body>
    </html>
</#macro>