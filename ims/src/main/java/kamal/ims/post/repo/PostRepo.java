package kamal.ims.post.repo;

import kamal.ims.post.model.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Long> {
    List<Post> findByUserId(Long userId, Sort sort);

}
