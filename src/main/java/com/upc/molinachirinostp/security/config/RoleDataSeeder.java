package com.upc.molinachirinostp.security.config;

import com.upc.molinachirinostp.entity.Role;
import com.upc.molinachirinostp.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleDataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleDataSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleUser.setDescription("Usuario estándar de la plataforma");
            roleRepository.save(roleUser);

            Role roleMentor = new Role();
            roleMentor.setName("ROLE_MENTOR");
            roleMentor.setDescription("Usuario con permisos de mentor");
            roleRepository.save(roleMentor);

            Role roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleAdmin.setDescription("Administrador del sistema");
            roleRepository.save(roleAdmin);

            System.out.println("✅ Roles iniciales creados: ROLE_USER, ROLE_MENTOR, ROLE_ADMIN");
        }
    }
}
