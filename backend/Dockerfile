FROM maven:latest

COPY . .

# Build the image
RUN mvn install

# run during container runtime only
CMD mvn spring-boot:run

EXPOSE 8080
