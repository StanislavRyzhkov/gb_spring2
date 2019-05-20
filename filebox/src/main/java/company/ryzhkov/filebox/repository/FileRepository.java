package company.ryzhkov.filebox.repository;

import company.ryzhkov.filebox.entity.File;
import company.ryzhkov.filebox.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends CrudRepository<File, Long> {

    List<File> findAllByUser(User user);

    @Override
    <S extends File> S save(S s);

    @Override
    Optional<File> findById(Long aLong);

    @Override
    void delete(File file);
}
