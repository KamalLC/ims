package kamal.ims.post.service;

import jakarta.transaction.Transactional;
import kamal.ims.post.model.Category;
import kamal.ims.post.model.Comment;
import kamal.ims.post.model.Post;
import kamal.ims.post.repo.CategoryRepo;
import kamal.ims.post.repo.PostRepo;
import kamal.ims.user.model.User;
import kamal.ims.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private UserRepo userRepo;

    public Post createPost(Post post) {
        Category existingCategory = categoryRepo.findByCategoryName(post.getCategory().getCategoryName());
        User existingUser =userRepo.findByUsername(post.getUser().getUsername());
        if(existingCategory == null){
            throw new RuntimeException("Category not found: " + post.getCategory().getCategoryName());
        }
        if(existingUser == null){
            throw new RuntimeException("User not found: " + post.getUser().getUsername());
        }
        post.setCategory(existingCategory);
        post.setUser(existingUser);
        postRepo.save(post);
        return post;
    }

    public List<Post> getAllPosts(){
        return postRepo.findAll(Sort.by(Sort.Direction.DESC, "updatedDate"));
    }


//    public List<Post> getPostsSortedByCreatedDateDesc() {
//        return postRepo.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
//    }

    public void deletePost(long id){
        postRepo.deleteById(id);
    }


    @Transactional
    public Post approvePost(long postId) {
        Post c = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found: " + postId));

//        if (c.getUser() == null || c.getUser().getRoles().contains("ADMIN")) {
//            throw new RuntimeException("You are not allowed to edit this comment");
//        }

        c.setStatus("ACTIVE");
        c.setUpdatedDate(LocalDateTime.now());
        return postRepo.save(c);
    }


    public List<Post> getPostsByUserId(Long userId) {
        return postRepo.findByUserId(userId, Sort.by(Sort.Direction.DESC, "createdDate"));
    }

}
