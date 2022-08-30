### howework1

kubectl create namespace otus

kubectl apply -f deployment.yaml,service.yaml,ingress.yaml -n otus

not working without it:
minikube service otus-app --url -n otus

GET http://127.0.0.1:59239/health
###


docker build -t otus-app:latest  .

docker run -m 1G -p 5000:5000 otus-app:latest -d

docker tag otus-app:latest recvezitor/otus-app:latest

docker push recvezitor/otus-app:latest

docker push docker.io/recvezitor/otus-app:latest

docker pull docker.io/recvezitor/otus-app:latest


### kube

kubectl create namespace otus
kubectl config set-context --current --namespace=otus

minikube ssh

kubectl apply -f pod.yaml

curl -X GET --location "http://172.17.0.6:5000/api/v1/container"

minikube service otus-app --url -n otus

kubectl port-forward svc/otus-app 8001:8000

minikube addons enable ingress

minikube addons enable ingress
🌟 The
kubectl apply -