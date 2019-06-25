package company.ryzhkov.filebox.entity;

import company.ryzhkov.filebox.anno.FieldMatch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@FieldMatch(first = "password1", second = "password2", message = "Пароли не совпадают")
public class SystemUser {

    @Size(min = 1, max = 200, message = "Некорретная длина")
    private String login;

    @Email(message = "Некорректный email")
    @Size(min = 1, max = 200, message = "Некорретная длина")
    private String email;

    @Size(min = 1, max = 200, message = "Некорретная длина")
    private String password1;

    @Size(min = 1, max = 200, message = "Некорретная длина")
    private String password2;
}
