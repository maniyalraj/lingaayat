package india.lingayat.we.controllers;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import india.lingayat.we.models.*;
import india.lingayat.we.payload.AddCreditsPayload;
import india.lingayat.we.payload.ApiResponse;
import india.lingayat.we.payload.FilterRequest;
import india.lingayat.we.repositories.CreditsAuditRepository;
import india.lingayat.we.repositories.UserRepository;
import india.lingayat.we.specifications.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CreditsAuditRepository creditsAuditRepository;

    @PostMapping("addCredits")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addCredits(@CurrentUser UserPrincipal currentUser, @RequestBody AddCreditsPayload creditsToAdd) {


        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/addCredits")
                .buildAndExpand(currentUser.getUsername()).toUri();


        if(creditsToAdd.getCreditsToAdd() > 0 && creditsToAdd.getCreditsToAdd() < 10000)
        {
            User user = userRepository.findById(creditsToAdd.getUserId()).orElse(null);

            user.setCredits(user.getCredits()+creditsToAdd.getCreditsToAdd());

            userRepository.save(user);

            CreditsAudit creditsAudit = new CreditsAudit();

            creditsAudit.setCreditsAdded(creditsToAdd.getCreditsToAdd());
            creditsAudit.setReferenceNumber(creditsToAdd.getReference());
            creditsAudit.setUserReference(creditsToAdd.getUserId());

            creditsAuditRepository.save(creditsAudit);

            return ResponseEntity.created(location).body(new ApiResponse(true, "Credits added"));

        }
        else
        {
            return new ResponseEntity<>(new ApiResponse(false, "Invalid Credit amount"), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PostMapping("getAllUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> getAllUsers(Pageable pageable, @CurrentUser UserPrincipal currentUser, @RequestBody FilterRequest filterRequest){

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        String firstName = filterRequest.getFirstName();
        String middleName = filterRequest.getMiddleName();
        String lastName = filterRequest.getLastName();

        booleanBuilder.and(QUser.user.id.ne(0L));

        if(!StringUtils.isEmpty(firstName))
        {
            booleanBuilder.and(QUser.user.firstName.likeIgnoreCase(firstName));
        }

        if(!StringUtils.isEmpty(middleName))
        {
            booleanBuilder.and(QUser.user.middleName.likeIgnoreCase(middleName));
        }

        if(!StringUtils.isEmpty(lastName))
        {
            booleanBuilder.and(QUser.user.lastName.likeIgnoreCase(lastName));
        }

        return userRepository.findAll(booleanBuilder.getValue(),pageable);
    }
}
