package kamal.ims.user.service;

import kamal.ims.user.model.Role;
import kamal.ims.user.model.User;
import kamal.ims.user.repo.RoleRepo;
import kamal.ims.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User registerUser(User user) {
        List<Role> roles = new ArrayList<>();
        for(Role role : user.getRoles()) {
            Role existingRole = roleRepo.findByRoleName(role.getRoleName());

            if(existingRole == null) {
                throw new RuntimeException("Role not found: " + role.getRoleName());
            }
            roles.add(existingRole);
        }
        user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        System.out.println("Output: " + user);
        userRepo.save(user);
        return user;
    }


    public List<User> getAll() {
        return userRepo.findAll();
    }

    public Optional<User> getUserById(long id){
        return userRepo.findById(id);
    }




    public void validateUser(User user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    }

}
