<#import "layout.ftlh" as main>
<@main.layout title="Новый пароль">
    <div class="w-50 mx-auto mt-5">
        <div class="card shadow-sm p-4">
            <h2 class="mb-4 text-center">Придумайте новый пароль</h2>
            <form action="/reset-password" method="post">
                <#if _csrf??><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/></#if>
                <input type="hidden" name="token" value="${token}">
                <div class="mb-3">
                    <input type="password" name="password" class="form-control" placeholder="Новый пароль" required>
                </div>
                <button type="submit" class="btn btn-success w-100">Сохранить пароль</button>
            </form>
        </div>
    </div>
</@main.layout>