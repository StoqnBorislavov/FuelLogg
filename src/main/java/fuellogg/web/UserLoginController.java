package fuellogg.web;

import fuellogg.model.binding.ChangePasswordBindingModel;
import fuellogg.service.UserService;
import fuellogg.service.impl.MyUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserLoginController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserLoginController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute("changePasswordBindingModel")
    public ChangePasswordBindingModel changePasswordBindingModel() {
        return new ChangePasswordBindingModel();
    }


    @GetMapping("/login")
    public String getLogin(Model model) {
        return "login";
    }

    @PostMapping("/login-error")
    public String failedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                    String userName, RedirectAttributes attributes) {
        attributes.addFlashAttribute("bad_credentials", true);
        attributes.addFlashAttribute("username", userName);

        return "redirect:/users/login";
    }

    @GetMapping("/changePassword")
    public String getChangePassword() {
        return "change-password";
    }

    @PostMapping("/changePassword")
    public String changePassword(ChangePasswordBindingModel changePasswordBindingModel, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes, @AuthenticationPrincipal MyUser user) {

        if (this.userService.checkCurrentPassword(user.getUserIdentifier(), changePasswordBindingModel.getConfirmPassword()) || bindingResult.hasErrors() || !changePasswordBindingModel.getNewPassword()
                .equals(changePasswordBindingModel.getConfirmPassword())) {
            if(this.userService.checkCurrentPassword(user.getUserIdentifier(), changePasswordBindingModel.getConfirmPassword())){
                redirectAttributes.addFlashAttribute("currentPasswordsNotMatch", true);
            }
            redirectAttributes.addFlashAttribute("changePasswordBindingModel", changePasswordBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.changePasswordBindingModel",
                            bindingResult);
            if (!changePasswordBindingModel.getNewPassword().equals(changePasswordBindingModel.getConfirmPassword())) {
                redirectAttributes.addFlashAttribute("passwordsNotMatch", true);
            }
            return "redirect:/users/changePassword";
        }


        this.userService.changePassword(changePasswordBindingModel.getNewPassword(), user.getUserIdentifier());

        return "redirect:/home";
    }

}
