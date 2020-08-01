package india.lingayat.we.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import india.lingayat.we.models.CurrentUser;
import india.lingayat.we.models.User;
import india.lingayat.we.models.UserImages;
import india.lingayat.we.models.UserPrincipal;
import india.lingayat.we.payload.ApiResponse;
import india.lingayat.we.repositories.UserImageRepository;
import india.lingayat.we.repositories.UserRepository;
import india.lingayat.we.services.ClouditonaryImageService;
import india.lingayat.we.services.S3ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ImageUploadController {

    @Autowired
    private ClouditonaryImageService clouditonaryImageService;

    @Autowired
    private S3ImageUploadService s3ImageUploadService;

    @Autowired
    private UserImageRepository userImageRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/save/profileImage")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> uploadImage(@CurrentUser UserPrincipal currentUser, @RequestParam("file") MultipartFile file)  {

        Cloudinary cloudinary = clouditonaryImageService.getCloudinary();
        Map uploadResult = null;
        String responseMessage = "";
        String imageFoler = "profile";

        User user = userRepository.findByEmail(currentUser.getEmail());

        //            uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        uploadResult = s3ImageUploadService.uploadImage(file, user, imageFoler);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/upload/image")
                .buildAndExpand("image").toUri();

        System.out.println(uploadResult);
        if(uploadResult.get("url")!=null)
        {

            responseMessage += uploadResult.get("url").toString();
            UserImages userImages = new UserImages();

            userImages.setImageUrl(uploadResult.get("url").toString());
            userImages.setImageType(imageFoler);
            userImages.setImageKey(uploadResult.get("bucketKey").toString());
            userImages.setUser(user);
            user.setUserImages(userImages);
            userRepository.save(user);
        }

        return ResponseEntity.created(location).body(new ApiResponse(true, responseMessage));
    }

    @GetMapping("/get/profileImage")
    @PreAuthorize("hasRole('USER')")
    public UserImages getProfileImage(@CurrentUser UserPrincipal currentUser){
        User user = userRepository.findById(currentUser.getId()).orElse(null);

        return user.getUserImages();
    }

    @PostMapping("/save/libraryImage")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> uploadLibraryImage(@CurrentUser UserPrincipal currentUser, @RequestParam("file") MultipartFile file)  {

        Map uploadResult = null;
        String responseMessage = "";
        String imageFolder = "library";

        User user = userRepository.findById(currentUser.getId()).orElse(null);

        int countImages = user.getUserImageLibrary().size();

        if(countImages > 4)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Cannot upload more than 4 images"));
        }

        uploadResult = s3ImageUploadService.uploadImage(file, user, imageFolder);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/upload/image")
                .buildAndExpand("image").toUri();


        if(uploadResult.get("url")!=null)
        {

            responseMessage += uploadResult.get("url").toString();
            UserImages userImage = new UserImages();
            userImage.setImageUrl(uploadResult.get("url").toString());
            userImage.setImageType(imageFolder);
            userImage.setImageKey(uploadResult.get("bucketKey").toString());
            userImage.setUser(user);
            Set<UserImages> library =  user.getUserImageLibrary();

            if(library == null)
            {
                library = new HashSet<UserImages>();

            }
            library.add(userImage);

            user.setUserImageLibrary(library);
            userRepository.save(user);

        }

        return ResponseEntity.created(location).body(new ApiResponse(true, responseMessage));
    }

    @GetMapping("/get/libraryImages")
    @PreAuthorize("hasRole('USER')")
    public Set<UserImages> getLibraryImages(@CurrentUser UserPrincipal currentUser){
        User user = userRepository.findById(currentUser.getId()).orElse(null);

        return user.getUserImageLibrary();
    }

    @PostMapping("/delete/libraryImage")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteImage(@CurrentUser UserPrincipal currentUser, @RequestBody UserImages imageToDelete)
    {
        User user = userRepository.findById(currentUser.getId()).orElse(null);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/delete/libraryImage")
                .buildAndExpand("image").toUri();

        boolean canDelete = false;

        Set<UserImages> imagesLibrary = user.getUserImageLibrary();
        for(Iterator<UserImages> itr=imagesLibrary.iterator(); itr.hasNext();)
        {
            UserImages _image = itr.next();

            if(_image.getId() == imageToDelete.getId())
            {
                imagesLibrary.remove(_image);
                canDelete = true;
                break;
            }
        }

        if(canDelete) {

            s3ImageUploadService.deleteImage(imageToDelete.getImageKey());

            user.setUserImageLibrary(imagesLibrary);
            userRepository.save(user);

            return ResponseEntity.created(location).body(new ApiResponse(true, "Image Delete Succesful"));

        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Not current user's Image"));

        }

    }

}
