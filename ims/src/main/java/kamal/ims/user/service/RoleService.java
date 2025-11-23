package kamal.ims.user.service;

import kamal.ims.user.model.Role;
import kamal.ims.user.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepo roleRepo;

    public void registerRole(Role role) {

        roleRepo.save(role);
    }

    public List<Role> getRoles() {
        return roleRepo.findAll();
    }

    public Role getRoleByName(String roleName) {
        return roleRepo.findByRoleName(roleName);
    }
}
