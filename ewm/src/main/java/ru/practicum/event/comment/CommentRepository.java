package ru.practicum.event.comment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> getCommentsByEvent_IdOrderByCreatedDesc(Long eventId, Pageable pageable);
}
