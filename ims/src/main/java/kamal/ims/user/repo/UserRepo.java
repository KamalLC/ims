package kamal.ims.user.repo;

import kamal.ims.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);

    @Query("SELECT DISTINCT u.email FROM users u WHERE u.email IS NOT NULL")
    List<String> findAllUniqueEmails();

}
