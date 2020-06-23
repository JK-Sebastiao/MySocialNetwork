package pl.jkiakumbo.MySocialNetworking.DTO;

import javax.validation.constraints.NotBlank;

public class UserDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Surname is required")
    private String surname;

    @NotBlank(message = "Username is required")
    private String username;

    private String password;

    private String passwordConfirmation;

    public UserDTO(String name, String surname, String username, String password, String passwordConfirmation) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public UserDTO(String name, String surname, String username) {
        this.name = name;
        this.surname = surname;
        this.username = username;
    }

    public UserDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
