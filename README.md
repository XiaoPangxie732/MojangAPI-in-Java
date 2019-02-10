# MojangAPI-in-Java
Mojang Public API Java implementation.  
It is still in development, so please don't download it :).  
    
## Download
Maven:
```xml
<repository>
  <id>xiaopangxie732</id>
  <url>http://yc.aikeshi.top:40002/repository/maven-public/</url>
</repository>
```
```xml
<dependency>
  <groupId>cn.xiaopangxie732</groupId>
  <artifactId>mojang_api</artifactId>
  <version>0.0.1</version>
</dependency>
```
    
## Usage:      
To check API server status:
```java
MojangAPI api = new MojangAPI(); //Create base MojangAPI
Status stat = api.createStatus(); //Create Status class
stat.getStatus(StatusServer.SESSIONSERVER_MOJANG_COM); //To get sessionserver.mojang.com status
```
My English is not very good, maybe some places are wrong:).(from translate.google.com)
