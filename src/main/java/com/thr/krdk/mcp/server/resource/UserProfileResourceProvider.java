package com.thr.krdk.mcp.server.resource;

import com.logaritex.mcp.annotation.McpResource;
import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema.ReadResourceRequest;
import io.modelcontextprotocol.spec.McpSchema.ReadResourceResult;
import io.modelcontextprotocol.spec.McpSchema.ResourceContents;
import io.modelcontextprotocol.spec.McpSchema.TextResourceContents;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Kullanıcı profili verilerini “user-profile://{username}” gibi URI’ler üzerinden dışarı açan sınıf.
 */
@Service
public class UserProfileResourceProvider {

    private final Map<String, Map<String, String>> userProfiles = new HashMap<>();

    //Kullanıcı veritabanı yarat
    public UserProfileResourceProvider() {
        // Initialize with some sample data
        Map<String, String> ahmet = new HashMap<>();
        ahmet.put("name", "Ahmet Simit");
        ahmet.put("email", "ahmet.simit@deneme.com");
        ahmet.put("age", "32");
        ahmet.put("location", "Çorum");

        Map<String, String> ayse = new HashMap<>();
        ayse.put("name", "Ayşe Döne");
        ayse.put("email", "ayse.done@gdeneme.com");
        ayse.put("age", "28");
        ayse.put("location", "Yozgat");

        Map<String, String> ali = new HashMap<>();
        ali.put("name", "Ali Mermer");
        ali.put("email", "ali.mermer@deneme.com");
        ali.put("age", "45");
        ali.put("location", "Çankırı");

        Map<String, String> hacer = new HashMap<>();
        hacer.put("name", "Hacer Kaya");
        hacer.put("email", "hacer.kaya@demeöe.com");
        hacer.put("age", "36");
        hacer.put("location", "Bilecik");

        userProfiles.put("ahmet", ahmet);
        userProfiles.put("ayse", ayse);
        userProfiles.put("ali", ali);
        userProfiles.put("hacer", hacer);
    }

    /**
     * ReadResourceRequest (URI ve fetch parametreleri) ile username argümanını alır; userProfiles’den
     * ilgili profili çekip formatProfileInfo ile metne dönüştürür
     * ve sonucu ReadResourceResult içinde bir TextResourceContents olarak döner.
     * <p>
     * Client, resources/read isteği gönderdiğinde, kullanıcı profilini doğrudan okuyup sohbet bağlamına eklemek için kullanılır.
     */
    @McpResource(uri = "user-profile://{username}", name = "User Profile", description = "Provides user profile information for a specific user")
    public ReadResourceResult getUserProfile(ReadResourceRequest request, String username) {
        String profileInfo = formatProfileInfo(userProfiles.getOrDefault(username.toLowerCase(), new HashMap<>()));

        return new ReadResourceResult(List.of(new TextResourceContents(request.uri(), "text/plain", profileInfo)));
    }

    /**
     * Yalnızca username (URI değişkeni) alır; ReadResourceRequest yerine doğrudan parametre binding yaparak userProfiles’den profili çeker
     */
    @McpResource(uri = "user-profile://{username}", name = "User Details", description = "Provides user details for a specific user using URI variables")
    public ReadResourceResult getUserDetails(String username) {
        String profileInfo = formatProfileInfo(userProfiles.getOrDefault(username.toLowerCase(), new HashMap<>()));

        return new ReadResourceResult(
                List.of(new TextResourceContents("user-profile://" + username, "text/plain", profileInfo)));
    }

    /**
     * username ve attribute (ör. name, email, age, location) parametrelerini alır; ilgili profil değerini döner, yoksa "Attribute not found" mesajı üretir.
     * <p>
     * örneğin yalnızca e-posta adresi gerektiğinde tüm profili değil, tek bir değeri sağlamak.
     */
    @McpResource(uri = "user-attribute://{username}/{attribute}", name = "User Attribute", description = "Provides a specific attribute from a user's profile")
    public ReadResourceResult getUserAttribute(String username, String attribute) {
        Map<String, String> profile = userProfiles.getOrDefault(username.toLowerCase(), new HashMap<>());
        String attributeValue = profile.getOrDefault(attribute, "Attribute not found");

        return new ReadResourceResult(
                List.of(new TextResourceContents("user-attribute://" + username + "/" + attribute, "text/plain",
                        username + "'s " + attribute + ": " + attributeValue)));
    }

    /**
     * İstek bağlamındaki kimlik bilgisi veya meta verilere göre farklı iş akışları uygulamak gerektiğinde kullanılır.
     */
    @McpResource(uri = "user-profile-exchange://{username}", name = "User Profile with Exchange", description = "Provides user profile information with server exchange context")
    public ReadResourceResult getProfileWithExchange(McpSyncServerExchange exchange, String username) {
        String profileInfo = formatProfileInfo(userProfiles.getOrDefault(username.toLowerCase(), new HashMap<>()));

        return new ReadResourceResult(List.of(new TextResourceContents("user-profile-exchange://" + username,
                "text/plain", "Profile with exchange for " + username + ": " + profileInfo)));
    }

    /**
     * Kullanıcının bağlantılarını doğrudan List<String> olarak döner (örn. ALi, Ayşe, Hacer).
     */
    @McpResource(uri = "user-connections://{username}", name = "User Connections", description = "Provides a list of connections for a specific user")
    public List<String> getUserConnections(String username) {
        // Generate a simple list of connections based on username
        return List.of(username + " is connected with Hacer", username + " is connected with Ali",
                username + " is connected with Charlie");
    }

    /**
     * exchange, request ve username parametrelerini alır; birden fazla ResourceContents öğesi (TextResourceContents) döndürür.
     */
    @McpResource(uri = "user-notifications://{username}", name = "User Notifications", description = "Provides notifications for a specific user")
    public List<ResourceContents> getUserNotifications(McpSyncServerExchange exchange, ReadResourceRequest request,
                                                       String username) {
        // Generate notifications based on username
        String notifications = generateNotifications(username);

        return List.of(new TextResourceContents(request.uri(), "text/plain", notifications));
    }

    /**
     * Kullanıcıların durum bilisini döner
     * type.
     */
    @McpResource(uri = "user-status://{username}", name = "User Status", description = "Provides the current status for a specific user")
    public ResourceContents getUserStatus(ReadResourceRequest request, String username) {
        // Generate a simple status based on username
        String status = generateUserStatus(username);

        return new TextResourceContents(request.uri(), "text/plain", status);
    }

    /**
     * Kullanıcının memleketini döner
     */
    @McpResource(uri = "user-location://{username}", name = "User Location", description = "Provides the current location for a specific user")
    public String getUserLocation(String username) {
        Map<String, String> profile = userProfiles.getOrDefault(username.toLowerCase(), new HashMap<>());

        // Extract location from profile data
        return profile.getOrDefault("location", "Location not available");
    }

    /**
     * Avatar bilgisini döner. image tipini de desteklediğini göstermek için eklenmiştir.
     */
    @McpResource(uri = "user-avatar://{username}", name = "User Avatar", description = "Provides a base64-encoded avatar image for a specific user", mimeType = "image/png")
    public String getUserAvatar(ReadResourceRequest request, String username) {
        // In a real implementation, this would be a base64-encoded image
        // For this example, we're just returning a placeholder string
        return "base64-encoded-avatar-image-for-" + username;
    }

    private String formatProfileInfo(Map<String, String> profile) {
        if (profile.isEmpty()) {
            return "User profile not found";
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : profile.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString().trim();
    }

    private String generateNotifications(String username) {
        // Simple logic to generate notifications
        return "You have 3 new messages\n" + "2 people viewed your profile\n" + "You have 1 new connection request";
    }

    private String generateUserStatus(String username) {
        // Simple logic to generate a status
        if (username.equals("Ahmet")) {
            return "🟢 Çevrimiçi";
        } else if (username.equals("Ayşe")) {
            return "🟠 Dışarda";
        } else if (username.equals("Ali")) {
            return "⚪ Çevrimdışı";
        } else if (username.equals("Have")) {
            return "🔴 ÖEgül";
        } else {
            return "⚪ Çevrimdışı";
        }
    }

}