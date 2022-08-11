package br.com.elo7.explorer.probe.repository;

import br.com.elo7.explorer.probe.model.Probe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProbeRepository extends JpaRepository<Probe, Long> {
}
