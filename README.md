# Spring boot Starter for swapi MCP Servers With Spring AI

<div style="border: 1px solid #050505; padding: 10px; background-color: #050505;">
  Note: Before You start, change the project name from swapi-mcp-starter to your own project name.
</div>

## Tools

<div style="border: 1px solid #050505; padding: 10px; background-color: #0f1fb5;">

**getStarWarsCharacter**
<ul> 
  <li>Description: Gets Star Wars character information with ID
  <li> Input: id (string): Character ID
  <li> Output: String: Name, gender ve Date of birth
</ul>
</div>

## TECH Stack
- 
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

</div>

## Create Confgicius MCP  container For SSE

<div style="border: 1px solid #050505; padding: 10px; background-color: #050505;">
   <h3>Warnings:</h3>
  <ul> 
    <li> to run container in sse mode, you need to set the environment variable SPRING_AI_MCP_SERVER_STDIO to false. 
    <li> SSE works only in async mode. So change SPRING_AI_MCP_SERVER_TYPE to ASYNC.
    <li> To verify that SSE is working, run this command:  curl -v -H "Accept: text/event-stream" http://localhost:8080/sse
</ul>
</div>
<br/>

```sh
podman run -d --name swapi-mcp-starter -p 8080:8080 swapi-mcp-starter
```

# podman Run in debug mode for podman

```sh
podman run -d --name swapi-mcp-starter -p 8080:8080 -p 5005:5005 -e JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005" swapi-mcp-starter
```

## Intellij settings for debugging

* Go to Run > Edit Configurations
* Click on the "+" icon and select "Remote"
* Set the following parameters:
    * Name: Remote Debug
    * Host: localhost
    * Port: 5005
* Click "Apply" and  "OK" to save the configuration

[Help Page For Debug](https://swapiwiki.atlassian.net/wiki/spaces/CNFGCS/pages/7705232202/Plugin+Y+kleme+ve+Debug+lemleri#Debug-i%C5%9Flemleri)

## Host / Client settings

<div style="border: 1px solid #050505; padding: 10px; background-color: #050505;">
  **Note:** To use podman, the podman command should be replaced by podman in the command property.
</div>

### Claude Desktop

this configuration should be added to claude_desktop_config.json

```json
{
  "mcpServers": {
    "swapi-mcp-starter": {
      "command": "podman",
      "args": [
        "run",
        "-i",
        "--rm",
        "-e",
        "SPRING_MAIN_BANNER_MODE",
        "off",
        "-e",
        "LOGGING_LEVEL_ROOT",
        "ERROR",
        "-e",
        "SPRING_AI_MCP_SERVER_STDIO",
        "true",
        "swapi-mcp-starter"
      ]
    }
  }
}
```

<details>
  <summary>Version V2 For Claude Desktop (Click Here)</summary>
This version uses env parameters in args property

```json
{
  "mcpServers": {
    "swapi-mcp-starter": {
      "command": "podman",
      "args": [
        "run",
        "-i",
        "--rm",
        "-e",
        "SPRING_MAIN_BANNER_MODE",
        "-e",
        "LOGGING_LEVEL_ROOT",
        "-e",
        "SPRING_AI_MCP_SERVER_STDIO",
        "swapi-mcp-starter"
      ],
      "env": {
        "SPRING_MAIN_BANNER_MODE": "off",
        "LOGGING_LEVEL_ROOT": "ERROR",
        "SPRING_AI_MCP_SERVER_STDIO": "true"
      }
    }
  }
}
```

</details>

### VS CODE: CODING ASSISTANTS Settings

For manual installation, add the following JSON block to your Preferences (JSON) file in VS Code. You can do this by
pressing Ctrl + Shift + P and typing Preferences: Open User Settings (JSON).

Optionally, you can add it to a file called .vscode/mcp.json in your workspace. This allows you to share the
configuration with others.

Note that the mcp key is not required in the .vscode/mcp.json file.

```json
{
  "mcp": {
    "inputs": [
      {
        "type": "promptString",
        "id": "spring_main_banner_mode",
        "description": "Spring Main Banner Mode",
        "default": "off"
      },
      {
        "type": "promptString",
        "id": "logging_level_root",
        "description": "Logging Level Root",
        "default": "ERROR"
      },
      {
        "type": "promptString",
        "id": "spring_ai_mcp_server_stdio",
        "description": "Spring AI MCP Server Stdio",
        "default": "true"
      }
    ],
    "servers": {
      "swapi-mcp-starter": {
        "command": "podman",
        "args": [
          "run",
          "--rm",
          "-i",
          "swapi-mcp-starter"
        ],
        "env": {
          "SPRING_MAIN_BANNER_MODE": "${input:spring_main_banner_mode}",
          "LOGGING_LEVEL_ROOT": "${input:logging_level_root}",
          "SPRING_AI_MCP_SERVER_STDIO": "${input:spring_ai_mcp_server_stdio}"
        }
      }
    }
  }
}
```

### Coding: Custom implementation

Demo project with [Langchain4j](https://bitbucket.swapi.com/projects/EMS/repos/02-mcp-server-implementation/browse)