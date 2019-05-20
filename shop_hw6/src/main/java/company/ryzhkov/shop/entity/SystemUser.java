package company.ryzhkov.shop.entity;

import company.ryzhkov.shop.anno.FieldMatch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@FieldMatch(first = "password1", second = "password2", message = "Пароли не совпадают")
public class SystemUser {

    @NotNull(message = "Введите имя пользователя")
    @Size(min = 5, max = 255, message = "Некорректное имя пользователя")
    private String login;

    @NotNull(message = "Введите email")
    @Size(min = 5, max = 255, message = "Некорректный email")
    @Email(message = "Некорректный email")
    private String email;

    @NotNull(message = "Ввведите пароль")
    @Size(message = "Некорректный пароль")
    private String password1;

    @NotNull(message = "Введите пароль еще раз")
    @Size(message = "Некорректный повторный пароль")
    private String password2;
}
