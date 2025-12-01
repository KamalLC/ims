package kamal.ims.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kamal.ims.post.dto.comment.CommentResponse;
import kamal.ims.post.model.Comment;
import kamal.ims.post.service.CommentService;
import kamal.ims.util.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/posts/{postId}/comments")
    public ResponseEntity<Map<String, Object>> getCommentsForPost(
            @PathVariable int postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdDate,asc") String sort,
            @RequestParam(defaultValue = "true") boolean includeReplies
    ) throws JsonProcessingException {

        Sort sortObj = parseSort(sort);
        List<Comment> respPage = commentService.getTopLevelCommentsForPost(postId, page, size, sortObj, includeReplies);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(respPage)
                .build()
        );
    }

//    @GetMapping(value = "/posts/{postId}/comments")
//    public ResponseEntity<Map<String, Object>> getCommentsForPost(
//            @PathVariable int postId,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size,
//            @RequestParam(defaultValue = "createdDate,asc") String sort,
//            @RequestParam(defaultValue = "true") boolean includeReplies
//    ) throws JsonProcessingException {
//
//        Sort sortObj = parseSort(sort);
//        Page<CommentResponse> respPage = commentService.getTopLevelCommentsForPost(postId, page, size, sortObj, includeReplies);
//
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBuilder()
//                .status(HttpStatus.OK)
//                .message("Success")
//                .data(respPage)
//                .build()
//        );
//    }

    @GetMapping(value = "/comments/{commentId}")
    public ResponseEntity<Map<String, Object>> getCommentThread(@PathVariable int commentId) throws JsonProcessingException {
        CommentResponse response = commentService.getCommentThread(commentId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(response)
                .build()
        );
    }

    private Sort parseSort(String sortParam) {
        // format: "field,direction"
        try {
            String[] parts = sortParam.split(",");
            String field = parts[0];
            Sort.Direction dir = (parts.length > 1 && "desc".equalsIgnoreCase(parts[1]))
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;
            return Sort.by(dir, field);
        } catch (Exception e) {
            return Sort.by(Sort.Direction.ASC, "createdDate");
        }
    }
}
