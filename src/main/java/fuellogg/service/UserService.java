package fuellogg.service;

import fuellogg.model.entity.User;
import fuellogg.model.service.UserRegisterServiceModel;


public interface UserService {

    void initializeUsersAndRoles();

    User findByUsername(String username);

    void registerAndLoginUser(UserRegisterServiceModel userRegisterServiceModel);

    boolean isUsernameFree(String username);

    void changePassword(String newPassword, String username);
}
