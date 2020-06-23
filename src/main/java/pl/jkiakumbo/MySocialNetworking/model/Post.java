package pl.jkiakumbo.MySocialNetworking.model;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Post implements Serializable, Comparable<Post>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Post message is required")
    private String post;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    private User user;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "post", orphanRemoval = true)
    @JsonIgnore
    private Set<Comment> comments;

    private Date postedDate;

    public Post(){}

    public Post(String post, User user) {
        this.post = post;
        this.user = user;
    }

    public Post(String post) {
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }


    @PrePersist
    protected void onPost(){
        this.postedDate = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (!id.equals(post.id)) return false;
        return postedDate.equals(post.postedDate);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + postedDate.hashCode();
        return result;
    }

    @Override
    public int compareTo(Post post) {
        int comparisonResult = this.postedDate.compareTo(post.postedDate);
        if(comparisonResult != 0)
            return -comparisonResult;
        return comparisonResult;
    }
}
