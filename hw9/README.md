### homework9

Реализация паттерна CQRS + EventSourcing. Использовался фреймворк Axon

При создании юзера автоматически создается  аккаунт с нулевой суммой. Баланс можно пополнить через billing API. В случае удачного пополнения/списания/создания аккаунта асинхронно посылается уведомление
в сервис нотификаций
Создание аккаунта происходит с задержкой (eventual consistency):
При создании пользователя отправляется команда на создание аккаунта асинхронно, поэтому ответ сервиса user возвращает accountId=null и status=PENDING_ACCOUNT
Команду исполняет сервис billing и в случае успеха отвправляет событие AccountCreated на который подписан сервис user. Поймав событие сервис user обновляет статус на NORMAL и устанавливает accountId
Это все происходит очень быстро и второй запрос на получение деталей пользоватлея по id уже должен приходить с заполненными данными

https://github.com/dmborovoy/dmboro-otus/tree/main/hw9

предварительно необходимо утсановить axon framework, не нашел под него хелмы поэтому только вручную:

start axon server
docker run -d --name axonserver -p 8024:8024 -p 8124:8124 axoniq/axonserver
docker start axonserver
http://localhost:8024

cd manifest
helm -n postgres-ns install postgres-release ./postgres

cd billing/manifest
helm -n otus install otus-hw9-billing ./app

cd notification/manifest
helm -n otus install otus-hw9-notification ./app

cd user/manifest
helm -n otus install otus-hw9-user ./app


Сценарий:
создать пользователь. Автоматически создастся аккаунт в сервисе биллинг
Положить денег
Снять денег чтобы хватило
Снять денег чтобы не хватило
Посмотреть список транзакций в сервисе нотификаций


TODO
ExceptionHandler при InsufficientFunds обработать ошибку и послать NOK notification
manifest
postman
засунуть аксон в докер компос и хелм
разобраться почему не срабатывает кастомный rabbitlistner