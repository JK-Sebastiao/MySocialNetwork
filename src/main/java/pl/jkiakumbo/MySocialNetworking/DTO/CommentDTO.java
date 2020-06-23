package pl.jkiakumbo.MySocialNetworking.DTO;

import javax.validation.constraints.NotBlank;

public class CommentDTO {
    @NotBlank
    private String comment;

    public CommentDTO(String comment){
        this.comment = comment;
    }

    public CommentDTO() {}

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
