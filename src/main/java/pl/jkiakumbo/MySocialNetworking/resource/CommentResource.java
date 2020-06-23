package pl.jkiakumbo.MySocialNetworking.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jkiakumbo.MySocialNetworking.DTO.CommentDTO;
import pl.jkiakumbo.MySocialNetworking.model.Comment;
import pl.jkiakumbo.MySocialNetworking.service.CommentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentResource {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments(){
        return new ResponseEntity<>(this.commentService.getAllComments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getUserById(@PathVariable Long id){
        return new ResponseEntity<>(this.commentService.getCommentById(id), HttpStatus.OK);
    }

    @PostMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<Comment> saveComment(@PathVariable Long postId, @PathVariable Long userId, @RequestBody @Valid CommentDTO commentDTO){
        return new ResponseEntity<>(this.commentService.saveComment(postId,userId,commentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody @Valid CommentDTO commentDTO){
        return new ResponseEntity<>(this.commentService.updateComment(id,commentDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCommentById(@PathVariable Long id){
        this.commentService.deleteCommentById(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> getCommentsByUserId(@PathVariable Long userId){
        return new ResponseEntity<>(this.commentService.getCommentsByUserId(userId),HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable Long postId){
        return new ResponseEntity<>(this.commentService.getCommentsByPostId(postId),HttpStatus.OK);
    }

}
