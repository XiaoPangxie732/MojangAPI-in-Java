![Java CI](https://github.com/XiaoPangxie732/MojangAPI-in-Java/workflows/Java%20CI/badge.svg)
# MojangAPI-in-Java
Mojang Public API Java implementation.  

## Usage
Usage is in [Wiki](https://github.com/XiaoPangxie732/MojangAPI-in-Java/wiki)    

## Download
Maven:  
Follow [these steps](https://help.github.com/en/packages/using-github-packages-with-your-projects-ecosystem/configuring-apache-maven-for-use-with-github-packages#authenticating-to-github-packages) to authenticate to GitHub Packages first.
```xml
<repositories>
  <repository>
    <id>github</id>
    <name>GitHub XiaoPangxie732 Apache Maven Packages</name>
    <url>https://maven.pkg.github.com/XiaoPangxie732/MojangAPI-in-Java</url>
  </repository>
</repositories>
<dependencies>
  <dependency>
    <groupId>cn.xiaopangxie732</groupId>
    <artifactId>mojang-api</artifactId>
    <version>0.1.1.Final</version>
  </dependency>
</dependencies>
```
  
Jar file download: Please download file at [GitHub releases](https://github.com/XiaoPangxie732/MojangAPI-in-Java/releases/latest)  
Javadoc: [Click here](https://xiaopangxie732.github.io/MojangAPI-in-Java/apidocs/index.html)
## How to build(from source):      
1.Install [Maven](https://maven.apache.org/).  
2.`cd` to source directory.  
3.Run <code>mvn clean install</code>  
4.When build complete, files are all in target folder. 
