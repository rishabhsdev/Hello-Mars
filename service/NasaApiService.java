import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NasaApiService {

    private final String apiKey = "YOUR_API_KEY";
    private final String rover = "curiosity";
    private final int sol = 1000; // The Martian sol (day) for which you want to fetch photos
    private final String apiUrl = "https://api.nasa.gov/mars-photos/api/v1/rovers/" + rover + "/photos?sol=" + sol + "&api_key=" + apiKey;

    private final RestTemplate restTemplate;

    public NasaApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String[] fetchRoverPhotos() {
        String[] photos = restTemplate.getForObject(apiUrl, String[].class);
        return photos;
    }
}
