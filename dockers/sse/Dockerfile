FROM eclipse-temurin:21.0.6_7-jre-ubi9-minimal

WORKDIR /app

COPY target/swapi-mcp-starter.jar app.jar

# INFO log’ları kapatıp yalnızca ERROR ve
# MCP-STDIO JSON’ını stdout’a bırakıyoruz:
ENTRYPOINT ["java","-Dspring.main.banner-mode=console","-Dlogging.level.root=ERROR","-Dspring.ai.mcp.server.stdio=false" ,"-Dspring.ai.mcp.server.type=ASYNC","-jar","app.jar"]