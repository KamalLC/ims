package kamal.ims.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kamal.ims.post.model.Post;
import kamal.ims.post.service.PostService;
import kamal.ims.util.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping(value = "/posts")
    public ResponseEntity<Map<String, Object>> getAllPosts() throws JsonProcessingException {
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
}
