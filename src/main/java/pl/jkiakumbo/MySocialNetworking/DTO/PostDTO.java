package pl.jkiakumbo.MySocialNetworking.DTO;

import javax.validation.constraints.NotBlank;

public class PostDTO {
    @NotBlank
    private String post;

    public PostDTO(String post) {
        this.post = post;
    }

    public PostDTO() {}

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
