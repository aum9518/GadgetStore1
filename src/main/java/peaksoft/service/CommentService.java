package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoComment.CommentRequest;
import peaksoft.dto.dtoComment.CommentResponse;

import java.util.List;

public interface CommentService {
    List<CommentResponse> getAllComments();
    SimpleResponse saveComment(CommentRequest commentRequest, Long userId, Long productId);
    SimpleResponse deleteCommentById(Long id);
    SimpleResponse updateComment(Long id,CommentRequest commentRequest);
}
