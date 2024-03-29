package Impl;

import jakarta.enterprise.context.ApplicationScoped;
import model.Category;
import java.util.Arrays;
import java.util.List;


@ApplicationScoped
public class CategoryService {
    public List<Category> getAllCategories() {
        return Arrays.asList(Category.values());
    }
}