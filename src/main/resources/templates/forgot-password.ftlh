<#import "layout.ftlh" as main>
<@main.layout title="Забыли пароль?">
    <div class="w-50 mx-auto mt-5">
        <div class="card shadow-sm p-4">
            <h2 class="mb-4 text-center">Сброс пароля</h2>
            <#if message??>
                <div class="alert alert-success text-center">${message}</div>
            </#if>
            <p class="text-muted text-center">Введите ваш email, мы отправим вам ссылку для сброса пароля.</p>
            <form action="/forgot-password" method="post">
                <#if _csrf??><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/></#if>
                <div class="mb-3">
                    <input type="email" name="email" class="form-control" placeholder="name@example.com" required>
                </div>
                <button type="submit" class="btn btn-primary w-100 mb-3">Отправить ссылку</button>
                <div class="text-center"><a href="/login" class="text-decoration-none">Вернуться ко входу</a></div>
            </form>
        </div>
    </div>
</@main.layout>