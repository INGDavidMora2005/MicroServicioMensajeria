package co.edu.Uceva.MicroServicioMensajeria.repository;

import co.edu.Uceva.MicroServicioMensajeria.model.Mensajeria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

// Le indica a spring que es una interfaz que accede a los datos de la BD
@Repository
public interface MensajeriaRepository extends JpaRepository<Mensajeria, Long> {
    // Metodo para buscar todos los mensajes por el correo del destinatario
    List<Mensajeria> findByCorreoDestinatario(String correoDestinatario);

    // Metodo para buscar todos los mensajes por una fecha específica de envio
    List<Mensajeria> findByFechaEnvio(LocalDate fechaEnvio);
}
