package ru.practicum.event.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.comment.model.CommentUserLink;

public interface CommentLinkRepository extends JpaRepository<CommentUserLink, Long> {

    CommentUserLink getByComment_IdAndUser_Id(Long commentId, Long userId);
}
