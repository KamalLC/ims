package kamal.ims.post.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import kamal.ims.post.model.Category;
import kamal.ims.post.service.CategoryService;
import kamal.ims.util.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping(value = "/categories")
    public ResponseEntity<Map<String, Object>> getAllCategories() throws JsonProcessingException {
        List<Category> categories = categoryService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(categories)
                .build()
        );
    }
}
