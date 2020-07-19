package india.lingayat.we.controllers;

import india.lingayat.we.models.CurrentUser;
import india.lingayat.we.models.User;
import india.lingayat.we.models.UserPrincipal;
import india.lingayat.we.payload.AddCreditsPayload;
import india.lingayat.we.payload.ApiResponse;
import india.lingayat.we.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/user/addCredits")
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

            return ResponseEntity.created(location).body(new ApiResponse(true, "Credits added"));

        }
        else
        {
            return new ResponseEntity<>(new ApiResponse(false, "Invalid Credit amount"), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


}
