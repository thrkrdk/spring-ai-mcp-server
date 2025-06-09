# Spring boot Starter for  MCP Servers With Spring AI

## TECH Stack

- **Spring Boot**: 3.5.0
- **Spring AI**: 1.0.0
- **Actuator**
- **Spring MCP Server (Stdio/Webflux)**
- **MCP Annotations**
- **Lombok**: 1.18.38
- **SpotBugs**: 4.8.6
- **Jackson Databind**
- **CheckStyle**

## To create jar file

```shell
mvn clean package
```

## Tools

<div style="border: 1px solid #050505; padding: 10px; background-color: #0f1fb5;">
<b>whoAmI in [WhoAmIToolsService.java](src/main/java/com/thr/krdk/mcp/server/WhoAmIToolsService.java)</b>
<ul>
  <li>Description: To get User Information</li>
  <li>Input: Parametre gerektirmez</li>
  <li>Output: JSON: Kullanıcı bilgilerini içeren JSON nesnesi (name, lastName, title)</li>
</ul>

<b>sum in [CalculatorToolsService.java](src/main/java/com/thr/krdk/mcp/server/CalculatorToolsService.java)</b>
<ul>
  <li>Description: Sums two numbers</li>
  <li>Input:
    <ul>
      <li>numberA (long): First number</li>
      <li>numberB (long): Second number</li>
    </ul>
  </li>
  <li>Output: long: Sum of numberA and numberB multiplied by a constant</li>
</ul>

<b>subtract in [CalculatorToolsService.java](src/main/java/com/thr/krdk/mcp/server/CalculatorToolsService.java)</b>
<ul>
  <li>Description: Calculate the difference of two numbers</li>
  <li>Input:
    <ul>
      <li>numberA (long): First number</li>
      <li>numberB (long): Second number</li>
    </ul>
  </li>
  <li>Output: long: Difference of numberA and numberB multiplied by a constant</li>
</ul>

<b>multiply in [CalculatorToolsService.java](src/main/java/com/thr/krdk/mcp/server/CalculatorToolsService.java)</b>
<ul>
  <li>Description: Calculate the product of two numbers</li>
  <li>Input:
    <ul>
      <li>numberA (long): First number</li>
      <li>numberB (long): Second number</li>
    </ul>
  </li>
  <li>Output: MultiplyResult: Contains numberA, numberB, and their product multiplied by a constant</li>
</ul>

<b>getStarWarsCharacter in [SwapiToolsService.java](src/main/java/com/thr/krdk/mcp/server/SwapiToolsService.java)</b>
<ul> 
  <li>Description: Fetch a Star Wars character by ID using the SWAPI</li>
  <li>Input: 
    <ul>
      <li>characterId (string): ID of the Star Wars character to retrieve</li>
    </ul>
  </li>
  <li>Output: JSON: Detailed information about the character (name, birth date, height, mass, list of films, etc.)</li>
</ul>
</div>

## Prompts

<div style="border: 1px solid #050505; padding: 10px; background-color: #0f1fb5;">
<b>greeting in [PromptProvider.java](src/main/java/com/thr/krdk/mcp/server/prompts/PromptProvider.java)</b>
<ul>
  <li>Description: A simple greeting prompt</li>
  <li>Input:
    <ul>
      <li>name (string): The name to greet</li>
    </ul>
  </li>
  <li>Output: GetPromptResult: Kullanıcıya özel karşılama mesajı</li>
</ul>

<b>personalized-message in [PromptProvider.java](src/main/java/com/thr/krdk/mcp/server/prompts/PromptProvider.java)</b>
<ul>
  <li>Description: Generates a personalized message based on user information</li>
  <li>Input:
    <ul>
      <li>name (string): The user's name</li>
      <li>age (integer): The user's age (optional)</li>
      <li>interests (string): The user's interests (optional)</li>
    </ul>
  </li>
  <li>Output: GetPromptResult: Kişiselleştirilmiş mesaj ve kullanıcı bilgileri</li>
</ul>

<b>conversation-starter in [PromptProvider.java](src/main/java/com/thr/krdk/mcp/server/prompts/PromptProvider.java)</b>
<ul>
  <li>Description: Provides a conversation starter with the system</li>
  <li>Input:
    <ul>
      <li>GetPromptRequest: System request object</li>
    </ul>
  </li>
  <li>Output: List<PromptMessage>: MCP hakkında bilgi içeren çoklu mesaj dizisi</li>
</ul>

<b>map-arguments in [PromptProvider.java](src/main/java/com/thr/krdk/mcp/server/prompts/PromptProvider.java)</b>
<ul>
  <li>Description: Demonstrates using a map for arguments</li>
  <li>Input:
    <ul>
      <li>Map<String, Object>: Key-value pairs of arguments</li>
    </ul>
  </li>
  <li>Output: GetPromptResult: Gelen argümanların listesi</li>
</ul>

<b>single-message in [PromptProvider.java](src/main/java/com/thr/krdk/mcp/server/prompts/PromptProvider.java)</b>
<ul>
  <li>Description: Demonstrates returning a single PromptMessage</li>
  <li>Input:
    <ul>
      <li>name (string): The user's name</li>
    </ul>
  </li>
  <li>Output: PromptMessage: Tek mesajlık yanıt</li>
</ul>

<b>string-list in [PromptProvider.java](src/main/java/com/thr/krdk/mcp/server/prompts/PromptProvider.java)</b>
<ul>
  <li>Description: Demonstrates returning a list of strings</li>
  <li>Input:
    <ul>
      <li>topic (string): The topic to provide information about</li>
    </ul>
  </li>
  <li>Output: List<String>: Seçilen konu hakkında bilgi listesi</li>
</ul>
</div>

README.md dosyasına Resources bölümünü aynı formatta ekleyeceğim.


## Resources

<div style="border: 1px solid #050505; padding: 10px; background-color: #0f1fb5;">
<b>database-row in [DatabaseResourceProvider.java](src/main/java/com/thr/krdk/mcp/server/resource/DatabaseResourceProvider.java)</b>
<ul>
  <li>Description: Belirtilen tablo ve ID için veri satırı döner</li>
  <li>Input:
    <ul>
      <li>table (string): Tablo adı</li>
      <li>id (string): Satır ID'si</li>
    </ul>
  </li>
  <li>Output: JSON: Tek bir tablo satırı</li>
</ul>

<b>database-table in [DatabaseResourceProvider.java](src/main/java/com/thr/krdk/mcp/server/resource/DatabaseResourceProvider.java)</b>
<ul>
  <li>Description: Belirtilen tablodaki tüm satırları JSON listesi olarak döner</li>
  <li>Input:
    <ul>
      <li>table (string): Tablo adı</li>
    </ul>
  </li>
  <li>Output: JSON: Tablodaki tüm satırların listesi</li>
</ul>

<b>user-profile in [UserProfileResourceProvider.java](src/main/java/com/thr/krdk/mcp/server/resource/UserProfileResourceProvider.java)</b>
<ul>
  <li>Description: Belirli bir kullanıcı için profil bilgilerini sağlar</li>
  <li>Input:
    <ul>
      <li>username (string): Kullanıcı adı</li>
    </ul>
  </li>
  <li>Output: Text: Kullanıcı profil bilgileri</li>
</ul>

<b>user-attribute in [UserProfileResourceProvider.java](src/main/java/com/thr/krdk/mcp/server/resource/UserProfileResourceProvider.java)</b>
<ul>
  <li>Description: Kullanıcı profilinden belirli bir özelliği döndürür</li>
  <li>Input:
    <ul>
      <li>username (string): Kullanıcı adı</li>
      <li>attribute (string): İstenen özellik (name, email, age, location)</li>
    </ul>
  </li>
  <li>Output: Text: İstenen özelliğin değeri</li>
</ul>

<b>user-status in [UserProfileResourceProvider.java](src/main/java/com/thr/krdk/mcp/server/resource/UserProfileResourceProvider.java)</b>
<ul>
  <li>Description: Kullanıcının mevcut durumunu döndürür</li>
  <li>Input:
    <ul>
      <li>username (string): Kullanıcı adı</li>
    </ul>
  </li>
  <li>Output: Text: Kullanıcının çevrimiçi durumu</li>
</ul>

<b>user-avatar in [UserProfileResourceProvider.java](src/main/java/com/thr/krdk/mcp/server/resource/UserProfileResourceProvider.java)</b>
<ul>
  <li>Description: Kullanıcının avatar resmini base64 kodlanmış olarak döndürür</li>
  <li>Input:
    <ul>
      <li>username (string): Kullanıcı adı</li>
    </ul>
  </li>
  <li>Output: Image: Base64 kodlanmış avatar resmi</li>
</ul>
</div>

## Sampling

<div style="border: 1px solid #050505; padding: 10px; background-color: #0f1fb5;">
<b>getTemperature in [WeatherSamplingService.java](src/main/java/com/thr/krdk/mcp/server/sampling/WeatherSamplingService.java)</b>
<ul>
  <li>Description: Get the temperature (in celsius) for a specific location</li>
  <li>Input:
    <ul>
      <li>latitude (double): The location latitude</li>
      <li>longitude (double): The location longitude</li>
    </ul>
  </li>
  <li>Output: String: Contains two AI-generated poems about the weather (from Gemma and Qwen models) and the raw weather data in JSON format</li>
</ul>

<b>callMcpSampling in [WeatherSamplingService.java](src/main/java/com/thr/krdk/mcp/server/sampling/WeatherSamplingService.java)</b>
<ul>
  <li>Description: Creates poetic interpretations of weather data using multiple AI models</li>
  <li>Input:
    <ul>
      <li>toolContext (ToolContext): MCP exchange context</li>
      <li>weatherResponse (WeatherResponse): Current weather data</li>
    </ul>
  </li>
  <li>Output: String: Combined output of poems from different AI models and original weather data</li>
</ul>
</div>


# TIME TO PRODUCTION

<div style="border: 1px solid #050505; padding: 10px; background-color: #050505;">
  Note: To use docker, the podman command should be replaced by podman,
</div>

## Podman Image

There are tow type Docker image for Spring MCP server for this projecy

### SSE Docker Image

- To crate SSE docker image use  [Dockerfile](dockers/sse/Dockerfile)

```shell
 podman build   -f dockers/sse/Dockerfile   -t springboot-mcp-starter-sse  .
```

### STDIO Docker Image

- To create STDIO docker image use [Dockerfile](dockers/stdio/Dockerfile)

```shell
  podman build   -f dockers/stdio/Dockerfile   -t springboot-mcp-starter-stdio  .
```

<div style="border: 1px solid #050505; padding: 10px; background-color: #132285;">
  Note-1 : In some cases, below command may not work. In that case, you can use following command to build the image. 
  Note-2:  Dockerfile and jar file should be in the same directory.
  Note-3: change this  "COPY target/springboot-mcp-starter.jar app.jar" to "COPY springboot-mcp-starter.jar app.jar" in the Dockerfile

sse
```sh
 podman build -t springboot-mcp-starter-sse  .
```

stdio
```sh
 podman build -t springboot-mcp-starter-stdio  .
```

</div>

## Create Springboot MCP  container For SSE

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
podman run -d --name springboot-mcp-starter-sse -p 8080:8080 springboot-mcp-starter-sse
```

# podman Run in debug mode for podman

```sh
podman run -d --name springboot-mcp-starter-sse -p 8080:8080 -p 5005:5005 -e JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005" springboot-mcp-starter
```

## Intellij settings for debugging

* Go to Run > Edit Configurations
* Click on the "+" icon and select "Remote"
* Set the following parameters:
    * Name: Remote Debug
    * Host: localhost
    * Port: 5005
* Click "Apply" and  "OK" to save the configuration
 

## Host / Client settings

<div style="border: 1px solid #050505; padding: 10px; background-color: #050505;">
  **Note:** To use podman, the podman command should be replaced by podman in the command property.
</div>

### Claude Desktop

this configuration should be added to claude_desktop_config.json

```json
{
  "mcpServers": {
    "springboot-mcp-starter": {
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
        "springboot-mcp-starter-stdio"
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
    "springboot-mcp-starter": {
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
        "springboot-mcp-starter-stdio"
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
      "springboot-mcp-starter": {
        "command": "podman",
        "args": [
          "run",
          "--rm",
          "-i",
          "springboot-mcp-starter-stdio"
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
