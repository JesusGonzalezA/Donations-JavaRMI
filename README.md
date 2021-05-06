# Donations-JavaRMI
UGR project. Client-Server using Java RMI

## Description 
This is a project using Java RMI. A server is compound by replicas. They can interact with each other in order to maintein the data updated.
The client can ask a replica to register, donate an amount of money, see the information of every replica, see the total amount donated by the client, the number of donations made by the client
and the total amount donated in the server. 
A client can only donate in the replica where they had been registered.
I used Java RMI to achieve the communication between the replicas and the client-server.

## Documentation
[See here](https://github.com/JesusGonzalezA/Donations-JavaRMI/tree/master/doc)

## Compilation
```shell
javac *.java
```

## Execution

### Client
```shell
 java -cp . -Djava.rmi.server.hostname=localhost -Djava.security.policy=server.policy -Djava.rmi.server.codebase=file:/. AppClient
```
### Server
```shell
 java -cp . -Djava.rmi.server.codebase=file:/. -Djava.rmi.server.hostname=localhost -Djava.security.policy=server.policy AppServer
```
