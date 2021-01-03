# notification-service

### Library
```xml
<dependency>
    <groupId>codes.mydna</groupId>
    <artifactId>notification-service-lib</artifactId>
    <version>${notification-service.version}</version>
</dependency>
```

### Docker

*Note: This service requires DB.*

Pull docker image:
```bash
docker pull mydnacodes/notification-service
```

Run docker image:
```bash
docker run -d -p <PORT>:8080 
    -e KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://<DB_HOST>:<DB_PORT>/notification-service
    -e KUMULUZEE_DATASOURCES0_USERNAME=<DB_USERNAME> 
    -e KUMULUZEE_DATASOURCES0_PASSWORD=<DB_PASSWORD> 
    --name notification-service-service
    mydnacodes/notification-service
```