package ru.practicum.categories.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.CategoryMapper;
import ru.practicum.categories.model.Category;
import ru.practicum.exception.ErrorHandler;
import ru.practicum.exception.ExistsElementException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        if (categoryRepository.findByName(categoryDto.getName()) != null) {
            throw new ExistsElementException("Category already exist");
        }
        Category category = CategoryMapper.toCategory(categoryDto);
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {

        getCategoryById(categoryDto.getId());

        if (categoryRepository.findByName(categoryDto.getName()) != null) {
            throw new ExistsElementException("Category with this name already exist");
        }

        Category oldCategory = CategoryMapper.toCategory(categoryDto);
        oldCategory.setName(categoryDto.getName());
        categoryRepository.save(oldCategory);
        return CategoryMapper.toCategoryDto(oldCategory);
    }

    @Override
    public void removeCategory(Integer catId) {

        getCategoryById(catId);
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with ID %s not found", catId)));
        if (!category.getEvents().isEmpty()) {
            throw new ExistsElementException(String.format("Category %s contains events and can't be removed", catId));
        } else {
            categoryRepository.deleteById(catId);
        }
    }

    @Override
    public CategoryDto retrieveCategoryById(Integer catId) {
        return getCategoryById(catId);
    }

    @Override
    public List<CategoryDto> retrieveAllCategories(int from, int size) {

        Pageable pageable = PageRequest.of(from / size, size);
        return categoryRepository.findAll(pageable)
                .stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    private CategoryDto getCategoryById(Integer catId) {

        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with ID %s not found", catId)));
        return CategoryMapper.toCategoryDto(category);
    }
}
