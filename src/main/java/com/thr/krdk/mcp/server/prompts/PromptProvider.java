package com.thr.krdk.mcp.server.prompts;

import com.logaritex.mcp.annotation.McpArg;
import com.logaritex.mcp.annotation.McpPrompt;
import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema.GetPromptRequest;
import io.modelcontextprotocol.spec.McpSchema.GetPromptResult;
import io.modelcontextprotocol.spec.McpSchema.LoggingLevel;
import io.modelcontextprotocol.spec.McpSchema.LoggingMessageNotification;
import io.modelcontextprotocol.spec.McpSchema.PromptMessage;
import io.modelcontextprotocol.spec.McpSchema.Role;
import io.modelcontextprotocol.spec.McpSchema.TextContent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PromptProvider {
    private static final long AGE_30 = 30;
    private static final long AGE_60 = 30;

    // bu methoda gecmeden önce anotasyon kullanılmadan  prompt nasıl oluştururlur ona bakalım
    // McpStarterApplication. java'da satır 31'de örnek mevcut:


    /**
     * Zorunlu name parametresiyle kullanıcıya basit bir selam mesajı gönderir.
     * Uygulama açılışında veya onboarding’de hızlıca “Merhaba” demek.
     *
     * @param name: zorunlu
     * @return onboarding karşılama mesajı
     */
    @McpPrompt(name = "greeting", description = "A simple greeting prompt")
    public GetPromptResult greetingPrompt(
            @McpArg(name = "name", description = "The name to greet", required = true) String name) {
        return new GetPromptResult("Greeting", List.of(new PromptMessage(Role.ASSISTANT,
                new TextContent("Merhaba, " + name + "! MCP dünyasına hoş geldiniz."))));
    }


    /**
     * name, age ve interests parametreleriyle kişiye özel bir mesaj oluşturur ve sunucu tarafında event loglama yapar.
     * <p>
     * Yaşa göre farklı mesaj varyasyonları (“so much ahead of you”, “gained valuable life experience”, “accumulated wisdom to share”)
     * üreterek kullanıcı profiline özel etkileşim sunmak.
     *
     * @param exchange  exchange server
     * @param name      Kullanıcının adı
     * @param age       Kullanıcının yası
     * @param interests Kullanıcının ilgi alanları
     * @return Kişiselleştirilmis mesaj
     */
    @McpPrompt(name = "personalized-message",
            description = "Generates a personalized message based on user information")
    public GetPromptResult personalizedMessage(McpSyncServerExchange exchange,
                                               @McpArg(name = "name", description = "The user's name", required = true) String name,
                                               @McpArg(name = "age", description = "The user's age", required = false) Integer age,
                                               @McpArg(name = "interests", description = "The user's interests", required = false) String interests) {

        exchange.loggingNotification(LoggingMessageNotification.builder()
                .level(LoggingLevel.INFO)
                .data("personalized-message event").build());

        StringBuilder message = new StringBuilder();
        message.append("Hello, ").append(name).append("!\n\n");

        if (age != null) {
            message.append(age).append(" yaşındasınız?");
            if (age < AGE_30) {
                message.append("Daha yolun başındasınız.\n\n");
            } else if (age < AGE_60) {
                message.append("Harika Tecürbelerle yaşanmış bir hayat.\n\n");
            } else {
                message.append("Bilgelik dönemi.\n\n");
            }
        }

        if (interests != null && !interests.isEmpty()) {
            message.append("İlgi Alanlarınız:")
                    .append(interests)
                    .append(". \n\n Merakınız ve Öğrenme tutkunuz inanılmaz.\n\n");
        }

        message
                .append(" her türlü sorunuzda size yardımcı olmak için buradayım.");

        return new GetPromptResult("Kişiselleştirilmiş Mesaj",
                List.of(new PromptMessage(Role.ASSISTANT, new TextContent(message.toString()))));
    }

    /**
     * birden fazla PromptMessage döndüren çok adımlı bir diyalog başlangıcı sağlar..
     * Chat uygulamalarında kullanıcıya rehberli bir sohbet akışı sunmak için kullanılır
     *
     * @param request:
     * @return bir mesaj listesi döner
     */
    @McpPrompt(name = "conversation-starter", description = "Provides a conversation starter with the system")
    public List<PromptMessage> conversationStarter(GetPromptRequest request) {
        return List.of(
                new PromptMessage(Role.ASSISTANT,
                        new TextContent("Merhaba! Ben MCP asistanıyım. Bugün size nasıl yardımcı olabilirim?")),
                new PromptMessage(Role.USER,
                        new TextContent("Model Context  Protokolü hakkında daha fazla bilgi edinmek istiyorum.")),
                new PromptMessage(Role.ASSISTANT, new TextContent(
                        "Harika bir seçim! Model Bağlam Protokolü (MCP), " +
                                "sunucuların dil modelleriyle iletişim kurması için standartlaştırılmış bir yoldur." +
                                " Bilgi alışverişi, istekte bulunma ve yanıtları işleme için yapılandırılmış" +
                                " bir yaklaşım sağlar." +
                                " İlk olarak hangi özel yönü keşfetmek istersiniz?")));
    }

    /**
     * Map<String, Object> tipinde argümanları alır ve tüm anahtar-değer çiftlerini listeleyen bir mesaj oluşturur.
     * Dinamik veya önceden bilinmeyen parametre setlerini keşfetmek ve hata ayıklama için kullanılabilir.
     *
     * @param arguments argüman listesi
     * @return sonuç
     */
    @McpPrompt(name = "map-arguments", description = "Demonstrates using a map for arguments")
    public GetPromptResult mapArguments(Map<String, Object> arguments) {
        StringBuilder message = new StringBuilder("Aşağıdaki argümanları parametre olarak geldi: \n\n");

        if (arguments != null && !arguments.isEmpty()) {
            for (Map.Entry<String, Object> entry : arguments.entrySet()) {
                message.append("- ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
        } else {
            message.append("Herhangi bir argüman yok..");
        }

        return new GetPromptResult("Map Argument örneği",
                List.of(new PromptMessage(Role.ASSISTANT, new TextContent(message.toString()))));
    }

    /**
     * Minimal bilgi taleplerinde, hızlı ve doğrudan yanıt sunmak için ideal bir kullanılabilir.
     *
     * @param name Kullanıcı adı
     * @return Prompt
     */
    @McpPrompt(name = "single-message", description = "Demonstrates returning a single PromptMessage")
    public PromptMessage singleMessagePrompt(
            @McpArg(name = "name", description = "The user's name", required = true) String name) {
        return new PromptMessage(Role.ASSISTANT,
                new TextContent("Hello, " + name + "! This is a single message response."));
    }

    /**
     * topic parametresine göre List<String> döndürür; örneğin “MCP” seçildiğinde üç maddelik bilgi dizisi sunar, aksi halde hata mesajları içerir.
     * Liste formatıyla istemci tarafında madde işaretli bilgi kartları veya kontrol listeleri oluşturmak için idealdir.
     *
     * @param topic seçilen konu
     * @return Liste halinde promptlar
     */
    @McpPrompt(name = "string-list", description = "Demonstrates returning a list of strings")
    public List<String> stringListPrompt(@McpArg(name = "topic",
            description = "The topic to provide information about", required = true) String topic) {
        if ("MCP".equalsIgnoreCase(topic)) {
            return List.of(
                    "Model Context Protokolü (MCP), sunucuların dil modelleriyle iletişim kurması için standartlaştırılmış bir yoldur.",
                    "Bilgi alışverişi, istekte bulunma ve yanıtları işleme için yapılandırılmış bir yaklaşım sağlar.",
                    "MCP, sunucuların kaynakları, araçları ve istemcileri tutarlı bir şekilde ortaya çıkarmasına olanak tanır.");
        } else {
            return List.of(topic + " hakkında özel bir bilgim yok.",
                    "Lütfen farklı bir konu deneyin veya daha spesifik bir soru sorun.");
        }
    }

}
