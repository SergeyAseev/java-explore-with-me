package ru.practicum.categories.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.CategoryMapper;
import ru.practicum.categories.model.Category;
import ru.practicum.exception.ExistsElementException;
import ru.practicum.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        verifyCategoryByName(categoryDto.getName());

        Category category = CategoryMapper.toCategory(categoryDto);
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {

        retrieveCategoryById(categoryDto.getId());
        verifyCategoryByName(categoryDto.getName());

        Category oldCategory = CategoryMapper.toCategory(categoryDto);
        oldCategory.setName(categoryDto.getName());
        return CategoryMapper.toCategoryDto(categoryRepository.save(oldCategory));
    }

    @Override
    public void removeCategory(Integer catId) {

        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with ID %s not found", catId)));
        if (!category.getEvents().isEmpty()) {
            throw new ExistsElementException(String.format("Category %s contains events and can't be removed", catId));
        } else {
            categoryRepository.deleteById(catId);
        }
    }

    @Override
    public List<CategoryDto> retrieveAllCategories(int from, int size) {

        Pageable pageable = PageRequest.of(from / size, size);
        return categoryRepository.findAll(pageable)
                .stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    public CategoryDto retrieveCategoryById(Integer catId) {

        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with ID %s not found", catId)));
        return CategoryMapper.toCategoryDto(category);
    }

    private void verifyCategoryByName(String catName) {
        if (categoryRepository.findByName(catName) != null) {
            throw new ExistsElementException("Category with this name already exist");
        }
    }
}
