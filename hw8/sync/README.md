### homework8

Распределенные транзакции. Использовал схему оркестратора. Чтобы не усложнять, сага начинается в первом сервисе из цепочки (Order).
Шаги саги реализованы через паттерн Command. В случае плохого ответа одного из шагов проигрывается revert каждого сервиса в обратном порядке.
В данной реализации никакой асинхронщины, все происходит в сервисе оркестраторе (Order service) последовательно и синхронно.


Happy Path:
Saga -> Init -> Order -> Account -> Stock -> Done

Failure Path:
Saga -> Init -> Order -> Account -> Stock Failed -> RollbackAccount -> RollbackOrder -> Failed

Чтобы вызвать откат транзакции можно просто указать id продукта (goodId) которого не сущетсвует

Перед запуском сценариев необходимо накатить тестовых данных из скрипта data.sql

### Docker

docker build -t otus-hw8-stock:latest  .

docker run -m 1G -p 5000:5000 otus-hw8-stock:latest -d

docker tag otus-hw8-stock:latest recvezitor/otus-hw8-stock:latest

docker push recvezitor/otus-hw8-stock:latest

docker push docker.io/recvezitor/otus-hw8-stock:latest

docker pull docker.io/recvezitor/otus-hw8-stock:latest