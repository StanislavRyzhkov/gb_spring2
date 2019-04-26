package company.ryzhkov.shop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "app_user_role")
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;
}
