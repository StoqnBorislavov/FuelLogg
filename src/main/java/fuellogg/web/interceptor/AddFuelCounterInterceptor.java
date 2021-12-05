package fuellogg.web.interceptor;

import fuellogg.service.StatsService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AddFuelCounterInterceptor implements HandlerInterceptor {

    private final StatsService statsService;

    public AddFuelCounterInterceptor(StatsService statsService) {
        this.statsService = statsService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        statsService.countFuelHits();
        return true;
    }
}
