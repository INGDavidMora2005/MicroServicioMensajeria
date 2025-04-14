package co.edu.Uceva.MicroServicioMensajeria.service;

import co.edu.Uceva.MicroServicioMensajeria.model.Mensajeria;
import co.edu.Uceva.MicroServicioMensajeria.repository.MensajeriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MensajeriaServiceImpl implements MensajeriaService {

    @Autowired
    private MensajeriaRepository mensajeriaRepository;

    @Override
    public Mensajeria guardarMensaje(Mensajeria mensaje) {
        return mensajeriaRepository.save(mensaje);
    }

    @Override
    public List<Mensajeria> listarMensajes() {
        return mensajeriaRepository.findAll();
    }

    @Override
    public Optional<Mensajeria> obtenerMensajePorId(Long id) {
        return mensajeriaRepository.findById(id);
    }

    @Override
    public Page<Mensajeria> obtenerMensajesPaginados(Pageable pageable) {
        return mensajeriaRepository.findAll(pageable);
    }
}

