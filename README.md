# InstantNotificationCenter
System monitor for jobs executing on apps in backend. The execution will provide data to track the progress and details about.

### Compiling project
First run the command
```
mvn clean package
``` 
This will compile the project.  
This command will generate `src/main/resources/public/` folder where is located the compiled front-end project in Angular 12.


### Running project
On compiled project thats generated `.jar` file, thats can be run with the command
```
java -jar target/InstantNotificationCenter-VERSION.jar
```

For run the source code for test, use this command
```
mvn spring-boot:run
```
