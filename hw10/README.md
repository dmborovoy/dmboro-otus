### homework9

Реализация паттерна CQRS + EventSourcing. Использовался фреймворк Axon

При создании юзера автоматически создается  аккаунт с нулевой суммой. Баланс можно пополнить через billing API. В случае удачного пополнения/списания/создания аккаунта асинхронно посылается уведомление
в сервис нотификаций
Создание аккаунта происходит с задержкой (eventual consistency):
При создании пользователя отправляется команда на создание аккаунта асинхронно, поэтому ответ сервиса user возвращает accountId=null и status=PENDING_ACCOUNT
Команду исполняет сервис billing и в случае успеха отвправляет событие AccountCreated на который подписан сервис user. Поймав событие сервис user обновляет статус на NORMAL и устанавливает accountId
Это все происходит очень быстро и второй запрос на получение деталей пользоватлея по id уже должен приходить с заполненными данными

TODO
ExceptionHandler при InsufficientFunds обработать ошибку и послать NOK notification
manifest
postman
засунуть аксон в докер компос и хелм
разобраться почему не срабатывает кастомный rabbitlistner

### start

minikube start

minikube tunnel

kubectl apply -f manifest/postgres/postgres.yaml
minikube service postgres --url -n postgres-ns    to get actual PG port in cluster
kubectl apply -f manifest/keycloak/keycloak.yaml
apply import json files
add permissions to management account for otus-client client

http://localhost:8080


start axon server
docker run -d --name axonserver -p 8024:8024 -p 8124:8124 axoniq/axonserver
docker start axonserver
http://localhost:8024

start PG and RMQ
docker-compose up
http://localhost:15672


