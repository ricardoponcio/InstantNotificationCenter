# InstantNotificationCenter

InstantNotificationCenter is a real-time monitoring tool for tracking the execution of jobs running on backend applications. It provides a web interface to visualize job progress, status, and execution details, making it easier to monitor distributed or scheduled tasks.

## Features

- **Live Job Monitoring:** View the status and progress of jobs as they execute.
- **Detailed Execution Data:** Access logs, progress percentages, and job metadata.
- **Modern Web Frontend:** Built with Angular 12 for a responsive user experience.
- **Easy Integration:** Backend can receive job updates from your applications.
- **Extensible SDK:** Use the [InstantNotificationSdk](https://github.com/ricardoponcio/InstantNotificationSdk) to send job updates from Java apps.

## Project Structure

- `src/main/` - Java Spring Boot backend source code.
- `src/main/frontend/` - Angular 12 frontend application.
- `src/main/resources/public/` - Compiled frontend assets (generated after build).
- `target/` - Build output, including the executable JAR.

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- Node.js & npm (for frontend development)

## Building the Project

To build both backend and frontend, run:
```
mvn clean package
```

This command compiles the project and generates the `src/main/resources/public/` folder, which contains the compiled front-end project in Angular 12.

## Running the Project

To run the compiled project, use the following command, replacing `VERSION` with the actual version number:
```
java -jar target/InstantNotificationCenter-VERSION.jar
```

For testing the source code, you can run the project in development mode using:
```
mvn spring-boot:run
```

The web interface will be available at [http://localhost:8080](http://localhost:8080).

## Frontend Development

To work on the Angular frontend separately:

1. Navigate to the frontend directory:
    ```
    cd src/main/frontend
    ```
2. Install dependencies:
    ```
    npm install
    ```
3. Start the development server:
    ```
    ng serve
    ```
4. Open [http://localhost:4200](http://localhost:4200) in your browser.

## Testing

- **Backend:** Run `mvn test` for backend unit tests.
- **Frontend:** In `src/main/frontend`, run `ng test` for frontend unit tests.

## Related Projects

- [InstantNotificationSdk](../InstantNotificationSdk/README.md): Java SDK for sending job updates to InstantNotificationCenter.

## License

This project is licensed under the [Apache License 2.0](LICENSE)