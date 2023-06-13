package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoComment.CommentRequest;
import peaksoft.dto.dtoComment.CommentResponse;
import peaksoft.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentAPI {
  private final CommentService commentService;

  @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
  @GetMapping
  public List<CommentResponse> getAllUserComments(){
    return commentService.getAllComments();
  }

  @PreAuthorize("hasAuthority('USER')")
  @PostMapping
  public SimpleResponse saveComment(@RequestBody CommentRequest commentRequest, @RequestParam Long userId,@RequestParam Long productId){
      return commentService.saveComment(commentRequest,userId,productId);
  }
  @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
  @DeleteMapping("{id}")
  public SimpleResponse deleteComment(@PathVariable Long id){
    return commentService.deleteCommentById(id);
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
  @PutMapping("{id}")
  public SimpleResponse updateComment(@PathVariable Long id,@RequestBody CommentRequest commentRequest){
    return commentService.updateComment(id, commentRequest);
  }
}
