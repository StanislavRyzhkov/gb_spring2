package company.ryzhkov.shop.controller;

import company.ryzhkov.shop.entity.SystemUser;
import company.ryzhkov.shop.entity.User;
import company.ryzhkov.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("registration")
public class RegistrationController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/form")
    public String registrationPage(Model model) {
        model.addAttribute("systemUser", new SystemUser());
        return "registration";
    }

    @PostMapping("/process")
    public String registrationProcess(
            @Valid @ModelAttribute("systemUser") SystemUser systemUser,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("systemUser", systemUser);
            return "registration";
        }
        User existingByLogin = userService.getUserByLogin(systemUser.getLogin());
        if (existingByLogin != null) {
            model.addAttribute("systemUser", systemUser);
            model.addAttribute("existError", "Пользователь с таким именем существует");
            return "registration";
        }
        userService.save(systemUser);
        model.addAttribute("systemUser", new SystemUser());
        model.addAttribute("success", "Успешная регистрация");
        return "registration";
    }
}
