package fuellogg.config;

import fuellogg.web.interceptor.AddExpensesCounterInterceptor;
import fuellogg.web.interceptor.AddFuelCounterInterceptor;
import fuellogg.web.interceptor.LogInterceptor;
import fuellogg.web.interceptor.StatsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfiguration  implements WebMvcConfigurer {

    private final StatsInterceptor statsInterceptor;
    private final LogInterceptor logInterceptor;
    private final AddFuelCounterInterceptor addFuelCounterInterceptor;
    private final AddExpensesCounterInterceptor addExpensesCounterInterceptor;

    public WebConfiguration(StatsInterceptor statsInterceptor, LogInterceptor logInterceptor, AddFuelCounterInterceptor addFuelCounterInterceptor, AddExpensesCounterInterceptor addExpensesCounterInterceptor) {
        this.statsInterceptor = statsInterceptor;
        this.logInterceptor = logInterceptor;
        this.addFuelCounterInterceptor = addFuelCounterInterceptor;
        this.addExpensesCounterInterceptor = addExpensesCounterInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(statsInterceptor);
        registry.addInterceptor(addFuelCounterInterceptor).addPathPatterns("/statistics/addFuel/**");
        registry.addInterceptor(addExpensesCounterInterceptor).addPathPatterns("/statistics/addExpenses/**");
        registry.addInterceptor(logInterceptor);
    }
}
