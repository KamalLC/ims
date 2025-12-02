package kamal.ims.user.controller;

import kamal.ims.user.model.Role;
import kamal.ims.user.model.User;
import kamal.ims.user.service.RoleService;
import kamal.ims.user.service.UserService;
import kamal.ims.util.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        List<User> users = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Users fetched successfully")
                .data(users)
                .build()
        );
    }


    @GetMapping(value = "/user/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable("id") Long id) {
        Optional<User> userOptional = userService.getUserById(id);

        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBuilder()
                    .status(HttpStatus.OK)
                    .message("User fetched successfully")
                    .data(userOptional.get())
                    .build()
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseBuilder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("User not found")
                    .build()
            );
        }
    }

    }
