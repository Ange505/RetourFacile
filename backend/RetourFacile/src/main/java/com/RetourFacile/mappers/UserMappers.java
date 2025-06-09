package com.RetourFacile.mappers;

import com.RetourFacile.dto.Request.LoginRequest;
import com.RetourFacile.dto.Response.UserResponse;
import com.RetourFacile.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMappers {

    public User toEntity(LoginRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        // Pas besoin de setTrackingId ici, il sera généré automatiquement avec @PrePersist
        return user;
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getTrackingId(),
                user.getUsername(),
                user.getEmail(),
                user.getNumTel(),
                user.getPhotoProfil(),
                user.isConnected(),
                user.isActivated(),
                user.getRole()
        );
    }
}
