package co.edu.Uceva.MicroServicioMensajeria.repository;

import co.edu.Uceva.MicroServicioMensajeria.model.Mensajeria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensajeriaRepository extends JpaRepository<Mensajeria, Long> {
}

