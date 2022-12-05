package ru.practicum.compilation.service;

import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {

    /**
     * Получение подборок событий
     *
     * @param pinned закрепленные/не закрепленные события
     * @param from   количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size   количество элементов в наборе
     * @return список дто-экземпляров подборок событий
     */
    List<CompilationDto> retrieveAllCompilations(Boolean pinned, Integer from, Integer size);

    /**
     * Получение подборки событий по его ID
     *
     * @param compId ID подборки
     * @return дто-экземпляр подборки
     */
    CompilationDto retrieveDtoCompilationById(Integer compId);

    /**
     * Добавление новой подборки админом
     *
     * @param newCompilationDto дто-экземпляр создаваемой подборки
     * @return дто-экземпляр созданной подборки
     */
    CompilationDto addNewCompilation(NewCompilationDto newCompilationDto);

    /**
     * Удаление подборки админом
     *
     * @param compId ID подборки
     */
    void removeCompilationById(Integer compId);

    /**
     * Удаление собятия из подборки админов
     *
     * @param eventId ID события
     * @param compId  ID подборки
     */
    void removeEventFromCompilation(Integer compId, Long eventId);

    /**
     * Добавление события в подборку админом
     *
     * @param compId  ID подборки
     * @param eventId ID события
     * @return дто-экземпляр подборки
     */
    CompilationDto addEventInCompilation(Integer compId, Long eventId);

    /**
     * Открепить подборки на главной странице
     *
     * @param compId ID подборки
     * @return дто-экземпляр открепленной подборки
     */
    CompilationDto pinnedOutCompilation(Integer compId);

    /**
     * Закрепить полборку на главной странице
     *
     * @param compId ID подборки
     * @return дто-экземпляр закрепленной подборки
     */
    CompilationDto pinnedCompilation(Integer compId);
}
