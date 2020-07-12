package india.lingayat.we.models;

import java.util.Set;

public class SafeUserDetails {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String contact;
    private String currentCity;
    private String permanentCity;
    private UserEducationalDetails userEducationalDetails;
    private Set<UserFamilyDetails> userFamilyDetails;
    private Set<SafeUserDetails> userFavourites;
    private UserImages userImages;
    private UserMedicalDetails userMedicalDetails;
    private UserPersonalDetails userPersonalDetails;
    private UserProfessionalDetails userProfessionalDetails;

    public SafeUserDetails() {
    }

    public SafeUserDetails(Long id, String firstName, String middleName, String lastName, String email, String contact, String currentCity, String permanentCity, UserEducationalDetails userEducationalDetails, Set<UserFamilyDetails> userFamilyDetails, UserImages userImages, UserMedicalDetails userMedicalDetails, UserPersonalDetails userPersonalDetails, UserProfessionalDetails userProfessionalDetails) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.contact = contact;
        this.currentCity = currentCity;
        this.permanentCity = permanentCity;
        this.userEducationalDetails = userEducationalDetails;
        this.userFamilyDetails = userFamilyDetails;
        this.userImages = userImages;
        this.userMedicalDetails = userMedicalDetails;
        this.userPersonalDetails = userPersonalDetails;
        this.userProfessionalDetails = userProfessionalDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getPermanentCity() {
        return permanentCity;
    }

    public void setPermanentCity(String permanentCity) {
        this.permanentCity = permanentCity;
    }

    public UserEducationalDetails getUserEducationalDetails() {
        return userEducationalDetails;
    }

    public void setUserEducationalDetails(UserEducationalDetails userEducationalDetails) {
        this.userEducationalDetails = userEducationalDetails;
    }

    public Set<UserFamilyDetails> getUserFamilyDetails() {
        return userFamilyDetails;
    }

    public void setUserFamilyDetails(Set<UserFamilyDetails> userFamilyDetails) {
        this.userFamilyDetails = userFamilyDetails;
    }

    public UserImages getUserImages() {
        return userImages;
    }

    public void setUserImages(UserImages userImages) {
        this.userImages = userImages;
    }

    public UserMedicalDetails getUserMedicalDetails() {
        return userMedicalDetails;
    }

    public void setUserMedicalDetails(UserMedicalDetails userMedicalDetails) {
        this.userMedicalDetails = userMedicalDetails;
    }

    public UserPersonalDetails getUserPersonalDetails() {
        return userPersonalDetails;
    }

    public void setUserPersonalDetails(UserPersonalDetails userPersonalDetails) {
        this.userPersonalDetails = userPersonalDetails;
    }

    public UserProfessionalDetails getUserProfessionalDetails() {
        return userProfessionalDetails;
    }

    public void setUserProfessionalDetails(UserProfessionalDetails userProfessionalDetails) {
        this.userProfessionalDetails = userProfessionalDetails;
    }

    public Set<SafeUserDetails> getUserFavourites() {
        return userFavourites;
    }

    public void setUserFavourites(Set<SafeUserDetails> userFavourites) {
        this.userFavourites = userFavourites;
    }
}
