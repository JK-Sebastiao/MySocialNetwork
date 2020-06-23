package pl.jkiakumbo.MySocialNetworking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jkiakumbo.MySocialNetworking.model.Post;
import pl.jkiakumbo.MySocialNetworking.model.User;

import java.util.List;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostsByUserIn(Set<User> followedUsers);
    List<Post> findPostsByUser_Id(Long userId);
}
