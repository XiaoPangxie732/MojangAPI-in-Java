# MojangAPI-in-Java
Mojang Public API Java implementation.  
It is still in development, so please don't download it :).  
<br>
<br>
## Usage:      
To check API server status:
```java
MojangAPI api = new MojangAPI(); //Create base MojangAPI
Status stat = api.createStatus(); //Create Status class
stat.getStatus(StatusServer.SESSIONSERVER_MOJANG_COM); //To get sessionserver.mojang.com status
```
My English is not very good, maybe some places are wrong:).(from translate.google.com)
