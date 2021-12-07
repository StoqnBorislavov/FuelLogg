package fuellogg.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    public static final String PAGE_NOT_FOUND = "Page not found!";

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAnyError(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", e.getMessage());
        modelAndView.setViewName("page.html");
//            modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
