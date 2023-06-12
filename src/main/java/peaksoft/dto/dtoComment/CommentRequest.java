package peaksoft.dto.dtoComment;

import lombok.Builder;

@Builder
public record CommentRequest(String comment) {
    public CommentRequest {
    }
}
