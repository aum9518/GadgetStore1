package peaksoft.dto.dtoComment;

import lombok.Builder;

import java.time.ZonedDateTime;
@Builder
public record CommentResponse(Long id,
                              String comment,
                              ZonedDateTime createAt) {
    public CommentResponse {
    }
}
