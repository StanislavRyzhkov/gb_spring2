package company.ryzhkov.filebox.service;

import company.ryzhkov.filebox.entity.File;
import company.ryzhkov.filebox.entity.Role;
import company.ryzhkov.filebox.entity.SystemUser;
import company.ryzhkov.filebox.entity.User;
import company.ryzhkov.filebox.repository.FileRepository;
import company.ryzhkov.filebox.repository.RoleRepository;
import company.ryzhkov.filebox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private FileRepository fileRepository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setEncoder(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Transactional
    public User findByLogin(String login) {
        return userRepository.findUserByLogin(login).orElse(null);
    }

    @Transactional
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email).orElse(null);
    }

    // Сюда можно добавить свои исключения, чтобы обработать их по-разному в контроллере
    // Пустой файл или файл с таким именем существует, например
    public boolean addFile(User user, MultipartFile file) {
        if (file.isEmpty()) return false;
        String fileName = file.getOriginalFilename();
        String userDirName = user.getDirectory();
        Path path = Paths.get("media", userDirName, fileName);
        try {
            file.transferTo(path);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        File savingToDBFile = new File();
        savingToDBFile.setName(fileName);
        savingToDBFile.setUser(user);
        savingToDBFile.setSavedAt(new Date());
        fileRepository.save(savingToDBFile);
        return true;
    }

    // На веб выводим еще инфу о размере файла, но эту инфу в БД не храним, так как она уже есть в самом файле
    // Преобразуем лист файлов с помощью функциональщины
    // Поле size в сущности File у наст помечено как @Transient
    public List<File> getAllFiles(User user) {
        return fileRepository
                .findAllByUser(user)
                .stream()
                .peek(file -> {
                    Path path = Paths.get("media", user.getDirectory(), file.getName());
                    try {
                        file.setSize(Files.size(path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean save(SystemUser systemUser) {
        User user = new User();
        if (findByLogin(systemUser.getLogin()) != null) {
            return false;
        }
        user.setLogin(systemUser.getLogin());
        user.setEmail(systemUser.getEmail());
        user.setPasswordHash(encoder.encode(systemUser.getPassword1()));
        user.setRoles(Collections.singletonList(roleRepository.findRoleByValue("ROLE_COMMON")));
        String userDirName = UUID.randomUUID().toString();
        Path path = Paths.get("media", userDirName);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        user.setDirectory(userDirName);
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(s).orElse(null);
        if (user == null) throw new UsernameNotFoundException("Неправильное имя пользователя или пароль!");
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPasswordHash(),
                mapRolesToAuthorities(user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getValue()))
                .collect(Collectors.toList());
    }
}
