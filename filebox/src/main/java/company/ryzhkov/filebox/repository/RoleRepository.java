package company.ryzhkov.filebox.repository;

import company.ryzhkov.filebox.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findRoleByValue(String value);
}
