package fuellogg.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/about")
    private String about(){
        return "about";
    }

    @GetMapping("/home")
    private String homePage(){
        return "home";
    }
}
