# Spring boot Starter for swapi MCP Servers With Spring AI

## TECH Stack

- **Spring Boot**: 3.4.4
- **Spring AI**: 1.0.0-M7
- **Spring MCP Server (Stdio/Webflux)**
- **Lombok**: 1.18.38
- **SpotBugs**: 4.8.6
- **Jackson Databind**
- **CheckStyle**

## To create jar file

```shell
mvn clean package
```

<div style="border: 1px solid #050505; padding: 10px; background-color: #050505;">
  Note: To use docker, the podman command should be replaced by podman,
</div>

## Podman Image

There are tow type Docker image for Spring MCP server for this projecy

### SSE Docker Image

- To crate SSE docker image use  [Dockerfile](dockers/sse/Dockerfile)

```shell
 podman build   -f dockers/sse/Dockerfile   -t swapi-mcp-starter:latest  .
```

### STDIO Docker Image

- To create STDIO docker image use [Dockerfile](dockers/stdio/Dockerfile)

```shell
  podman build   -f dockers/stdio/Dockerfile   -t swapi-mcp-starter:latest  .
```

<div style="border: 1px solid #050505; padding: 10px; background-color: #132285;">
  Note-1 : In some cases, below command may not work. In that case, you can use following command to build the image. 
  Note-2:  Dockerfile and jar file should be in the same directory.
  Note-3: change this  "COPY target/swapi-mcp-starter.jar app.jar" to "COPY swapi-mcp-starter.jar app.jar" in the Dockerfile

```sh
 podman build -t swapi-mcp-starter  .
```
