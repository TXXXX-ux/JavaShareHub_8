<#import "layout.ftlh" as main>
<@main.layout title="Мой профиль">

    <h2>Добро пожаловать, ${user.email}!</h2>
    <hr>

    <div class="row">
        <div class="col-md-4">
            <h4>Загрузить новый файл</h4>
            <form action="/profile/upload" method="post" enctype="multipart/form-data" class="card p-3">
                <#if _csrf??>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </#if>

                <div class="mb-3">
                    <label>Выберите файл:</label>
                    <input type="file" name="file" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label>Категория:</label>
                    <select name="category" class="form-select">
                        <option value="Учеба">Код</option>
                        <option value="Учеба">Игры</option>
                        <option value="Работа">Музыка</option>
                        <option value="Разное">Работа</option>
                        <option value="Разное">Разное</option>
                    </select>
                </div>

                <div class="mb-3 form-check">
                    <input type="checkbox" name="isPublic" value="true" class="form-check-input" id="isPublicCheck">
                    <label class="form-check-label" for="isPublicCheck">Сделать публичным</label>
                </div>

                <button type="submit" class="btn btn-primary">Загрузить</button>
            </form>
        </div>

    <div class="col-md-8">
        <h4>Мои файлы</h4>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Файл</th>
                <th>Статус</th>
                <th>Скачиваний</th>
                <th>Приватная ссылка</th>
                <th class="text-center align-middle">Действие</th>
            </tr>
            </thead>
            <tbody>
            <#list myFiles as file>
                <tr>
                    <td>${file.name}</td>
                    <td>${file.isPublic()?string('Публичный', 'Приватный')}</td>
                    <td>${file.downloadCount}</td>
                    <td>
                        <#if !file.isPublic()>
                            <#if file.uniqueKey??>
                                <input type="text" class="form-control form-control-sm" value="http://localhost:8089/download/private/${file.uniqueKey}" readonly>
                            <#else>
                                <span class="text-danger">Скачан (Ключ сгорел)</span>
                            </#if>
                        <#else>
                            -
                    <td class="text-center align-middle">
                        <form action="/profile/delete/${file.id}" method="post" class="m-0 d-inline-block" onsubmit="return confirm('Вы уверены, что хотите навсегда удалить этот файл?');">
                            <#if _csrf??>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </#if>
                            <button type="submit" class="btn btn-outline-danger p-2" title="Удалить файл">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16" style="width: 55px;">
                                    <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z"/>
                                    <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z"/>
                                </svg>
                            </button>
                        </form>
                    </td>
                        </#if>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

</@main.layout>