package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoComment.CommentRequest;

public interface CommentService {
    SimpleResponse saveComment(CommentRequest commentRequest);
    SimpleResponse deleteCommentById(Long id);
}
