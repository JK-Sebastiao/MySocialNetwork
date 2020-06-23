package pl.jkiakumbo.MySocialNetworking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jkiakumbo.MySocialNetworking.DTO.CommentDTO;
import pl.jkiakumbo.MySocialNetworking.model.Comment;
import pl.jkiakumbo.MySocialNetworking.model.Post;
import pl.jkiakumbo.MySocialNetworking.model.User;
import pl.jkiakumbo.MySocialNetworking.repository.CommentRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    public List<Comment> getAllComments(){
        return this.commentRepository.findAll();
    }

    public Comment getCommentById(Long id){
        return this.commentRepository.getOne(id);
    }

    @Transactional
    public Comment saveComment(Long postId, Long userId, CommentDTO commentDTO){
        Post post = postService.getPostById(postId);
        User user = userService.getUserById(userId);
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setComment(commentDTO.getComment());
        return this.commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long id, CommentDTO commentDTO){
        Comment commentToUpdate = this.getCommentById(id);
        commentToUpdate.setComment(commentDTO.getComment());
        return this.commentRepository.save(commentToUpdate);
    }

    public void deleteCommentById(Long id){
        this.commentRepository.deleteById(id);
    }

    public List<Comment> getCommentsByUserId(Long userId){
        return this.commentRepository.findCommentByUser_Id(userId);
    }

    public List<Comment> getCommentsByPostId(Long postId){
        return this.commentRepository.findCommentByPost_Id(postId);
    }
}
