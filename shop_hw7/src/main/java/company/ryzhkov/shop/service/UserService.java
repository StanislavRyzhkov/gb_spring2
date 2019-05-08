package company.ryzhkov.shop.service;

import company.ryzhkov.shop.entity.Role;
import company.ryzhkov.shop.entity.SystemUser;
import company.ryzhkov.shop.entity.User;
import company.ryzhkov.shop.repository.RoleRepository;
import company.ryzhkov.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public boolean save(SystemUser systemUser) {
        User user = new User();
        if (getUserByLogin(systemUser.getLogin()) != null) {
            return false;
        }
        user.setLogin(systemUser.getLogin());
        user.setEmail(systemUser.getEmail());
        user.setPasswordHash(passwordEncoder.encode(systemUser.getPassword1()));
        user.setRoles(Collections.singletonList(roleRepository.findRoleByName("ROLE_COMMON")));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public User getUserByLogin(String login) {
        return userRepository.findUserByLogin(login).orElse(null);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(s).orElse(null);
        if (user == null) throw new UsernameNotFoundException("Неправильное имя или пароль!");
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPasswordHash(),
                mapRolesToAuthorities(user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
