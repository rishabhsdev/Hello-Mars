import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/* 
 * Custom workaround API to fetch data from Curiosity rover instead, after insight rover stopped sending data.
 */

@SpringBootApplication
public class CuriosityAPI {

    public static void main(String[] args) {
        SpringApplication.run(MarsDataApplication.class, args);
    }

    @RestController
    public static class MarsController {

        @GetMapping("/")
        public List<Object> scrapeData() throws IOException {
            List<Object> extractedData = new ArrayList<>();

            // Web scraping for weather data
            Document doc = Jsoup.connect("https://mars.nasa.gov/msl/weather/").get();
            Elements weatherRows = doc.select("#weather_observation > tbody > tr:gt(1)");

            for (Element row : weatherRows) {
                String date = row.select("th:nth-child(1)").text();
                String sol = row.select("th:nth-child(2)").text();
                String minTemp = row.select("td.temperature.min > span.fahrenheit > nobr").text();
                String maxTemp = row.select("td.temperature.max > span.fahrenheit > nobr").text();

                extractedData.add(new MarsWeatherData(date, sol, minTemp, maxTemp));
            }

            // Fetching rover photos
            List<String> photos = fetchRoverPhotos("curiosity", 3000, "DEMO_KEY");
            int randomIndex = (int) (Math.random() * photos.size());
            String randomPhotoUrl = photos.get(randomIndex);
            extractedData.add(randomPhotoUrl);

            return extractedData;
        }

        private List<String> fetchRoverPhotos(String rover, int sol, String apiKey) throws IOException {
            String url = String.format("https://api.nasa.gov/mars-photos/api/v1/rovers/%s/photos?sol=%d&api_key=%s", rover, sol, apiKey);
            Document doc = Jsoup.connect(url).get();
            Elements photoElements = doc.select("img_src");
            List<String> photos = new ArrayList<>();
            for (Element photo : photoElements) {
                photos.add(photo.attr("src"));
            }
            return photos;
        }
    }

    static class MarsWeatherData {
        private String date;
        private String sol;
        private String minTemp;
        private String maxTemp;

        public MarsWeatherData(String date, String sol, String minTemp, String maxTemp) {
            this.date = date;
            this.sol = sol;
            this.minTemp = minTemp;
            this.maxTemp = maxTemp;
        }

        // Getters and setters
    }
}
