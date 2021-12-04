package fuellogg.config;

import fuellogg.web.interceptor.LogInterceptor;
import fuellogg.web.interceptor.StatsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfiguration  implements WebMvcConfigurer {

    private final StatsInterceptor statsInterceptor;
    private final LogInterceptor logInterceptor;

    public WebConfiguration(StatsInterceptor statsInterceptor, LogInterceptor logInterceptor) {
        this.statsInterceptor = statsInterceptor;
        this.logInterceptor = logInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(statsInterceptor);
        registry.addInterceptor(logInterceptor);
    }
}
