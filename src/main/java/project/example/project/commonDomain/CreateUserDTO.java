package project.example.project.commonDomain;

import project.example.project.exceptions.DomainException;

public class CreateUserDTO {

    private String username;
    private String email;
    private String password;
    private String role;

    public void setEmail(String email){
        this.email = email;

    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public void setRole(String role){
        if (role.equals("admin") || role.equals("teamleader") || role.equals("developer")){
            this.role = role;
        }
        else throw new DomainException("Role should be either 'admin' or 'teamleader' or 'developer'");
    }
    public String getRole(){
        return this.role;
    }

}


