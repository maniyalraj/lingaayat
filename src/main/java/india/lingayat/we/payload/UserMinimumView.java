package india.lingayat.we.payload;

import india.lingayat.we.models.enums.Complexion;
import india.lingayat.we.models.enums.Gender;
import india.lingayat.we.models.enums.MaritalStatus;

import java.sql.Date;

public class UserMinimumView {

//    Users.java
    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

//    UserPersonal

    private Date dob;

    private int heightInCms;

    private int weightInKgs;

    private Gender gender;

    private MaritalStatus maritalStatus;

//    UserImages
    private String imageUrl;


//    User Professsional
    private long monthlyIncome;

    private String jobRole;

//    User Educational
    private String qualification;

    public UserMinimumView() {

    }

}
