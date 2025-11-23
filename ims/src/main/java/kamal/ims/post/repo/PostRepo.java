package kamal.ims.post.repo;

import kamal.ims.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post,Long> {

}
