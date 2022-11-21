package ru.practicum.categories.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.service.CategoryService;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class CategoryAdminController {

    @Autowired
    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Create category = {}", categoryDto);
        return categoryService.createCategory(categoryDto);
    }

    @PatchMapping
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Update category {}", categoryDto);
        return categoryService.updateCategory(categoryDto);
    }

    @DeleteMapping(value = "/{catId}")
    public void removeCategory(@PathVariable @NonNull Integer catId) {
        log.info("Remove category with ID = {}", catId);
        categoryService.removeCategory(catId);
    }
}
