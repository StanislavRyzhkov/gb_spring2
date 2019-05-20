package company.ryzhkov.filebox.controller;

import company.ryzhkov.filebox.entity.File;
import company.ryzhkov.filebox.entity.User;
import company.ryzhkov.filebox.service.FileService;
import company.ryzhkov.filebox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping
public class IndexController {
    private UserService userService;
    private FileService fileService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    @Secured({"ROLE_COMMON"})
    public String indexPage(Model model, Principal principal) {
        String login = principal.getName();
        User user = userService.findByLogin(login);
        model.addAttribute("files", userService.getAllFiles(user));
        model.addAttribute("dirname", user.getDirectory());
        return "index";
    }

    @PostMapping("/addFile")
    @Secured({"ROLE_COMMON"})
    public String addFile(
            @RequestParam("file")MultipartFile file,
            Model model,
            Principal principal
    ) {
        String login = principal.getName();
        User user = userService.findByLogin(login);
        boolean saved = userService.addFile(user, file);
        if (!saved) {
            model.addAttribute("error", "Ошибка сохранения файла");
            return "index";
        }
        return "redirect:/";
    }

    @PostMapping("/deleteFile")
    @Secured({"ROLE_COMMON"})
    public String deleteFile(
            @RequestParam("fileId") Long fileId,
            Model model,
            Principal principal
    ) {
        String login = principal.getName();
        User user = userService.findByLogin(login);
        File file = fileService.getById(fileId);

        if (file == null) {
            model.addAttribute("error", "Ошибка удаления");
            return "index";
        }

        // Проверяем, принадлежит ли файл данному пользоваетелю
        if (!file.getUser().equals(user)) {
            model.addAttribute("error", "У вас нет прав на удаление файла");
            return "index";
        }

        boolean deleted = fileService.delete(file, user);
        if (!deleted) {
            model.addAttribute("error", "Ошибка удаления");
        }
        return "redirect:/";
    }
}
