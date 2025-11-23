package kamal.ims.user.controller;

import kamal.ims.user.model.Role;
import kamal.ims.user.service.RoleService;
import kamal.ims.util.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping(value="/roles")
    public ResponseEntity<Map<String, Object>> save(@RequestBody Role role) {
        roleService.registerRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.CREATED)
                        .message("Role added successfully")
                        .build()
        );

    }

    @GetMapping(value="/roles")
    public ResponseEntity<Map<String, Object>> getAll() {
        List<Role> roles = roleService.getRoles();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.OK)
                        .message("Roles fetched successfully")
                        .data(roles)
                        .build()
        );
    }

}
