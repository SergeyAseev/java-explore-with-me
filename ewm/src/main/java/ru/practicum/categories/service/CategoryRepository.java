package ru.practicum.categories.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.categories.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    //Ищем категорию по названию
    Category findByName(String name);
}
