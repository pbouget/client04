package fr.bouget.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.bouget.jpa.model.Client;

/**
 * @author Philippe
 *
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

}
