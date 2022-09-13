### homework6
idempotence

1 success - save request with request id and fingerprint, created ok
2 client network lag - send request and save both request and user but reply timeout (emulate that server handled client request but didn't reply back in time)
repeat request validate request id and reply 409

it is harder for update but not in the scope of HW

### homework6


### install

cd otusapp6
docker build -t otus-app6:latest  .
docker tag otus-app6:latest recvezitor/otus-app6:latest
docker push docker.io/recvezitor/otus-app6:latest

cd otus-gateway
docker build -t otus-gateway:latest  .
docker tag otus-gateway:latest recvezitor/otus-gateway:latest
docker push docker.io/recvezitor/otus-gateway:latest

### deploy

cd keycloak
kubectl apply -f ./keycloak/keycloak.yaml -n otus
import configs from file realm-export.json using UI
import public-client from file public-client.json using UI
import otus-client from file otus-client.json using UI
configure mapper for userId

cd manifest
helm -n postgres-ns install postgres-release ./postgres
helm -n otus install gateway-release ./gateway
helm -n otus install otus6-release ./app

Ingress (nginx) используется только как точка входа для внешнего трафика в кластер. Роутинг минимальный: все что касается авторизации - на Киклок, остальное через API Gateway
Api Gateway написан с использованием спрингового стартера. Там находится весь "сложный" бизнес роутинг. Там же находится вся логика безопасности:
/api/v1/register  только для анонимов
/actuator - для аутентицированного но любая роль (permitAll)
/api/v1/users/**  - авторизованные юзеры с ролью USER (ролевая модель не реализована, все аутентицированные пользователи автоматически получают роль USER)
Также внтури гейтвея парсится токен и извлекается информация о пользователе и пробрасывается дальше внутрь кластера в заголовках запроса.
После гейтвея считаем зону безопасной и никакой секьюрити там нет, считаем, что апи гейтвей на входе работает правильно и не пропускает плохих юзеров
В Профайл (otus-app6) сервисе используется информация о текущем пользователе по средством хедеров и разрешается управлять только своим профилем
При создании пользователя (/register) информация о пользователе синхронизируется с Киклоком по внутреннему каналу чтобы при последующей авторизации можно было вытащить userId из токена





helm -n otus upgrade otus6-release ./app
helm -n otus upgrade gateway-release ./gateway

helm delete otus6-release -n otus
helm delete gateway-release -n otus
helm delete postgres-release -n postgres-ns



kubectl get pods -n postgres-ns
kubectl -n postgres-ns exec -it postgres-6fd479647d-jppf7  -- psql --username postgres
sudo -i -u postgres
\dt
DELETE FROM otus.user;
SELECT * FROM postgres.flyway_schema_history;

kubectl logs -n otus otus-gateway-6689cd7698-vh6t8 --follow
### helm


https://www.keycloak.org/getting-started/getting-started-kube


install keyclaok helm repo add bitnami https://charts.bitnami.com/bitnami


helm -n postgres-ns install otus6-release ./postgres

helm -n otus install otus6-release ./app

helm -n otus upgrade otus6-release ./app

helm install stack prometheus-community/kube-prometheus-stack -f stack-values.yaml

kubectl port-forward service/prometheus-operated  9090

kubectl port-forward service/stack-grafana  9000:80

kubectl apply -f service-monitor.yaml

### prometheus

at app side:

http://localhost:6000/actuator/prometheus

ui:
http://localhost:9090

### graphana

http://localhost:9000
admin/prom-operator



1. https://github.com/dmborovoy/dmboro-otus/tree/main/hw2/otusapp2/manifest
2. kubectl apply -f postgres.yaml
   kubectl apply -f app-config.yaml,app-config-secret.yaml,deployment-fromconfig.yaml,service.yaml,ingress.yaml
   Миграция схемы не нужна, происходит при запуске приложения с помощью flyway
3. postman collection
   don't forget minikube tunnel

### Docker

docker build -t otus-app6:latest  .

docker run -m 1G -p 6000:6000 otus-app6:latest -d

docker tag otus-app6:latest recvezitor/otus-app6:latest

docker push recvezitor/otus-app6:latest

docker push docker.io/recvezitor/otus-app6:latest

docker pull docker.io/recvezitor/otus-app6:latest

### statefullset

kubectl apply -f postgres.yaml

kubectl apply -f app-config.yaml,app-config-secret.yaml,deployment-fromconfig.yaml,service.yaml,ingress.yaml

kubectl delete statefulset postgres-statefulset
kubectl delete service postgres
kubectl delete pvc postgredb-postgres-statefulset-0

postgredb-postgres-statefulset-0

minikube service postgres --url


kubectl delete all --all -n otus
kubectl delete all --all -n postgres-ns

kubectl logs -n otus otus-app6-84ddcdd6c6-j2cn4 --follow
kubectl logs  pvc-3c1ee7fb-4636-4d7c-a676-ffe1a8660489
helm delete $(helm ls --short)

-- http://127.0.0.1:61648

psql -h 127.0.0.1 -p 61648 -U postgres -W postgres

\dt

### kube

kubectl create namespace otus
kubectl config set-context --current --namespace=otus

minikube ssh

kubectl apply -f pod.yaml

curl -X GET --location "http://172.17.0.6:6000/api/v1/container"

minikube service otus-app --url -n otus

kubectl port-forward svc/otus-app 8001:8000

minikube addons enable ingress

### helm

helm repo add bitnami https://charts.bitnami.com/bitnami # Добавить репозиторий себе

helm repo update # Обновить локально информацию про удалённые репозитории

helm pull bitnami/postgresql # Скачать репозиторий как архив
tar -xvf postgresql-11.6.18.tgz # ;)

kubectl create namespace postgres-ns #Создадим отдельный namespace

helm -n postgres-ns install otus6-release ./postgres
helm -n otus install otus6-release ./app

helm -n otus upgrade otus6-release ./app


[//]: # (helm -n postgres-ns upgrade otus2-release -f values.yaml postgresql/ # Посмотреть как появятся секрет и конфиг мап в нейспейсе postgres)