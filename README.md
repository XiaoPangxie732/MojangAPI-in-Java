# MojangAPI-in-Java
Mojang Public API Java implementation.  
<h2>It is still in development. The download link may not be downloaded because the server is debugging.</h2>
    
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
  
Jar file download: Please download file at [GitHub releases](https://github.com/XiaoPangxie732/MojangAPI-in-Java/releases/tag/0.0.2)  
Javadoc: [Click here](http://yc.aikeshi.top:30003/xiaopangxie732/javadoc/mojang-api/0.0.1)
## Usage:      
To check API server status:
```java
Status stat = new Status(); //Create Status class
stat.getStatus(StatusServer.AUTHSERVER_MOJANG_COM); //To get sessionserver.mojang.com status
```
<hr>
My English is not very good, maybe some places are wrong:).(from translate.google.com)
