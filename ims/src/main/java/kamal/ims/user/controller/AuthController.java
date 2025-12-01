
package kamal.ims.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kamal.ims.security.JwtService;
import kamal.ims.user.model.User;
import kamal.ims.user.service.UserService;
import kamal.ims.util.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user) throws JsonProcessingException {
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.CREATED)
                        .message("User registered successfully")
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) throws JsonProcessingException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        // Build token & response
        String accessToken = jwtService.generateAccessToken((org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal());
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .distinct()
                .collect(Collectors.toList());

        Map<String, Object> data = Map.of(
                "accessToken", accessToken,
                "tokenType", "Bearer",
                "expiresIn", 15 * 60, // seconds
                "roles", roles
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.OK)
                        .message("User logged in successfully")
                        .data(data)
                        .build()
        );
    }
}
