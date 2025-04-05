package co.edu.Uceva.MicroServicioMensajeria.service;

import co.edu.Uceva.MicroServicioMensajeria.model.Mensajeria;
import co.edu.Uceva.MicroServicioMensajeria.repository.MensajeriaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MensajeriaServiceImpl implements MensajeriaService {

    private final MensajeriaRepository mensajeriaRepository;

    public MensajeriaServiceImpl(MensajeriaRepository mensajeriaRepository) {
        this.mensajeriaRepository = mensajeriaRepository;
    }

    @Override
    public Mensajeria guardarMensaje(Mensajeria mensajeria) {
        if (mensajeria.getFechaEnvio() == null) {
            mensajeria.setFechaEnvio(LocalDate.now());
        }

        return mensajeriaRepository.save(mensajeria);
    }

    @Override
    public Optional<Mensajeria> obtenerMensajePorId(Long id) {
        return mensajeriaRepository.findById(id);
    }

    @Override
    public List<Mensajeria> listarMensajes() {
        return mensajeriaRepository.findAll();
    }

    @Override
    public void eliminarMensaje(Long id) {
        mensajeriaRepository.deleteById(id);
    }
}
