package kamal.ims.post.repo;

import kamal.ims.post.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
    Category findByCategoryName(String categoryName);
}
