package net.hassani.springwebservicesexample1;

import net.hassani.springwebservicesexample1.entities.Compte;
import net.hassani.springwebservicesexample1.repository.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SpringWebServicesExample1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebServicesExample1Application.class, args);
    }

    @Bean
    CommandLineRunner start(CompteRepository compteRepository) {
        return args -> {
            Compte c1 = new Compte(null, 9000, new Date(), "ACTIVE");
            Compte c2 = new Compte(null, 6000, new Date(), "SUSPENDED");
            Compte c3 = new Compte(null, 3000, new Date(), "ACTIVE");
            compteRepository.save(c1);
            compteRepository.save(c2);
            compteRepository.save(c3);
            List<Compte> comptes = compteRepository.findAll();
            comptes.forEach(compte->{
                System.out.println(compte);
            });
        };
    }
}
