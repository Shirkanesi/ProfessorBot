package de.shirkanesi.professorbot.youtube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class YouTubeVideo {

    private static final String VIDEO_URL_STRUCTURE
            = "https://youtube.googleapis.com/youtube/v3/videos?part=snippet,contentDetails,statistics&id=%s&key=%s";

    private static final String API_KEY = "AIzaSyCkArrIhAWb0eZs72v3niRMFX93FehxxLc";

    private final String videoId;

    public YouTubeVideo(String videoId) {
        this.videoId = videoId;
    }

    private String buildRequestUrl() {
        return String.format(VIDEO_URL_STRUCTURE, videoId, API_KEY);
    }

    private SnippetData requestData() throws URISyntaxException, IOException {
        // Create a neat value object to hold the URL
        URL url = new URL(this.buildRequestUrl());

        // Open a connection(?) on the URL(??) and cast the response(???)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Now it's "open", we can set the request method, headers etc.
        connection.setRequestProperty("accept", "application/json");

        // This line makes the request
        InputStream responseStream = connection.getInputStream();
        BufferedReader r = new BufferedReader(new InputStreamReader(responseStream));

        StringBuilder input = new StringBuilder();
        String read;
        HashMap<String, String> snippetContent = new HashMap<>();
        boolean inSnippet = false;
        while ((read = r.readLine()) != null) {
            input.append(read.trim()).append(System.lineSeparator());
            if(read.trim().startsWith("\"snippet\"")){
                inSnippet = true;
            }
            if(inSnippet){
                if(!read.trim().endsWith("{") && read.contains(":")){
                    snippetContent.put(read.trim().split(":")[0].replaceAll("\"", ""),
                            read.trim().split(":", 2)[1].trim().replaceAll("[\"|,]]", ""));
                }
            }
        }
/*
        System.out.println(input);
*/
        snippetContent.forEach((k, v) ->{
            System.out.println(String.format("%s: %s", k, v));
        });

        return new SnippetData(snippetContent.get("publishedAt"), snippetContent.get("title"), snippetContent.get("channelTitle"));
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        SnippetData d = new YouTubeVideo("j-p2t670O3g").requestData();
        System.out.println("-----");
        SimpleDateFormat sdf = new SimpleDateFormat(d.publishedAt.replace('T', ' '));
        System.out.println(d.title + "  by  " + d.channelTitle);
        System.out.println(sdf.toLocalizedPattern());
    }


    private class SnippetData{
        public String publishedAt;
        public String title;
        public String channelTitle;

        public SnippetData(String publishedAt, String title, String channelTitle) {
            this.publishedAt = publishedAt;
            this.title = title;
            this.channelTitle = channelTitle;
        }
    }

    private enum ThumbnailResolutions {
        PX120_90("default.jpg"), PX320_180("mqdefault.jpg"), PX480_360("hqdefault.jpg"), PX640_480(
                "sddefault.jpg"), PX1280_720("maxresdefault.jpg");

        private final String fileName;

        ThumbnailResolutions(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }

        public String getThumbnailUrl(String videoId){
            return String.format("https://i.ytimg.com/v1/%s/%s", videoId, this.getFileName());
        }

        public static String getThumbnailUrl(String videoId, ThumbnailResolutions res){
            return res.getThumbnailUrl(videoId);
        }
    }


}
