package co.edu.Uceva.MicroServicioMensajeria.service;

import co.edu.Uceva.MicroServicioMensajeria.model.Mensajeria;

import java.util.List;
import java.util.Optional;

public interface MensajeriaService {
    Mensajeria guardarMensaje(Mensajeria mensajeria);
    Optional<Mensajeria> obtenerMensajePorId(Long id);
    List<Mensajeria> listarMensajes();
    void eliminarMensaje(Long id);
}
