# Dart Monitoring

Console application to track dart games, built with Spring Boot, Spring Data JPA,
Hibernate, and H2.

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
`ApplicationConfiguration` registers the scanner as a bean. Spring Boot configures
the database connection and JPA infrastructure. `ConsoleRunner` starts the console
game after Spring has initialized the components. When the game loop ends, the
application context closes the scanner and database resources.

Database settings are in `src/main/resources/application.properties`. The H2
database is stored under `data/` relative to the working directory.

The three repository interfaces extend `JpaRepository`, which provides standard
operations such as `save()` and `findById()`. Player selection uses
`findAll()` with an explicit query to sort players by ascending ID.
Games are saved through `saveWithUpdatedAt()`, which sets the timestamp before
delegating to Spring Data's `save()`, including when no other game fields changed.
The standard `save()` method does not set that timestamp itself.

Run the repository integration tests against an isolated in-memory H2 database:

```sh
mvn test
```
