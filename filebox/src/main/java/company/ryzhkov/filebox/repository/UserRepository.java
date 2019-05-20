package company.ryzhkov.filebox.repository;

import company.ryzhkov.filebox.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByLogin(String login);

    Optional<User> findUserByEmail(String email);
}
