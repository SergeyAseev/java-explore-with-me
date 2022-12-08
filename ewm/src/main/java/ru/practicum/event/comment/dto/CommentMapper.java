package ru.practicum.event.comment.dto;

import ru.practicum.event.comment.model.Comment;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

public class CommentMapper {

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .created(comment.getCreated())
                .user(comment.getUser().getId())
                .event(comment.getEvent().getId())
                .text(comment.getText())
                .likesCount(comment.getLikesCount())
                .dislikesCount(comment.getDislikesCount())
                .build();
    }

    public static Comment toComment(CommentDto commentDto, User user, Event event) {
        return Comment.builder()
                .id(commentDto.getId())
                .created(commentDto.getCreated())
                .user(user)
                .event(event)
                .text(commentDto.getText())
                .likesCount(commentDto.getLikesCount())
                .dislikesCount(commentDto.getDislikesCount())
                .build();
    }
}
