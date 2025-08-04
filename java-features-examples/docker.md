###############################################################################
## 1. Docker Images
###############################################################################

- `docker images`  
  *List all Docker images on your system.*
  ```sh
  docker images
  ```

- `docker build -t <image_name> .`  
  *Build an image from a Dockerfile in the current directory.*
  ```sh
  docker build -t myapp:latest .
  ```

- `docker pull <image_name>`  
  *Download an image from Docker Hub or another registry.*
  ```sh
  docker pull nginx:latest
  ```

- `docker rmi <image_name>`  
  *Remove a Docker image.*
  ```sh
  docker rmi myapp:latest
  ```

---

###############################################################################
## 2. Docker Containers
###############################################################################

- `docker ps`  
  *List running containers.*
  ```sh
  docker ps
  ```

- `docker ps -a`  
  *List all containers (running and stopped).*
  ```sh
  docker ps -a
  ```

- `docker run -it <image_name>`  
  *Run a container interactively.*
  ```sh
  docker run -it ubuntu /bin/bash
  ```

- `docker run -d --name <container_name> <image_name>`  
  *Run a container in detached mode with a custom name.*
  ```sh
  docker run -d --name web nginx
  ```

- `docker start <container_id>`  
  *Start a stopped container.*
  ```sh
  docker start 123abc
  ```

- `docker stop <container_id>`  
  *Stop a running container.*
  ```sh
  docker stop 123abc
  ```

- `docker restart <container_id>`  
  *Restart a container.*
  ```sh
  docker restart 123abc
  ```

- `docker rm <container_id>`  
  *Remove a container.*
  ```sh
  docker rm 123abc
  ```

---

###############################################################################
## 3. Docker Volumes
###############################################################################

- `docker volume create <volume_name>`  
  *Create a Docker volume.*
  ```sh
  docker volume create mydata
  ```

- `docker volume ls`  
  *List all Docker volumes.*
  ```sh
  docker volume ls
  ```

- `docker volume rm <volume_name>`  
  *Remove a Docker volume.*
  ```sh
  docker volume rm mydata
  ```

---

###############################################################################
## 4. Docker Networking
###############################################################################

- `docker network create <network_name>`  
  *Create a Docker network.*
  ```sh
  docker network create mynet
  ```

- `docker network ls`  
  *List all Docker networks.*
  ```sh
  docker network ls
  ```

- `docker network rm <network_name>`  
  *Remove a Docker network.*
  ```sh
  docker network rm mynet
  ```

---

###############################################################################
## 5. Logs, Stats, and Inspection
###############################################################################

- `docker logs <container_id>`  
  *View logs of a container.*
  ```sh
  docker logs web
  ```

- `docker stats`  
  *Display a live stream of container(s) resource usage statistics.*
  ```sh
  docker stats
  ```

- `docker inspect <container_id>`  
  *Return low-level information on Docker objects.*
  ```sh
  docker inspect web
  ```

---

###############################################################################
## 6. Exec and Other Useful Commands
###############################################################################

- `docker exec -it <container_id> /bin/bash`  
  *Run a command in a running container (open a shell).*
  ```sh
  docker exec -it web /bin/bash
  ```

- `docker attach <container_id>`  
  *Attach your terminal to a running container.*
  ```sh
  docker attach web
  ```

- `docker save -o <file.tar> <image_name>`  
  *Save an image to a tar archive.*
  ```sh
  docker save -o myapp.tar myapp:latest
  ```

- `docker load -i <file.tar>`  
  *Load an image from a tar archive.*
  ```sh
  docker load -i myapp.tar
  ```

---

**Tip:**  
Practice these commands and understand the difference between images, containers, volumes, and networks. Be ready to explain how to troubleshoot containers and manage persistent data.

---

###############################################################################
# kubectl Command Guide for Interview Preparation
###############################################################################

This guide covers essential `kubectl` commands for Kubernetes, with explanations and examples. Use it to prepare for technical interviews and strengthen your Kubernetes skills.

---

###############################################################################
## 1. Get and Describe Resources
###############################################################################

- `kubectl get pods`  
  *List all pods in the current namespace.*
  ```sh
  kubectl get pods
  ```

- `kubectl get pods --all-namespaces`  
  *List all pods in all namespaces.*
  ```sh
  kubectl get pods --all-namespaces
  ```

- `kubectl get services`  
  *List all services in the current namespace.*
  ```sh
  kubectl get services
  ```

- `kubectl get nodes`  
  *List all nodes in the cluster.*
  ```sh
  kubectl get nodes
  ```

- `kubectl describe pod <pod_name>`  
  *Describe a specific pod (details, events, status).*
  ```sh
  kubectl describe pod mypod
  ```

- `kubectl describe node <node_name>`  
  *Describe a specific node.*
  ```sh
  kubectl describe node mynode
  ```

---

###############################################################################
## 2. Create, Apply, and Delete Resources
###############################################################################

- `kubectl create -f <file.yaml>`  
  *Create resources defined in a YAML file.*
  ```sh
  kubectl create -f deployment.yaml
  ```

- `kubectl create deployment <name> --image=<image>`  
  *Create a deployment directly from the command line.*
  ```sh
  kubectl create deployment nginx-deploy --image=nginx
  ```

- `kubectl apply -f <file.yaml>`  
  *Apply changes defined in a YAML file (create or update resources).*
  ```sh
  kubectl apply -f deployment.yaml
  ```

- `kubectl delete pod <pod_name>`  
  *Delete a specific pod.*
  ```sh
  kubectl delete pod mypod
  ```

- `kubectl delete -f <file.yaml>`  
  *Delete resources defined in a YAML file.*
  ```sh
  kubectl delete -f deployment.yaml
  ```

---

###############################################################################
## 3. Scaling, Logs, and Exec
###############################################################################

- `kubectl scale deployment <name> --replicas=<number>`  
  *Scale a deployment to the specified number of replicas.*
  ```sh
  kubectl scale deployment nginx-deploy --replicas=5
  ```

- `kubectl logs <pod_name>`  
  *View logs for a specific pod.*
  ```sh
  kubectl logs mypod
  ```

- `kubectl logs <pod_name> -c <container_name>`  
  *View logs for a specific container in a pod.*
  ```sh
  kubectl logs mypod -c mycontainer
  ```

- `kubectl exec -it <pod_name> -- /bin/bash`  
  *Start a bash session in a pod.*
  ```sh
  kubectl exec -it mypod -- /bin/bash
  ```

---

###############################################################################
## 4. Additional Useful kubectl Commands
###############################################################################

- `kubectl get namespaces`  
  *List all namespaces.*
  ```sh
  kubectl get namespaces
  ```

- `kubectl config get-contexts`  
  *List all available contexts.*
  ```sh
  kubectl config get-contexts
  ```

- `kubectl config use-context <context_name>`  
  *Switch to a different context.*
  ```sh
  kubectl config use-context my-context
  ```

- `kubectl port-forward <pod_name> <local_port>:<pod_port>`  
  *Forward one or more local ports to a pod.*
  ```sh
  kubectl port-forward mypod 8080:80
  ```

- `kubectl edit <resource>/<name>`  
  *Edit a resource in place (opens in editor).*
  ```sh
  kubectl edit deployment/nginx-deploy
