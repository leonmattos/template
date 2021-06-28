package br.com.exemplos.users.repository;

import br.com.exemplos.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "from User where name = ?1")
    List<User> findByName(String name);
}
