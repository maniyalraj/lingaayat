package india.lingayat.we.utils;

import india.lingayat.we.models.SafeUserDetails;
import india.lingayat.we.models.User;
import india.lingayat.we.models.UserAdditionalDetails;

import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static SafeUserDetails mapUserToSafeUsers(User user, boolean showFavourites)
    {
        SafeUserDetails sud = new SafeUserDetails();

        if(user == null)
        {
            return null;
        }

        sud.setId(user.getId());
        sud.setFirstName(user.getFirstName());
        sud.setMiddleName(user.getMiddleName());
        sud.setLastName(user.getLastName());
        sud.setEmail(user.getEmail());
        sud.setContact(user.getContact());

        UserAdditionalDetails uad = user.getUserAdditionalDetails();

        if(uad!=null)
        {
            sud.setCurrentCity(uad.getCurrentCity());
            sud.setPermanentCity(uad.getPermanentCity());
        }
        else{
            sud.setCurrentCity("");
            sud.setPermanentCity("");
        }

        sud.setUserEducationalDetails(user.getUserEducationalDetails());
        sud.setUserFamilyDetails(user.getUserFamilyDetails());
        sud.setUserImages(user.getUserImages());
        sud.setUserMedicalDetails(user.getUserMedicalDetails());
        sud.setUserPersonalDetails(user.getUserPersonalDetails());
        sud.setUserProfessionalDetails(user.getUserProfessionalDetails());

        if(showFavourites)
        {
            Set<SafeUserDetails> sudSet =  new HashSet<>();
            user.getFavouritesList().forEach(u -> sudSet.add(Utils.mapUserToSafeUsers(u, false)));
            sud.setUserFavourites(sudSet);
        }

        return sud;
    }
}
