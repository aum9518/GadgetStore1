package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoComment.CommentRequest;
import peaksoft.repository.CommentRepository;
import peaksoft.service.CommentService;
@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
    @Override
    public SimpleResponse saveComment(CommentRequest commentRequest) {
        return null;
    }

    @Override
    public SimpleResponse deleteCommentById(Long id) {
        return null;
    }
}
