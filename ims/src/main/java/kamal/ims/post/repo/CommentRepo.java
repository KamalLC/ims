package kamal.ims.post.repo;

import kamal.ims.post.model.Comment;
import kamal.ims.post.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

    // Top-level comments for a post (parent is null)
    Page<Comment> findByPostAndParentIsNull(Post post, Pageable pageable);

    // Replies to a parent comment
    Page<Comment> findByParent(Comment parent, Pageable pageable);

    // Count replies for a parent
    long countByParent(Comment parent);

    List<Comment> findByPost(Post post);
}
