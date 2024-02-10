import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MarsController {

    @GetMapping("/")
    public String index(Model model) {
        // You can add any necessary initialization logic here
        return "index";
    }
}
