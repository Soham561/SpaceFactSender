import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import org.json.JSONObject;

public class ApiFetcher {
    private static final String NASA_API_URL = "https://api.nasa.gov/planetary/apod?api_key=";

    public static String fetchSpaceFact(Properties config) {
        try {
            String apiKey = config.getProperty("nasa.api.key");
            URL url = new URL(NASA_API_URL + apiKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            JSONObject json = new JSONObject(response.toString());
            String title = json.getString("title");
            String explanation = json.getString("explanation");
            String date = json.getString("date");
            String imageUrl = json.getString("url");

            // Build beautiful HTML content
            StringBuilder html = new StringBuilder();
            html.append("<div style=\"font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; padding: 20px; background-color: #0b0c10; color: #c5c6c7; border-radius: 10px;\">");
            html.append("<h1 style=\"text-align: center; color: #66fcf1; font-size: 36px;\">🚀 NASA's Space Fact of the Day</h1>");
            html.append("<h2 style=\"color: #45a29e; font-size: 24px; margin-top: 30px;\">").append(title).append("</h2>");
            html.append("<p style=\"color: #a9a9a9; font-size: 18px;\"><strong>Date:</strong> ").append(date).append("</p>");
            html.append("<img src='").append(imageUrl).append("' alt='NASA Image' style='max-width:100%; border-radius: 15px; margin: 20px 0; box-shadow: 0 0 10px #45a29e;' />");
            html.append("<p style=\"line-height: 1.6; font-size: 18px; text-align: justify;\">").append(explanation).append("</p>");
            html.append("<hr style=\"margin-top: 40px; border: none; border-top: 2px solid #45a29e;\" />");
            html.append("<p style=\"font-size: 14px; text-align: center; color: #66fcf1;\">Created with 💙 by <strong>SpaceFactSender Java App</strong></p>");
            html.append("</div>");

            return html.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Failed to fetch space fact.";
        }
    }
}
