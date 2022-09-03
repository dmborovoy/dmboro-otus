### homework3

### helm

helm -n postgres-ns install otus3-release ./postgres

helm -n otus install otus3-release ./app

helm -n otus upgrade otus3-release ./app

helm install stack prometheus-community/kube-prometheus-stack -f stack-values.yaml

kubectl port-forward service/prometheus-operated  9090

kubectl port-forward service/stack-grafana  9000:80

kubectl apply -f service-monitor.yaml

### prometheus

at app side:

http://localhost:5000/actuator/prometheus

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

docker build -t otus-app3:latest  .

docker run -m 1G -p 5000:5000 otus-app3:latest -d

docker tag otus-app3:latest recvezitor/otus-app3:latest

docker push recvezitor/otus-app3:latest

docker push docker.io/recvezitor/otus-app3:latest

docker pull docker.io/recvezitor/otus-app3:latest

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

kubectl logs -n otus otus-app3-6c79645c6c-j7vjk
kubectl logs  pvc-3c1ee7fb-4535-4d7c-a575-ffe1a8550489


 -- http://127.0.0.1:61648
 
psql -h 127.0.0.1 -p 61648 -U postgres -W postgres

\dt

### kube

kubectl create namespace otus
kubectl config set-context --current --namespace=otus

minikube ssh

kubectl apply -f pod.yaml

curl -X GET --location "http://172.17.0.6:5000/api/v1/container"

minikube service otus-app --url -n otus

kubectl port-forward svc/otus-app 8001:8000

minikube addons enable ingress

### helm

helm repo add bitnami https://charts.bitnami.com/bitnami # Добавить репозиторий себе

helm repo update # Обновить локально информацию про удалённые репозитории

helm pull bitnami/postgresql # Скачать репозиторий как архив
tar -xvf postgresql-11.6.18.tgz # ;)

kubectl create namespace postgres-ns #Создадим отдельный namespace

helm -n postgres-ns install otus3-release ./postgres
helm -n otus install otus3-release ./app

helm -n otus upgrade otus3-release ./app


[//]: # (helm -n postgres-ns upgrade otus2-release -f values.yaml postgresql/ # Посмотреть как появятся секрет и конфиг мап в нейспейсе postgres)