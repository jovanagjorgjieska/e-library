package mk.ukim.finki.elibrary.service;

import mk.ukim.finki.elibrary.model.Category;

import java.util.List;

public interface CategoryService {
    Category create(String name);

    void delete(String name);

    List<Category> findAll();

    List<Category> searchCategories(String searchText);
}
