package in.lingayat.we.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Entity
@Table(name="user_personal")
public class UserPersonalDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotBlank
    private Date dob;

    @NotBlank
    private String placeOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

//    @NotBlank
    private Integer heightInCms;

//    @NotBlank
    private Integer weightInKgs;

    @Enumerated(EnumType.STRING)
    private Complexion complexion;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public UserPersonalDetails() {
    }


    public UserPersonalDetails(@NotBlank Date dob, @NotBlank String placeOfBirth, Gender gender, @NotBlank int heightInCms, @NotBlank int weightInKgs, Complexion complexion, MaritalStatus maritalStatus, User user) {
        this.dob = dob;
        this.placeOfBirth = placeOfBirth;
        this.gender = gender;
        this.heightInCms = heightInCms;
        this.weightInKgs = weightInKgs;
        this.complexion = complexion;
        this.maritalStatus = maritalStatus;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getHeightInCms() {
        return heightInCms;
    }

    public void setHeightInCms(int heightInCms) {
        this.heightInCms = heightInCms;
    }

    public int getWeightInKgs() {
        return weightInKgs;
    }

    public void setWeightInKgs(int weightInKgs) {
        this.weightInKgs = weightInKgs;
    }

    public Complexion getComplexion() {
        return complexion;
    }

    public void setComplexion(Complexion complexion) {
        this.complexion = complexion;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}