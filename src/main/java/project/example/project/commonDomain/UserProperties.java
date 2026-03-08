package project.example.project.commonDomain;

public class UserProperties {
    private Long userId;
    private String userEmail;
    private String userName;
    private String userImage;

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
    public String getUserImage() {
        return userImage;
    }

}
