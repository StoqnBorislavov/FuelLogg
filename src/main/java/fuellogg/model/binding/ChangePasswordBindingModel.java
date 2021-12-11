package fuellogg.model.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChangePasswordBindingModel {

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

    public ChangePasswordBindingModel() {
    }

    @NotNull
    @Size(min = 4, max = 20)
    public String getCurrentPassword() {
        return currentPassword;
    }

    public ChangePasswordBindingModel setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
        return this;
    }

    @NotNull
    @Size(min = 4, max = 20)
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @NotNull
    @Size(min = 4, max = 20)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
