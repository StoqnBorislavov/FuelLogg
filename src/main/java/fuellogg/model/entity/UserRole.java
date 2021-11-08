package fuellogg.model.entity;

import fuellogg.model.enums.UserRoleEnum;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class UserRole extends BaseEntity {

    private UserRoleEnum role;

    public UserRole() {
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public UserRoleEnum getRole() {
        return role;
    }

    public UserRole setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }
}
