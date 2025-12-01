package kamal.ims.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kamal.ims.post.dto.comment.CommentUpdateRequest;
import kamal.ims.post.model.Comment;
import kamal.ims.post.model.Post;
import kamal.ims.post.service.PostService;
import kamal.ims.user.model.User;
import kamal.ims.user.service.UserService;
import kamal.ims.util.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Validated
    @GetMapping(value = "/posts")
    public ResponseEntity<Map<String, Object>> getAllPosts() throws JsonProcessingException {

//        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");


        List<Post> posts = postService.getAllPosts();

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .data(posts)
                .message("Success")
                .build()
        );
    }

    @GetMapping(value = "open_posts")
    public ResponseEntity<Map<String, Object>> getOpenPosts() throws JsonProcessingException {
        List<Post> posts = new ArrayList<>();

        for (Post post : postService.getAllPosts()) {
            if (post.getStatus().equals("ACTIVE")) {
                posts.add(post);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBuilder()
                .message("success")
                .status(HttpStatus.OK)
                .data(posts)
                .build()
        );
    }

    @GetMapping(value = "posts/user/{id}")
    public ResponseEntity<Map<String, Object>> getPostByUserId(@PathVariable("id") Long id) throws JsonProcessingException {
        List<Post> posts = postService.getPostsByUserId(id);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBuilder()
                .message("success")
                .status(HttpStatus.OK)
                .data(posts)
                .build()
        );
    }

    @GetMapping(value = "pending_posts")
    public ResponseEntity<Map<String, Object>> getPendingPosts() throws JsonProcessingException {
        List<Post> posts = new ArrayList<>();

        for (Post post : postService.getAllPosts()) {
            if (post.getStatus().equals("PENDING")) {
                posts.add(post);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBuilder()
                .message("success")
                .status(HttpStatus.OK)
                .data(posts)
                .build()
        );
    }
    @DeleteMapping(value = "post/delete/{id}")
    public ResponseEntity<Map<String, Object>> deletePost(@PathVariable("id") Long id) throws JsonProcessingException{
        for(Post post : postService.getAllPosts()){
            if(post.getId() == id){
                postService.deletePost(id);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBuilder()
                .message("Post Deleted")
                .status(HttpStatus.OK)
                .build()
        );
    }

    @PatchMapping(value = "/post/{postId}")
    public ResponseEntity<Map<String, Object>> approvePost(
            @PathVariable int postId
    ) throws JsonProcessingException {

        Post updated = postService.approvePost(postId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Post Approved Successfully.")
                .data(updated)
                .build()
        );
    }
}
