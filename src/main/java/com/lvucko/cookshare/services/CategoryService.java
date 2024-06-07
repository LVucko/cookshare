package com.lvucko.cookshare.services;

import com.lvucko.cookshare.dao.CategoryDao;
import com.lvucko.cookshare.models.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryDao categoryDao;
    public List<Category> getCategories(){
        return categoryDao.getAllCategories();
    }
    public void addCategory(String categoryName){

        categoryDao.addNewCategory(categoryName);
    }
    public void removeCategory(Category category){
        categoryDao.deleteCategory(category);
    }
}
