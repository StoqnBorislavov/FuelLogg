package fuellogg.web;

import fuellogg.service.StatsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }


    @GetMapping("/statistics")
    public String statistics(@AuthenticationPrincipal Principal user, Model model){
        model.addAttribute("stats", this.statsService.getStatistic());
        return "stats";
    }
}
