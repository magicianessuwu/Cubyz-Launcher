package github;

public class Commit {
    private String message;
    private String sha;
    private String author;
    private String date;

    public Commit(String message, String sha, String author, String date) {
        this.message = message;
        this.sha = sha;
        this.author = author;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public String getSha() {
        return sha;
    }

    public String getShortSha() {
        return sha.substring(0, 7);
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Commit{" +
               "message='" + message + '\'' +
               ", sha='" + sha + '\'' +
               ", author='" + author + '\'' +
               ", date='" + date + '\'' +
               '}';
    }
}