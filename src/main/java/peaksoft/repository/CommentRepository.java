package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.dtoComment.CommentResponse;
import peaksoft.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Modifying
    @Query("select new peaksoft.dto.dtoComment.CommentResponse(c.id,c.comment,c.createdAt) from Comment c ")
    List<CommentResponse> getAllUserComments();
}