package india.lingayat.we.payload;

import india.lingayat.we.models.SafeUserDetails;
import india.lingayat.we.models.User;

import java.util.List;
import java.util.Set;

public class UserSummary {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String middleName;
    private String contact;
    private String email;
    private Set<User> favouritesList;

    public UserSummary(Long id, String username, String firstName, String lastName, String middleName, String contact, String email,Set<User> favouritesList) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.contact = contact;
        this.email = email;
        this.favouritesList = favouritesList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<User> getFavouritesList() {
        return favouritesList;
    }

    public void setFavouritesList(Set<User> favouritesList) {
        this.favouritesList = favouritesList;
    }
}
