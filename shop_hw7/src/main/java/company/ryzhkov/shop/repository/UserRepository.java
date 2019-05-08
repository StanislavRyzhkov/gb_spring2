package company.ryzhkov.shop.repository;

import company.ryzhkov.shop.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByLogin(String login);

    @Override
    <S extends User> S save(S s);
}
