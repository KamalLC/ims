package kamal.ims.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kamal.ims.user.model.User;
import kamal.ims.user.service.UserService;
import kamal.ims.util.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping(value = "/auth")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping(value  ="/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user) throws JsonProcessingException {
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.CREATED)
                        .message("User registered successfully")
                        .build()
        );
    }

    @PostMapping(value="/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) throws JsonProcessingException {
        userService.validateUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.OK)
                        .message("User logged in successfully")
                        .build()
        );
    }
}
