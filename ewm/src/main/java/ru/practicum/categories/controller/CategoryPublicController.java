package ru.practicum.categories.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryPublicController {

    @Autowired
    private final CategoryService categoryService;

    @GetMapping(value = "/{catId}")
    public CategoryDto retrieveCategoryById(@PathVariable @NonNull Integer catId) {
        log.info("Get category with ID = {}", catId);
        return categoryService.retrieveCategoryById(catId);
    }

    @GetMapping
    public List<CategoryDto> retrieveAllCategories(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                   @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get all categories");
        return categoryService.retrieveAllCategories(from, size);
    }
}
