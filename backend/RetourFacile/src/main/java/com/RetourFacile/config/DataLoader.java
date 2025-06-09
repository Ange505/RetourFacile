package com.RetourFacile.config;

import com.RetourFacile.entity.Role;
import com.RetourFacile.entity.User;
import com.RetourFacile.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.findAllByRole(Role.ADMIN).isEmpty()) {
            User admin1 = new User();
            admin1.setUsername("admin1");
            admin1.setEmail("admin1@example.com");
            admin1.setPassword(passwordEncoder.encode("admin123"));
            admin1.setRole(Role.ADMIN);
            admin1.setTrackingId(UUID.randomUUID()); // Correction ici
            admin1.setActivated(true); // Activation du compte
            admin1.setConnected(false); // Statut de connexion
            admin1.setNumTel("99576313"); // Numéro de téléphone ajouté

            User admin2 = new User();
            admin2.setUsername("admin2");
            admin2.setEmail("admin2@example.com");
            admin2.setPassword(passwordEncoder.encode("admin123"));
            admin2.setRole(Role.ADMIN);
            admin2.setTrackingId(UUID.randomUUID()); // Correction ici
            admin2.setActivated(true); // Activation du compte
            admin2.setConnected(false); // Statut de connexion
            admin2.setNumTel("99576313"); // Numéro de téléphone ajouté

            userRepository.save(admin1);
            userRepository.save(admin2);

            System.out.println("✅ Admins créés avec succès !");
        }
    }
}
