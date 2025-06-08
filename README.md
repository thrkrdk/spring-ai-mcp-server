# Spring boot Starter for swapi MCP Servers With Spring AI

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
<b>whoAmI in [WhoAmIToolsService.java](src/main/java/com/thr/krdk/swapi/mcp/server/WhoAmIToolsService.java)</b>
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