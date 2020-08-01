package india.lingayat.we.repositories;

import com.querydsl.core.types.Predicate;
import india.lingayat.we.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    User findByEmail(String email);

    @Override
//    @Cacheable(value="users")
    Page<User> findAll(Predicate var1, Pageable var2);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    <T> Optional<User> findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail1);

}
