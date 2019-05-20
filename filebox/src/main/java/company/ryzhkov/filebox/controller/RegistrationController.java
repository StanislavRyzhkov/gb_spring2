package company.ryzhkov.filebox.controller;

import company.ryzhkov.filebox.entity.SystemUser;
import company.ryzhkov.filebox.entity.User;
import company.ryzhkov.filebox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String registrationPage(
            Model model,
            @RequestParam(name = "result", required = false) String result
    ) {

        model.addAttribute("systemUser", new SystemUser());
        if (result != null && result.equals("success")) {
            model.addAttribute("success", "Успешная регистрация");
            return "registration";
        }
        return "registration";
    }

    @PostMapping("/process")
    public String registrationProcess(
            @Valid @ModelAttribute("systemUser") SystemUser systemUser,
            BindingResult result,
            Model model
    ) {
        String login = systemUser.getLogin();
        String email = systemUser.getEmail();

        if (result.hasErrors()) {
            model.addAttribute("systemUser", systemUser);
            model.addAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
            return "registration";
        }

        User existingByLogin = userService.findByLogin(login);
        if (existingByLogin != null) {
            model.addAttribute("systemUser", systemUser);
            model.addAttribute("error", "Пользователь с таким логином существует");
            return "registration";
        }

        User existingByEmail = userService.findByEmail(email);
        if (existingByEmail != null) {
            model.addAttribute("systemUser", systemUser);
            model.addAttribute("error", "Пользователь с таким email существует");
            return "registration";
        }

        boolean saved = userService.save(systemUser);

        if (!saved) {
            model.addAttribute("systemUser", systemUser);
            model.addAttribute("error", "Ошибка");
            return "registration";
        }

        return "redirect:/registration/form?result=success";
    }
}
