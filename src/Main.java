import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties config = new Properties();

        // Cloud-ready: load from environment variables
        config.setProperty("nasa.api.key", System.getenv("NASA_API_KEY"));
        config.setProperty("email.sender", System.getenv("EMAIL_SENDER"));
        config.setProperty("email.password", System.getenv("EMAIL_PASSWORD"));
        config.setProperty("email.recipient", System.getenv("EMAIL_RECIPIENT"));

        String fact = ApiFetcher.fetchSpaceFact(config);
        EmailSender.sendEmail("🚀 Today's Space Fact", fact, config);
    }
}
