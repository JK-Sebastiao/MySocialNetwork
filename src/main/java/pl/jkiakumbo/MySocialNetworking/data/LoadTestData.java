package pl.jkiakumbo.MySocialNetworking.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.jkiakumbo.MySocialNetworking.DTO.CommentDTO;
import pl.jkiakumbo.MySocialNetworking.DTO.PostDTO;
import pl.jkiakumbo.MySocialNetworking.DTO.UserDTO;
import pl.jkiakumbo.MySocialNetworking.service.CommentService;
import pl.jkiakumbo.MySocialNetworking.service.PostService;
import pl.jkiakumbo.MySocialNetworking.service.UserService;


@Component
public class LoadTestData implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Override
    public void run(String... args) throws Exception {
        this.loadData();
    }

    public void loadData(){
        UserDTO user1 = new UserDTO("John","Doe", "john.doe@gmail.com","12345678","12345678");
        userService.saveUser(user1);
        UserDTO user2 = new UserDTO("Jane","Doe", "jane.doe@gmail.com","12345678","12345678");
        userService.saveUser(user2);
        UserDTO user3 = new UserDTO("Bob","Andrei", "bob.andrei@yahoo.com","12345678","12345678");
        userService.saveUser(user3);
        userService.addFollowedUser(1L,3L);
        userService.addFollowedUser(2L,1L);
        userService.addFollowedUser(3L,2L);

        PostDTO post1 = new PostDTO("Morbi condimentum urna ligula, vitae auctor risus ornare at. Phasellus vel metus faucibus, malesuada ex sed, fringilla elit. Phasellus eget.");
        postService.savePost(1L,post1);

        PostDTO post2 = new PostDTO("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam euismod condimentum nibh, non gravida elit dignissim eget. Praesent vel nec.");
        postService.savePost(3L,post2);

        PostDTO post3 = new PostDTO("Suspendisse ac pulvinar neque. Aenean sagittis enim et velit laoreet elementum. Aliquam finibus mi quis nunc dignissim faucibus. Etiam biam.");
        postService.savePost(2L,post3);

        CommentDTO comment1 = new CommentDTO("Morbi condimentum urna ligula, vitae auctor risus ornare at. Phasellus vel metus faucibus, malesuada ex sed, fringilla elit. Phasellus eget.");
        commentService.saveComment(1L,1L,comment1);

        CommentDTO comment2 = new CommentDTO("Phasellus vel metus faucibus, malesuada ex sed, fringilla elit. Phasellus eget.");
        commentService.saveComment(1L,3L,comment2);

        CommentDTO comment3 = new CommentDTO("Aenean sagittis enim et velit laoreet elementum. Aliquam finibus mi quis nunc dignissim");
        commentService.saveComment(2L,1L,comment3);

    }
}
