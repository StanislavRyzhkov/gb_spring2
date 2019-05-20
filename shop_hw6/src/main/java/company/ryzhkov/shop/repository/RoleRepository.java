package company.ryzhkov.shop.repository;

import company.ryzhkov.shop.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findRoleByName(String name);
}
