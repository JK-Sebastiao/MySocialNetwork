package pl.jkiakumbo.MySocialNetworking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jkiakumbo.MySocialNetworking.DTO.UserDTO;
import pl.jkiakumbo.MySocialNetworking.builder.UserBuilder;
import pl.jkiakumbo.MySocialNetworking.model.User;
import pl.jkiakumbo.MySocialNetworking.repository.UserRepository;
import pl.jkiakumbo.MySocialNetworking.util.EncoderUtil;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public User getUserById(Long id){
        Optional<User> optionalUser = this.userRepository.findById(id);
        if(!optionalUser.isPresent())
            return null;
        return this.userRepository.findById(id).get();

    }

    @Transactional
    public User saveUser(UserDTO userDTO){
        User user = new UserBuilder()
                .setName(userDTO.getName())
                .setSurname(userDTO.getSurname())
                .setUsername(userDTO.getUsername())
                .setPassword(EncoderUtil.encode(userDTO.getPassword()))
                .build();
        return this.userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, UserDTO userDTO){
        User userFromDB = this.getUserById(id);
        if(userFromDB != null){
            userFromDB.setName(userDTO.getName());
            userFromDB.setSurname(userDTO.getSurname());
            userFromDB.setUsername(userDTO.getUsername());
            return this.userRepository.save(userFromDB);
        }
        return null;
    }

    public void deleteUserById(Long id){
        this.userRepository.deleteById(id);
    }

    @Transactional
    public void addFollowedUser(Long id, Long userToFollowId){
        User user = this.getUserById(id);
        user.addFollowedUser(this.getUserById(userToFollowId));
        this.userRepository.save(user);
    }

    public Set<User> getFollowedUsers(Long id){
        return this.getUserById(id).getFollowedUsers();
    }
}
