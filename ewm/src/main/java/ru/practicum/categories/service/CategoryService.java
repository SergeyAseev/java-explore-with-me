package ru.practicum.categories.service;

import ru.practicum.categories.dto.CategoryDto;

import java.util.List;

public interface CategoryService {


    /**
     * Добавление новой категории
     *
     * @param categoryDto экземпляр сущности для создания
     * @return дто-экземпляр новой категории
     */
    CategoryDto createCategory(CategoryDto categoryDto);

    /**
     * Изменение категории
     *
     * @param categoryDto экземпляр сущности для изменения
     * @return дто-экземпляр измененной категории
     */
    CategoryDto updateCategory(CategoryDto categoryDto);

    /**
     * Удаление категории
     *
     * @param catId ID удаляемой категории
     */
    void removeCategory(Integer catId);

    /**
     * Получение информации о категории по ее ID
     *
     * @param catId ID запрашиваемой категории
     * @return дто-экземпляр категории
     */
    CategoryDto retrieveCategoryById(Integer catId);

    /**
     * Получение категорий
     *
     * @param from кол-во категорий, которые нужно пропустить для формирования текущего набора
     * @param size кол-во категорий в наборе
     * @return Список дто-экземпляров категорий
     */
    List<CategoryDto> retrieveAllCategories(int from, int size);
}
