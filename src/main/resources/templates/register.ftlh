<#import "layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout title="Регистрация">

    <div class="w-50 mx-auto mt-5">
        <h2 class="mb-3">Регистрация</h2>

        <form action="/register" method="post">
            <#if _csrf??>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </#if>

            <div class="mb-3">
                <label>Email</label>
                <@spring.formInput "userDto.email" 'class="form-control"'/>
                <@spring.showErrors "<br>" "text-danger"/>
            </div>

            <div class="mb-3">
                <label>Пароль</label>
                <@spring.formPasswordInput "userDto.password" 'class="form-control"'/>
                <@spring.showErrors "<br>" "text-danger"/>
            </div>

            <button type="submit" class="btn btn-success w-100">Зарегистрироваться</button>
        </form>
    </div>

</@main.layout>