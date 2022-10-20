### homework9

Реализация паттерна CQRS + EventSourcing. Использовался фреймворк Axon

При создании юзера автоматически создается  аккаунт с нулевой суммой. Баланс можно пополнить через billing API. В случае удачного пополнения/списания/создания аккаунта асинхронно посылается уведомление
в сервис нотификаций
Создание аккаунта происходит с задержкой (eventual consistency):
При создании пользователя отправляется команда на создание аккаунта асинхронно, поэтому ответ сервиса user возвращает accountId=null и status=PENDING_ACCOUNT
Команду исполняет сервис billing и в случае успеха отвправляет событие AccountCreated на который подписан сервис user. Поймав событие сервис user обновляет статус на NORMAL и устанавливает accountId
Это все происходит очень быстро и второй запросна получение деталей пользоватлея по id уже должен приходить с заполненными данными


### start
start axon server
docker run -d --name axonserver -p 8024:8024 -p 8124:8124 axoniq/axonserver
http://localhost:8024

start PG and RMQ
docker-compose up
http://localhost:15672

