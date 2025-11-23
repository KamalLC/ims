package kamal.ims.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kamal.ims.post.model.Category;
import kamal.ims.post.model.Post;
import kamal.ims.post.service.CategoryService;
import kamal.ims.post.service.PostService;
import kamal.ims.util.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/create")
public class CreationController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    PostService postService;

    @PostMapping(value = "/post")
    public ResponseEntity<Map<String, Object>> createPost(@RequestBody Post post) throws JsonProcessingException {
        postService.createPost(post);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBuilder()
                .status(HttpStatus.CREATED)
                .message("Post Created Successfully.")
                .build()
        );
    }


    @PostMapping(value = "/category")
    public ResponseEntity<Map<String, Object>> addCategory(@RequestBody Category category) throws JsonProcessingException {
        categoryService.save(category);

        return ResponseEntity.ok().body(new ApiResponseBuilder()
                .status(HttpStatus.CREATED)
                .message("Category Created Successfully.")
                .build()
        );
    }
}
