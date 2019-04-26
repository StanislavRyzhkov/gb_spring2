package company.ryzhkov.shop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
class UserRoleId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name_id")
    private Long roleId;
}
