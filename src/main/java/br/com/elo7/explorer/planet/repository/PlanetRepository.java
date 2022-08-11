package br.com.elo7.explorer.planet.repository;

import br.com.elo7.explorer.planet.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {

    public Optional<Planet> findByName(String planetName);
}
