package pl.jkiakumbo.MySocialNetworking.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.jkiakumbo.MySocialNetworking.DTO.UserDTO;
import pl.jkiakumbo.MySocialNetworking.model.User;
import pl.jkiakumbo.MySocialNetworking.service.UserService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        User user = userService.getUserById(id);
        if(user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserDTO userDTO){
        return new ResponseEntity<>(userService.saveUser(userDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO){
        User updateUser = userService.updateUser(id,userDTO);
        if(updateUser == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}/follow/user/{userId}")
    public ResponseEntity addFollowedUser(@PathVariable Long id, @PathVariable Long userId){
        userService.addFollowedUser(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/followed/users/{id}")
    public ResponseEntity<Set<User>> getFollowedUsers(@PathVariable Long id){
        return new ResponseEntity<>(userService.getFollowedUsers(id), HttpStatus.OK);
    }

}
