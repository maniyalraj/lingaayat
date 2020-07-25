package india.lingayat.we.utils;

import india.lingayat.we.models.SafeUserDetails;
import india.lingayat.we.models.User;
import india.lingayat.we.models.UserAdditionalDetails;
import india.lingayat.we.models.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static SafeUserDetails mapUserToSafeUsers(UserPrincipal currentUser, User user, boolean isUnlocked)
    {
        SafeUserDetails sud = new SafeUserDetails();

        sud.setUnlocked(isUnlocked);

        boolean isAdmin = currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if(user == null)
        {
            return null;
        }

            sud.setId(user.getId());
            sud.setFirstName(user.getFirstName());
            sud.setMiddleName(user.getMiddleName());


            UserAdditionalDetails uad = user.getUserAdditionalDetails();

            if (uad != null) {
                sud.setCurrentCity(uad.getCurrentCity());
                sud.setPermanentCity(uad.getPermanentCity());
            } else {
                sud.setCurrentCity("");
                sud.setPermanentCity("");
            }

            sud.setUserEducationalDetails(user.getUserEducationalDetails());
            sud.setUserImages(user.getUserImages());
            sud.setUserMedicalDetails(user.getUserMedicalDetails());
            sud.setUserPersonalDetails(user.getUserPersonalDetails());
            sud.setUserProfessionalDetails(user.getUserProfessionalDetails());

            sud.setLastName(user.getLastName());
            sud.setEmail(user.getEmail());
            sud.setContact(user.getContact());
            sud.setUserFamilyDetails(user.getUserFamilyDetails());

            if (currentUser.getId() == user.getId() || isAdmin) {
                Set<SafeUserDetails> sudSet = new HashSet<>();
                Set<User> unlockedUsers = user.getUnlockedList();
                Set<SafeUserDetails> unlockedSud = new HashSet<>();

                user.getFavouritesList().forEach(u -> sudSet.add(Utils.mapUserToSafeUsers(currentUser, u, unlockedUsers.contains(u))));
                sud.setUserFavourites(sudSet);


                user.getUnlockedList().forEach(u -> unlockedSud.add(Utils.mapUserToSafeUsers(currentUser, u, true)));

                sud.setUnlockedUsers(unlockedSud);
                sud.setCredits(user.getCredits());

                sud.setUserImageLibrary(user.getUserImageLibrary());

            }else if(!isUnlocked)
            {
                sud.setLastName(user.getLastName().substring(0,1).concat("****"));
                sud.setMiddleName(user.getMiddleName().substring(0,1).concat("****"));
                sud.setEmail("*****");
                sud.setContact("****");
                sud.setUserFamilyDetails(new HashSet<>());
            }

            return sud;

    }
}
