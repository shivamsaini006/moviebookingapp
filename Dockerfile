FROM openjdk:8
EXPOSE 8080
ADD target/moviebooking-automation.jar moviebooking-automation.jar
ENTRYPOINT ["java","-jar","/moviebooking-automation.jar"]
