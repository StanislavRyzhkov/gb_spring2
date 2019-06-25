package company.ryzhkov.filebox.config;


import company.ryzhkov.filebox.entity.User;
import company.ryzhkov.filebox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException, ServletException {
        String login = auth.getName();
        User theUser = userService.findByLogin(login);
        HttpSession session = req.getSession();
        session.setAttribute("login", theUser.getLogin());
        session.setAttribute("email", theUser.getEmail());
        res.sendRedirect(req.getContextPath() + "/");
    }
}
