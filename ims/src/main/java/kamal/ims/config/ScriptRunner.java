package kamal.ims.config;

import kamal.ims.post.model.Category;
import kamal.ims.post.service.CategoryService;
import kamal.ims.user.model.Role;
import kamal.ims.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ScriptRunner implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void run(String... args) throws Exception {
        List<Role> roles = Arrays.asList(new Role("ADMIN"), new Role("USER"));
        for(Role role : roles){
            Role existedRole = roleService.getRoleByName(role.getRoleName());
            if(existedRole == null){
                roleService.registerRole(role);
            }
        }

        List<Category> categories = Arrays.asList(new Category("ISSUE"),
                new Category("COMPLAINT"),
                new Category("POST"));
        for(Category category : categories){
            Category existedCategory = categoryService.getCategoryByName(category.getCategoryName());
            if(existedCategory == null){
                categoryService.save(category);
            }
        }
    }
}
