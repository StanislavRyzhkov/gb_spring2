package company.ryzhkov.shop.config;

import company.ryzhkov.shop.entity.User;
import company.ryzhkov.shop.service.UserService;
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
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
            throws IOException, ServletException {
        String login = auth.getName();
        User user = userService.getUserByLogin(login);
        HttpSession session = req.getSession();
        session.setAttribute("login", user.getLogin());
        session.setAttribute("userId", user.getId());
        res.sendRedirect(req.getContextPath() + "/");
    }
}
