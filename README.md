# backend
## Name
SmartMat API

## Description
The API backend of [smartmat.app](https://smartmat.app). Smartmat helps you avoid waste and gives full control of you recipes, and items in your fridge.

"SmartMat, your smart food companion"

## Links
- **Frontend:** https://smartmat.app
- **API subdomain:** https://api.smartmat.app
- **Backend Render URl:** https://smartmat.onrender.com/swagger-ui/index.html
- **Swagger ui:** https://api.smartmat.app/swagger-ui/index.html
- **Jacoco tests:** https://idatt2106-v23-03.pages.stud.idi.ntnu.no/backend/jacoco/index.html
- **Javadoc Documentation:** https://idatt2106-v23-03.pages.stud.idi.ntnu.no/backend/apidocs/index.html



## Getting started

1. Clone SmartMat using: 

#### For SSH:

    `git clone git@gitlab.stud.idi.ntnu.no:idatt2106-v23-03/backend.git`

#### For HTTPS:

    `git clone https://gitlab.stud.idi.ntnu.no/idatt2106-v23-03/backend.git`
    
2. Change directory:

    `cd backend`

3. Install dependencies and create JAR

    `mvn clean install`

4. Or, run the application directly

    `mvn spring-boot:run`

## Dockerfile
This Dockerfile is used to build and run the SmartMat backend in a Docker container. It is based on the Eclipse Temurin JDK and JRE Alpine images to keep the image size small and ensure consistency in the Java environment.

1. **Install Docker** from :

[Docker's official website](https://www.docker.com/products/docker-desktop).

2. **Build the Docker image**: 

`docker build -t your-image-name .`

3. **Check the built image**: 

`docker images`

4. **Run the Docker container**: 

`docker run -d --name your-container-name -p 8080:8080 your-image-name`

5. **Access the application**: 

`http://localhost:8080`

6. **Stop the container**: 

`docker stop your-container-name`

## Tests
1. **Run Unit tests:** 

`mvn test`

2. **Get jacoco coverage reports:**

`mvn jacoco:prepare-agent jacoco:report -Dmaven.javadoc.failOnError=false`

The reports are located in `target -> site -> jacoco -> index.html` 

## Authors and acknowledgment
Show your appreciation to those who have contributed to the project.
- Magnus Lutro Allison
- Anders Montsko Austlid
- John Fredrik Bendvold
- Pedro Pablo Cardona Arroyave
- Vebjørn Andreas Lind‐Solstad Myklebust
- Birk Øvstetun Narvhus
- Stian Lyng Stræte

## Thanks

- The developers of [kassal.app](https://kassal.app) for use of their products API.
- The faculty of NTNU for providing helpful advice and guidance trough the process. Especially:
    - Surya Kathayat
    - Grethe Sandstrak
    - Muhammad Ali Norozi