package pl.jkiakumbo.MySocialNetworking.builder;

import pl.jkiakumbo.MySocialNetworking.model.User;

public class UserBuilder {
    private User user;

    public UserBuilder() {
        this.user = new User();
    }
    public UserBuilder setName(String name){
        this.user.setName(name);
        return this;
    }
    public UserBuilder setSurname(String surname){
        this.user.setSurname(surname);
        return this;
    }
    public UserBuilder setUsername(String username){
        this.user.setUsername(username);
        return this;
    }
    public UserBuilder setPassword(String password){
        this.user.setPassword(password);
        return this;
    }
    public User build(){
        return this.user;
    }
}
