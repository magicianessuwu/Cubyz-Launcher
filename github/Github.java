package github;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.net.HttpURLConnection;
import java.net.URL;

public class Github {

    protected static final String BASE_URL = "https://api.github.com/repos/";

    public static String getApiKey() throws IOException {
        return Files.readString(Path.of("config.txt"));
    }

    public static String sendGetRequest(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "token " + getApiKey());
        
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Failed " + url + " : HTTP error code : " + responseCode + " : " + connection.getResponseMessage());
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public static Repository getRepo(String owner, String repo) {
        return new Repository(owner, repo);
    }
}