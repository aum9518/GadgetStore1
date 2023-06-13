package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoComment.CommentRequest;
import peaksoft.dto.dtoComment.CommentResponse;
import peaksoft.entity.Comment;
import peaksoft.entity.Product;
import peaksoft.entity.User;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.CommentRepository;
import peaksoft.repository.ProductRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.CommentService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private User getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> new NotFoundException("User with email:" + email + " is not found!"));
        return user;
    }

    @Override
    public List<CommentResponse> getAllComments() {
//        List<User> all = userRepository.findAll();
//        User user = getAuthentication();
//        Long id = user.getId();
//        List<CommentResponse> commentResponses = new ArrayList<>();
//        for (Comment c:user.getComments()) {
//            commentResponses.add( CommentResponse.builder()
//                    .comment(c.getComment())
//                    .id(c.getId())
//                    .createAt(c.getCreatedAt())
//                    .build());
//        }

                return repository.getAllUserComments();
    }

    @Override
    public SimpleResponse saveComment(CommentRequest commentRequest, Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id: " + userId + "is not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product with id:" + productId + " is not found!"));
        List<Product> products = new ArrayList<>();
        products.add(product);
        Comment comment = new Comment();
        comment.setComment(commentRequest.comment());
        comment.setCreatedAt(ZonedDateTime.now());
        comment.setUser(user);
        comment.setProduct(products);
        repository.save(comment);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("The comment successfully saved")
                .build();
    }

    @Override
    public SimpleResponse deleteCommentById(Long id) {
        Comment comment = repository.findById(id).orElseThrow(() -> new NotFoundException("Comment is not found"));
        if (repository.existsById(id)) {
            comment.getUser().getComments().remove(comment);
            for (Product p : comment.getProduct()) {
                if (p.getComment().equals(comment)) {
                    p.setComment(null);
                }
            }
            repository.deleteById(id);
        }
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully deleted")
                .build();
    }

    @Override
    public SimpleResponse updateComment(Long id, CommentRequest commentRequest) {
        Comment comment = repository.findById(id).orElseThrow(() -> new NotFoundException("Comment is not found"));
        User user = getAuthentication();
            if (comment.getUser().equals(user)){
                comment.setComment(commentRequest.comment());
                repository.save(comment);
                return SimpleResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Successfully updated")
                        .build();
            }else throw new NullPointerException("Comment is empty");

    }
}
