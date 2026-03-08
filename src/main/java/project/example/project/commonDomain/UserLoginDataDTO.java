package project.example.project.commonDomain;

public class UserLoginDataDTO {
    private Long id;
    private String username;
    private String email;
    private String image;
    private String role;

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getImage(){
        return image;
    }
    public void setImage(String image){
        this.image = image;
    }

    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role = role;
    }

}
