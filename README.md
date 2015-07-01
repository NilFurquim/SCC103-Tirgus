# SSC0103 - Tirgus
Assignment for a class of [Object Oriented Programming][ssc]

#Compilation
The project was made entirely inside **Jetbrains IntelliJ IDEA 14**, compiling in there should go without problems, anyway we include ant build files so you can also compile 
```sh
$ ant -f scc103-tirgus.xml
``` 
##Dependencies
The project has maven dependencies, namely:
* JUnit 4
* Apache PDFBox 1.8.9

#Running
##Server
```sh
$ java -jar Server.jar [port]
```
`port` (optional): the port where the server will listen for clients to connect (default 4537)
##Client
```sh
$ java -jar Client.jar [host] [port]
```
`host` (optional): address of the server (default localhost)
`port` (optional): port where the server is listening (default 4537)

#How to use
All functionality on both server and client present on the application's toolbar, just don't forget you **must be logged in and select a product in order to buy it** 

#Extra points
1. **JavaFX**: Both applications run entirely on JavaFX
2. **JUnit**: We used unit tests in our models, collections and serialization system
3. **PDF**: We used *Apache PDFBox* to make reports on the history of sales
4. **Design Pattern**:```tirgus.net.message.TirgusMessageMapper``` is **singleton**