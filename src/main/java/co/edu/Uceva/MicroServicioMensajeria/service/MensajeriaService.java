package co.edu.Uceva.MicroServicioMensajeria.service;

import co.edu.Uceva.MicroServicioMensajeria.model.Mensajeria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MensajeriaService {
    // Guarda un mensaje nuevo o actualizado
    Mensajeria guardarMensaje(Mensajeria mensaje);

    // Busca un mensaje por su id, si no existe devuelve un optional
    Optional<Mensajeria> obtenerMensajePorId(Long id);

    // Lista todos los mensajes ya guardados
    List<Mensajeria> listarMensajes();

    Page<Mensajeria> obtenerMensajesPaginados(Pageable pageable);
}
