package kamal.ims.post.service;

import kamal.ims.post.model.Category;
import kamal.ims.post.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public void save(Category category){
        categoryRepo.save(category);
    }
    public Category getCategoryByName(String categoryName){
        return categoryRepo.findByCategoryName(categoryName);
    }

    public List<Category> getAll(){
        return categoryRepo.findAll();
    }
}
