package india.lingayat.we.controllers;

import com.querydsl.core.BooleanBuilder;
import india.lingayat.we.models.*;
import india.lingayat.we.models.enums.Gender;
import india.lingayat.we.payload.FilterRequest;
import india.lingayat.we.payload.SafeUserPage;
import india.lingayat.we.payload.UserCount;
import india.lingayat.we.repositories.*;
import india.lingayat.we.repositories.UserRepository;
import india.lingayat.we.services.CacheEvictionService;
import india.lingayat.we.specifications.UserSpecification;
import india.lingayat.we.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class SearchController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPersonalDetailsRepository userPersonalDetails;


    @Autowired
    private CacheEvictionService cacheEvictionService;

    @PersistenceContext
    EntityManager em;

    @GetMapping("/evictCache")
    @PreAuthorize("hasRole('USER')")
    public void evictCache()
    {
        cacheEvictionService.evictCache();
    }


    @PostMapping("/getAllUsers")
    @PreAuthorize("hasRole('USER')")
    public SafeUserPage getAllUsers(Pageable pageable, @CurrentUser UserPrincipal currentUser, @RequestBody FilterRequest filterRequest) {

        Specification<User> specification = Specification.where(UserSpecification.alwaysTrue());

        User cuser = userRepository.findById(currentUser.getId()).orElse(null);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(QUser.user.id.ne(currentUser.getId()));

        if(cuser.getUserPersonalDetails() !=null)
        {
            booleanBuilder.and(QUser.user.userPersonalDetails.gender.ne(cuser.getUserPersonalDetails().getGender()));
        }

        if (filterRequest.getMaxHeight() > 0) {

            booleanBuilder.and(
                    QUser.user.userPersonalDetails.heightInCms.between(
                            filterRequest.getMinHeight(), filterRequest.getMaxHeight()
                    )
            );
        }

        if (filterRequest.getMaxAge() > 0) {

            java.util.Date maxDateInMillis = new java.util.Date(System.currentTimeMillis() - (filterRequest.getMaxAge() * 1000L * 60L * 60L
                    * 24L * 365L));
            java.util.Date minDateInMillis = new java.util.Date(System.currentTimeMillis() - (filterRequest.getMinAge() * 1000L * 60L * 60L
                    * 24L * 365L));

            Date minDate = new Date(minDateInMillis.getTime());
            Date maxDate = new Date(maxDateInMillis.getTime());

            booleanBuilder.and(
                    QUser.user.userPersonalDetails.dob.between(
                            maxDate, minDate)
                    );

        }

        if(filterRequest.getMinSalary() > 0){
            booleanBuilder.and(
              QUser.user.userProfessionalDetails.monthlyIncome.goe(filterRequest.getMinSalary())
            );
        }

        if(filterRequest.getQualification() != null && filterRequest.getQualification().size() !=0){
            booleanBuilder.and(
                    QUser.user.userEducationalDetails.qualification.in(filterRequest.getQualification())
            );
        }


        if(!StringUtils.isEmpty(filterRequest.getFirstName()))
        {
            booleanBuilder.and(
                    QUser.user.firstName.likeIgnoreCase(filterRequest.getFirstName())
            );
        }

        if(!StringUtils.isEmpty(filterRequest.getLastName()))
        {
            booleanBuilder.and(
                    QUser.user.lastName.likeIgnoreCase(filterRequest.getLastName())
            );
        }

        if(filterRequest.getJobType() != null && filterRequest.getJobType().size() !=0){
            booleanBuilder.and(
                    QUser.user.userProfessionalDetails.jobType.in(filterRequest.getJobType())
            );
        }

        if(!StringUtils.isEmpty(filterRequest.getCityNameOrPin()))
        {
            if(isNumeric(filterRequest.getCityNameOrPin())){
                booleanBuilder.and(
                        QUser.user.userAdditionalDetails.currentPinCode.eq(Integer.parseInt(filterRequest.getCityNameOrPin()))
                );
            }else
            {
                booleanBuilder.and(
                        QUser.user.userAdditionalDetails.currentCity.likeIgnoreCase(filterRequest.getCityNameOrPin())
                );
            }
        }

        Page<User> userPage = userRepository.findAll(booleanBuilder.getValue(), pageable);

        List<User> userList = userPage.toList();

        Set<SafeUserDetails> safeUserDetailsList = new HashSet<>();

        Set<User> unlockedUsers = cuser.getUnlockedList();

        userList.stream().forEach(user ->safeUserDetailsList.add(Utils.mapUserToSafeUsers(currentUser, user, unlockedUsers.contains(user))));

        SafeUserPage page = new SafeUserPage(userPage.getTotalElements(), safeUserDetailsList);

        return page;

    }

    @GetMapping("/getUser/{id}")
    public SafeUserDetails getUser(@CurrentUser UserPrincipal currentUser, @PathVariable Long id)
    {

        SafeUserDetails sud =  new SafeUserDetails();

        User user = userRepository.findById(id).orElse(null);

        User _user = userRepository.findById(currentUser.getId()).orElse(null);

        boolean isUnlocked = _user.getUnlockedList().contains(user);

        boolean canView = user != null
                && _user !=null
                && user.getUserPersonalDetails() !=null
                && _user.getUserPersonalDetails() !=null
                && !user.getUserPersonalDetails().getGender()
                .equals(
                        _user.getUserPersonalDetails().getGender()
                );
        if(currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
        canView = true;
    }

        if(canView)
        {
            sud = Utils.mapUserToSafeUsers(currentUser, user, isUnlocked);
        }
        else
        {
            sud = null;
        }


        return sud;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @GetMapping("/noauth/getUserCount")
    public List<UserCount> getUserCount(){
        List<UserCount> result =  new ArrayList<>();

        result.add(new UserCount(Gender.GENDER_FEMALE, userPersonalDetails.countByGender(Gender.GENDER_FEMALE)));
        result.add(new UserCount(Gender.GENDER_MALE, userPersonalDetails.countByGender(Gender.GENDER_MALE)));

    return result;
    }
}
