package pl.jkiakumbo.MySocialNetworking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jkiakumbo.MySocialNetworking.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
