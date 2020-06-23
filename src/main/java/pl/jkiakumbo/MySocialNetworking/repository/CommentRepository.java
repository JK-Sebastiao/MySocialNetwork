package pl.jkiakumbo.MySocialNetworking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jkiakumbo.MySocialNetworking.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentByUser_Id(Long userId);
    List<Comment> findCommentByPost_Id(Long userId);

}
