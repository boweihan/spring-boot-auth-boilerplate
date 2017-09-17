Wrapper: gradle wrapper --gradle-version 2.13
Build: ./gradlew build && java -jar build/libs/gs-spring-boot-0.1.0.jar

Implementation Notes:
- Jetty (web server)
- Actuator (production stats and endpoints)
- JUnit (TDD)
- Security (Basic authorization/Authentication)
- Data-JPA/PostgreSQL (Relational DB)

TODO:
- Snowflake IDS
- Hashed passwords
- Authentication tokens with endpoints to get TOKEN
- Users hooked up with webSecurityConfig