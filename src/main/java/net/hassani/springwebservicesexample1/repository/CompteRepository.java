package net.hassani.springwebservicesexample1.repository;

import net.hassani.springwebservicesexample1.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte, Long> {
}
