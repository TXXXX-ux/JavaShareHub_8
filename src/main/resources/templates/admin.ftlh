<#import "layout.ftlh" as main>
<@main.layout title="Панель администратора">

    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="fw-bold text-danger">
                <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" fill="currentColor" class="bi bi-shield-lock-fill me-2" viewBox="0 0 16 16">
                    <path fill-rule="evenodd" d="M8 0c-.69 0-1.843.265-2.928.56-1.11.3-2.229.655-2.887.87a1.54 1.54 0 0 0-1.044 1.262c-.596 4.477.787 7.795 2.465 9.99a11.777 11.777 0 0 0 2.517 2.453c.386.273.744.482 1.048.625.28.132.581.24.829.24s.548-.108.829-.24a7.159 7.159 0 0 0 1.048-.625 11.775 11.775 0 0 0 2.517-2.453c1.678-2.195 3.061-5.513 2.465-9.99a1.541 1.541 0 0 0-1.044-1.263 62.467 62.467 0 0 0-2.887-.87C9.843.266 8.69 0 8 0zm0 5a1.5 1.5 0 0 1 .5 2.915l.385 1.99a.5.5 0 0 1-.491.595h-.788a.5.5 0 0 1-.49-.595l.384-1.99A1.5 1.5 0 0 1 8 5z"/>
                </svg>
                Панель управления
            </h2>
        </div>

        <div class="card shadow-sm border-0 rounded-4 mb-5">
            <div class="card-header bg-white border-bottom-0 pt-4 pb-2 px-4">
                <h5 class="fw-bold mb-0">Пользователи системы</h5>
            </div>
            <div class="card-body px-4 pb-4">
                <table class="table table-hover align-middle mb-0">
                    <thead class="table-light">
                    <tr>
                        <th class="text-secondary py-3">ID</th>
                        <th class="text-secondary py-3">Email</th>
                        <th class="text-secondary py-3">Роль</th>
                        <th class="text-secondary py-3">Статус</th>
                        <th class="text-center text-secondary py-3">Действия</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list users as u>
                        <tr>
                            <td class="fw-semibold text-muted">#${u.id}</td>
                            <td class="fw-bold text-dark">${u.email}</td>
                            <td>
                                <#if u.role == "ROLE_ADMIN">
                                    <span class="badge bg-danger">Администратор</span>
                                <#else>
                                    <span class="badge bg-primary">Пользователь</span>
                                </#if>
                            </td>
                            <td>
                                <#if u.enabled>
                                    <span class="badge bg-success bg-opacity-25 text-success border border-success rounded-pill px-3">Активен</span>
                                <#else>
                                    <span class="badge bg-secondary bg-opacity-25 text-secondary border border-secondary rounded-pill px-3">Забанен</span>
                                </#if>
                            </td>
                            <td class="text-center">
                                <form action="/admin/user/toggle/${u.id}" method="post" class="d-inline">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <button type="submit" class="btn btn-sm ${u.enabled?string('btn-outline-danger', 'btn-outline-success')}">
                                        ${u.enabled?string('Заблокировать', 'Разблокировать')}
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</@main.layout>