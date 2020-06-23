package pl.jkiakumbo.MySocialNetworking.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jkiakumbo.MySocialNetworking.DTO.PostDTO;
import pl.jkiakumbo.MySocialNetworking.model.Post;
import pl.jkiakumbo.MySocialNetworking.service.PostService;
import pl.jkiakumbo.MySocialNetworking.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostResource {
    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        return new ResponseEntity<>(this.postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getUserById(@PathVariable Long id){
        return new ResponseEntity<>(this.postService.getPostById(id),HttpStatus.OK);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Post> savePost(@PathVariable Long userId, @RequestBody @Valid PostDTO postDTO){
        return new ResponseEntity<>(this.postService.savePost(userId,postDTO),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post){
        return new ResponseEntity<>(this.postService.updatePost(id,post), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePostById(@PathVariable Long id){
        this.postService.deletePostById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/user/{userId}/followed")
    public ResponseEntity<List<Post>> getFollowedUsersPosts(@PathVariable Long userId){

        return new ResponseEntity<>(this.postService.getPostsByUserIn(userId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getUserPosts(@PathVariable Long userId){
        return new ResponseEntity<>(this.postService.getPostsByUserId(userId), HttpStatus.OK);
    }

}
