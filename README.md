# Dart Monitoring

Console application to track dart games, built with Spring Boot, Hibernate, and H2.

Requires Java 25 and Maven 3.6.3 or later.

Run the application:

```sh
mvn spring-boot:run
```

Build and run an executable JAR:

```sh
mvn clean package
java -jar target/dart_monitoring-1.0-SNAPSHOT.jar
```

The entry point is `dart_monitoring.Main`. Spring discovers components in that
package and its subpackages and supplies their constructor dependencies.
`ApplicationConfiguration` registers the scanner and Hibernate session factory as
beans. `ConsoleRunner` starts the console game after Spring has initialized the
components. When the game loop ends, the application context closes both resources.

Hibernate settings remain in `src/main/resources/hibernate.properties`. The H2
database is stored under `data/` relative to the working directory.
