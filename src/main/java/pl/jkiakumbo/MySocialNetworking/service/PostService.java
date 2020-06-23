package pl.jkiakumbo.MySocialNetworking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jkiakumbo.MySocialNetworking.DTO.PostDTO;
import pl.jkiakumbo.MySocialNetworking.model.Post;
import pl.jkiakumbo.MySocialNetworking.model.User;
import pl.jkiakumbo.MySocialNetworking.repository.PostRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public List<Post> getAllPosts(){
        List<Post> posts = this.postRepository.findAll();
        Collections.sort(posts);
        return posts;
    }

    public Post getPostById(Long id){
        return this.postRepository.getOne(id);
    }

    @Transactional
    public Post savePost(Long userId, PostDTO postDTO){
        Post post = new Post();
        post.setPost(postDTO.getPost());
        post.setUser(userService.getUserById(userId));
        return this.postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Long id, Post post){
        Post postToUpdate = this.getPostById(id);
        postToUpdate.setPost(post.getPost());
        return this.postRepository.save(postToUpdate);
    }

    public void deletePostById(Long id){
        this.postRepository.deleteById(id);
    }

    public List<Post> getPostsByUserIn(Long userId){
        User user = userService.getUserById(userId);
        List<Post> posts = postRepository.findPostsByUserIn(user.getFollowedUsers());
        if(posts == null)
            return new ArrayList<>();
        Collections.sort(posts);
        return posts;
    }

    public List<Post> getPostsByUserId(Long  userId){
        List<Post> posts = postRepository.findPostsByUser_Id(userId);
        Collections.sort(posts);
        return posts;
    }
}
