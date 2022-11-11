package ru.practicum.categories.service;

import ru.practicum.categories.dto.CategoryDto;

import java.util.List;

public interface CategoryService {


    /**
     *
     * @param categoryDto
     * @return
     */
    CategoryDto createCategory (CategoryDto categoryDto);

    /**
     *
     * @param categoryDto
     * @return
     */
    CategoryDto updateCategory (CategoryDto categoryDto);

    /**
     *
     * @param catId
     */
    void removeCategory (Integer catId);

    /**
     *
     * @param catId
     * @return
     */
    CategoryDto retrieveCategoryById (Integer catId);

    /**
     *
     * @param from
     * @param size
     * @return
     */
    List<CategoryDto> retrieveAllCategories(int from, int size);
}
