# Restaurant-order-management-system
Система Управления Заказами в Ресторане

Система управления заказами предназначена для использования в ресторанах, позволяя клиентам (посетителям) и администраторам управлять заказами и меню в реальном времени. Разработана с соблюдением принципов ООП и SOLID, а также использованием соответствующих шаблонов проектирования. Система обрабатывает заказы в многопоточном режиме, позволяя клиентам добавлять блюда в заказ в реальном времени, а также отображает статусы заказов. 
Это REST-API приложение  на Java с использованием фреймворка SpringBoot и хранением данных в БД (использовались  pgAdmin и postgres)

## Функции
- Реализована система аутентификации для двух типов пользователей: посетителей и администраторов.
- Управление блюдами: Администраторы могут добавлять, удалять и обновлять блюда в меню, включая детали, такие как количество, цена и время приготовления.
- Управление заказами: Посетители могут создавать заказы, добавляя блюда из текущего меню, и просматривать статус своих заказов в реальном времени.
- Многопоточная обработка заказов: Заказы обрабатываются в отдельных потоках, симулируя процесс приготовления и позволяя обновлять статус заказов в реальном времени.
- Динамическое изменение заказа: Посетители могут изменять свои заказы, добавляя блюда, пока заказ находится в обработке.
- Отмена заказа: Предоставляется возможность посетителям отменять свои заказы до их завершения.
- Оплата и отслеживание выручки: По завершении заказы могут быть оплачены, и система отслеживает общую выручку.

## Паттерны проектирования
- Singleton: Ваши сервисы (@Service) и репозитории (@Repository) по умолчанию являются синглтонами в Spring контексте.
- Фабрика (Factory): Spring Boot использует этот паттерн для создания и управления бинами приложения.
- Builder для созданния DTO и сущностей.
## Архитектура
Приложение следует принципам ООП и SOLID, используя шаблоны проектирования, где это уместно, для обеспечения надежной, легко поддерживаемой и масштабируемой системы. Архитектура разделена на несколько ключевых компонентов:

## Структура проекта
- Модели (model): Определяют структуру данных (Dish, Order, User, Role, Status) с аннотациями JPA для маппинга объектов в базе данных.
- Репозитории (repository): Интерфейсы для взаимодействия с базой данных, наследующиеся от JpaRepository.
- Сервисы (services): Классы, содержащие бизнес-логику приложения, включая обработку заказов, управление пользователями и блюдами.
- Контроллеры (controllers): Обрабатывают HTTP-запросы и взаимодействуют с сервисным слоем для выполнения операций CRUD и другой логики приложения.

## Объяснение многопоточности
ExecutorService и LinkedBlockingQueue: Используются в сервисе CookingService для асинхронной обработки заказов и приготовления блюд. Заказы помещаются в очередь, а затем обрабатываются в пуле потоков, что позволяет эффективно распределять задачи по приготовлению блюд между поварами (потоками).

## Использование Swagger
Swagger UI доступен по адресу [http://localhost:8080/swagger](http://localhost:8080/swagger-ui/index.html#) после запуска приложения. Это интерактивный интерфейс для тестирования и документирования REST API приложения. 

Для работы с приложением была создана БД "Restaurantkpo"  в pgAdmin
Для удобства тестирования в папке с проектом предоставлен бекап БД

