package github;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class Repository {

    private String owner;
    private String name;

    public Repository(String owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    public List<Commit> getCommits() throws IOException {
        List<Commit> commits = new ArrayList<>();
        int page = 1;
        boolean hasMoreCommits = true;
    
        while (hasMoreCommits) {
            String url = Github.BASE_URL + owner + "/" + name + "/commits?page=" + page;
            String jsonResponse = Github.sendGetRequest(url);
    
            JSONArray commitsArray = new JSONArray(jsonResponse);
    
            if (commitsArray.length() == 0) {
                hasMoreCommits = false;
                break;
            }
    
            for (int i = 0; i < commitsArray.length(); i++) {
                JSONObject commitObject = commitsArray.getJSONObject(i);
                JSONObject commitDetails = commitObject.getJSONObject("commit");
                String message = commitDetails.getString("message");
                String sha = commitObject.getString("sha");
                String author = commitDetails.getJSONObject("author").getString("name");
                String date = commitDetails.getJSONObject("author").getString("date");
                
                Commit commit = new Commit(message, sha, author, date);
                commits.add(commit);
            }
    
            page++;
        }
    
        return commits;
    }
}