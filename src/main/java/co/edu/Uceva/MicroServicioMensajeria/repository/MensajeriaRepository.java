package co.edu.Uceva.MicroServicioMensajeria.repository;

import co.edu.Uceva.MicroServicioMensajeria.model.Mensajeria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MensajeriaRepository extends JpaRepository<Mensajeria, Long> {
    List<Mensajeria> findByCorreoDestinatario(String correoDestinatario);
    List<Mensajeria> findByFechaEnvio(LocalDate fechaEnvio);
}
