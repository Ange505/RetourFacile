package com.RetourFacile.dto.Request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UserUpdateRequest {
    private String username;
    private String email;
    private String numTel;
    private MultipartFile photo;  // Champ pour la photo de profil
}
