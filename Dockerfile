FROM eclipse-temurin:17
COPY target/*.jar app.jar
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5433/ai-drink-db
ENV SPRING_DATASOURCE_USERNAME=admin
ENV SPRING_DATASOURCE_PASSWORD=admin

EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]