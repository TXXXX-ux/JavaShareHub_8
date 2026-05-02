<#import "layout.ftlh" as main>
<@main.layout title="Главная - Файлы">

    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="fw-bold" style="color: #2c3e50;">Публичные файлы</h2>
        </div>

        <div class="card shadow-sm border-0 rounded-4 mb-4">
            <div class="card-header bg-white border-bottom-0 pt-4 pb-2 px-4">
                <form action="/" method="get" class="d-flex w-50">
                    <div class="input-group shadow-sm">
                        <select name="category" class="form-select border-primary">
                            <option value="">Все категории</option>
                            <option value="Код" <#if category?? && category == "Код">selected</#if>>Код</option>
                            <option value="Игры" <#if category?? && category == "Игры">selected</#if>>Игры</option>
                            <option value="Музыка" <#if category?? && category == "Музыка">selected</#if>>Музыка</option>
                            <option value="Работа" <#if category?? && category == "Работа">selected</#if>>Работа</option>
                            <option value="Разное" <#if category?? && category == "Разное">selected</#if>>Разное</option>
                        </select>
                        <button type="submit" class="btn btn-primary px-4 fw-bold">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-funnel-fill me-1" viewBox="0 0 16 16">
                                <path d="M1.5 1.5A.5.5 0 0 1 2 1h12a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-.128.334L10 8.692V13.5a.5.5 0 0 1-.342.474l-3 1A.5.5 0 0 1 6 14.5V8.692L1.628 3.834A.5.5 0 0 1 1.5 3.5v-2z"/>
                            </svg>
                            Фильтр
                        </button>
                    </div>
                </form>
            </div>

            <div class="card-body px-4 pb-4">
                <table class="table table-hover align-middle mb-0">
                    <thead class="table-light">
                    <tr>
                        <th class="text-secondary py-3">Имя файла</th>
                        <th class="text-secondary py-3">Категория</th>
                        <th class="text-secondary py-3">Автор</th>
                        <th class="text-center text-secondary py-3">Скачиваний</th>
                        <th class="text-center text-secondary py-3">Действие</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list files as file>
                        <tr>
                            <td class="fw-semibold text-dark">
                                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="#6c757d" class="bi bi-file-earmark-text me-2" viewBox="0 0 16 16">
                                    <path d="M5.5 7a.5.5 0 0 0 0 1h5a.5.5 0 0 0 0-1h-5zM5 9.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h2a.5.5 0 0 1 0 1h-2a.5.5 0 0 1-.5-.5z"/>
                                    <path d="M9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V4.5L9.5 0zm0 1v2.5a1 1 0 0 0 1 1H13v9a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5z"/>
                                </svg>
                                ${file.name}
                            </td>
                            <td>
                                    <span class="badge bg-info text-dark bg-opacity-25 border border-info rounded-pill px-3 py-2">
                                        ${file.category!}
                                    </span>
                            </td>
                            <td class="text-muted">${file.uploaderEmail}</td>
                            <td class="text-center fw-bold text-primary">${file.downloadCount}</td>
                            <td class="text-center">
                                <a href="/download/public/${file.id}" class="btn btn-sm btn-success rounded-3 px-3 shadow-sm">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cloud-arrow-down-fill me-1" viewBox="0 0 16 16">
                                        <path d="M8 2a5.53 5.53 0 0 0-3.594 1.342c-.766.66-1.321 1.52-1.464 2.383C1.266 6.095 0 7.555 0 9.318 0 11.366 1.708 13 3.781 13h8.906C14.502 13 16 11.57 16 9.773c0-1.636-1.242-2.969-2.834-3.194C12.923 3.999 10.69 2 8 2zm2.354 6.854-2 2a.5.5 0 0 1-.708 0l-2-2a.5.5 0 1 1 .708-.708L7.5 9.293V5.5a.5.5 0 0 1 1 0v3.793l1.146-1.147a.5.5 0 0 1 .708.708z"/>
                                    </svg>
                                    Скачать
                                </a>
                            </td>
                        </tr>
                    <#else>
                        <tr>
                            <td colspan="5" class="text-center text-muted py-5">
                                <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="currentColor" class="bi bi-folder-x mb-3 text-secondary opacity-50" viewBox="0 0 16 16">
                                    <path d="M.54 3.87.5 3a2 2 0 0 1 2-2h3.672a2 2 0 0 1 1.414.586l.828.828A2 2 0 0 0 9.828 3h3.982a2 2 0 0 1 1.992 2.181L15.546 8H14.54l.265-2.91A1 1 0 0 0 13.81 4H2.19a1 1 0 0 0-.996 1.09l.637 7a1 1 0 0 0 .995.91H9v1H2.826a2 2 0 0 1-1.991-1.819l-.637-7a1.99 1.99 0 0 1 .342-1.31zm6.339-1.576A1 1 0 0 0 6.172 2H2.5a1 1 0 0 0-1 .981l.006.139C1.72 3.042 1.95 3 2.19 3h11.62l.148-.008a1 1 0 0 0-.986-.832H9.828a2 2 0 0 1-1.414-.586l-.828-.828z"/>
                                    <path d="M11.854 10.146a.5.5 0 0 0-.707.708L12.293 12l-1.146 1.146a.5.5 0 0 0 .707.708L13 12.707l1.146 1.146a.5.5 0 0 0 .708-.708L13.707 12l1.146-1.146a.5.5 0 0 0-.708-.708L13 11.293l-1.146-1.146z"/>
                                </svg>
                                <h5>Файлов пока нет</h5>
                                <p>Они появятся здесь, как только кто-то загрузит публичный файл.</p>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>

        <#if totalPages &gt; 1>
            <div class="d-flex justify-content-center mt-4 mb-5">
                <#if currentPage &gt; 0>
                    <a href="/?page=${currentPage - 1}<#if category??>&category=${category}</#if>" class="btn btn-outline-primary me-2 fw-bold px-4 rounded-pill">Назад</a>
                </#if>

                <span class="align-self-center fw-semibold text-muted mx-3">Страница ${currentPage + 1} из ${totalPages}</span>

                <#if currentPage &lt; totalPages - 1>
                    <a href="/?page=${currentPage + 1}<#if category??>&category=${category}</#if>" class="btn btn-outline-primary ms-2 fw-bold px-4 rounded-pill">Вперед</a>
                </#if>
            </div>
        </#if>

    </div>

</@main.layout>