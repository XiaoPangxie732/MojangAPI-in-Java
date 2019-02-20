# MojangAPI-in-Java
Mojang Public API Java implementation.  
<h2>It is still in development. The download link may not be downloaded because the server is debugging.:D</h2>
    
## Download
Maven:
```xml
<repository>
  <id>xiaopangxie732</id>
  <url>http://yc.aikeshi.top:40002/repository/maven-public</url>
</repository>
```
```xml
<dependency>
  <groupId>cn.xiaopangxie732</groupId>
  <artifactId>mojang-api</artifactId>
  <version>0.0.2</version>
</dependency>
```
  
Jar file download: [Click here](http://yc.aikeshi.top:30003/xiaopangxie732/downloads/java-jar/mojang-api-java.jar)  
Javadoc: [Click here](http://yc.aikeshi.top:30003/xiaopangxie732/javadoc/mojang-api)
## Usage:      
To check API server status:
```java
MojangAPI api = new MojangAPI(); //Create base MojangAPI
Status stat = api.createStatus(); //Create Status class
stat.getStatus(StatusServer.SESSIONSERVER_MOJANG_COM); //To get sessionserver.mojang.com status
```
My English is not very good, maybe some places are wrong:).(from translate.google.com)
