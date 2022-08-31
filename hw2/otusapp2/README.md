### homework2

1. https://github.com/dmborovoy/dmboro-otus/tree/main/hw2/otusapp2/manifest
2. kubectl apply -f postgres.yaml
   kubectl apply -f app-config.yaml,app-config-secret.yaml,deployment-fromconfig.yaml,service.yaml,ingress.yaml
 Миграция схемы не нужна, происходит при запуске приложения с помощью flyway
3. postman collection
don't forget minikube tunnel

### Docker

docker build -t otus-app2:latest  .

docker run -m 1G -p 5000:5000 otus-app2:latest -d

docker tag otus-app2:latest recvezitor/otus-app2:latest

docker push recvezitor/otus-app2:latest

docker push docker.io/recvezitor/otus-app2:latest

docker pull docker.io/recvezitor/otus-app2:latest

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

kubectl logs -n otus otus-app2-74f6df95f9-ftmsm
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

helm -n postgres-ns install otus2-release ./postgres

[//]: # (helm -n postgres-ns upgrade otus2-release -f values.yaml postgresql/ # Посмотреть как появятся секрет и конфиг мап в нейспейсе postgres)