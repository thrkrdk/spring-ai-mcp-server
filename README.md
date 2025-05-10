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

## Tools

<div style="border: 1px solid #050505; padding: 10px; background-color: #0f1fb5;">
<b>getStarWarsCharacter</b>
<ul> 
  <li>Description: Fetch a Star Wars character by ID using the SWAPI</li>
  <li>Input: 
    <ul>
      <li>characterId (string): ID of the Star Wars character to retrieve</li>
    </ul>
  </li>
  <li>Output: JSON: Detailed information about the character (name, birth date, height, mass, list of films, etc.)</li>
</ul>
<b>sum</b>
<ul> 
  <li>Description: Sums two numbers</li>
  <li>Input: 
    <ul>
      <li>numberA (long): First number</li>
      <li>numberB (long): Second number</li>
    </ul>
  </li>
  <li>Output: long: Sum of numberA and numberB</li>
</ul>
    <b>subtract</b>
    <ul> 
      <li>Description: Calculate the difference of two numbers
      <li> Input: numberA (long): First number, numberB (long): Second number
      <li> Output: long: Difference of numberA and numberB multiplied by a constant
    </ul>
    <b>multiply</b>
    <ul> 
      <li>Description: Calculate the product of two numbers
      <li> Input: numberA (long): First number, numberB (long): Second number
      <li> Output: MultiplyResult: Contains numberA, numberB, and their product multiplied by a constant
    </ul>

</div>
