package fuellogg.web;

import fuellogg.model.view.VehicleViewModel;
import fuellogg.service.VehicleService;
import fuellogg.service.impl.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private final VehicleService vehicleService;

    @Autowired
    public HomeController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/about")
    private String about(){
        return "about";
    }

    @GetMapping("/home")
    private String homePage(Model model,@AuthenticationPrincipal MyUser user){
        List<VehicleViewModel> myVehicles = this.vehicleService.findMyVehicles(user.getUserIdentifier());
        model.addAttribute("myVehicles", myVehicles);
        model.addAttribute("vehiclesCount", myVehicles.size());
        return "home";
    }
}
