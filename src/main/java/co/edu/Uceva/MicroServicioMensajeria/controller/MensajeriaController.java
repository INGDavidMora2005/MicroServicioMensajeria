package co.edu.Uceva.MicroServicioMensajeria.controller;

import co.edu.Uceva.MicroServicioMensajeria.dto.RespuestaMensajeDTO;
import co.edu.Uceva.MicroServicioMensajeria.model.Mensajeria;
import co.edu.Uceva.MicroServicioMensajeria.repository.MensajeriaRepository;
import co.edu.Uceva.MicroServicioMensajeria.service.MensajeriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mensajes")
public class MensajeriaController {

    private final MensajeriaService mensajeriaService;
    private final MensajeriaRepository mensajeriaRepository;

    public MensajeriaController(MensajeriaService mensajeriaService, MensajeriaRepository mensajeriaRepository) {
        this.mensajeriaService = mensajeriaService;
        this.mensajeriaRepository = mensajeriaRepository;
    }

    @PostMapping
    public ResponseEntity<RespuestaMensajeDTO> crearMensaje(@Valid @RequestBody Mensajeria mensajeria) {
        if (mensajeria.getFechaEnvio() == null) {
            mensajeria.setFechaEnvio(LocalDate.now());
        }

        Mensajeria nuevoMensaje = mensajeriaService.guardarMensaje(mensajeria);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RespuestaMensajeDTO("Mensaje creado correctamente", nuevoMensaje));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerMensajePorId(@PathVariable Long id) {
        Optional<Mensajeria> mensaje = mensajeriaService.obtenerMensajePorId(id);
        return mensaje.<ResponseEntity<?>>map(value ->
                ResponseEntity.ok(new RespuestaMensajeDTO("Mensaje encontrado", value)))
                .orElseGet(() ->
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new RespuestaMensajeDTO("Mensaje no encontrado", null)));
    }

    @GetMapping("/buscarPorCorreo")
    public ResponseEntity<?> obtenerMensajePorCorreo(@RequestParam String correo) {
        List<Mensajeria> mensajes = mensajeriaRepository.findByCorreoDestinatario(correo);

        if (mensajes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body("No se encontraron mensajes para este correo");
        }

        return ResponseEntity.ok(mensajes);
    }

    @GetMapping("/buscarPorFecha")
    public ResponseEntity<?> buscarPorFecha(@RequestParam String fecha) {
        LocalDate fechaEnvio = LocalDate.parse(fecha);
        List<Mensajeria> mensajes = mensajeriaRepository.findByFechaEnvio(fechaEnvio);

        if (mensajes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay mensajes encontrados para esta fecha");
        }

        return ResponseEntity.ok(mensajes);
    }

    @GetMapping
    public ResponseEntity<?> listarMensajes() {
        List<Mensajeria> mensajes = mensajeriaService.listarMensajes();
        if (mensajes.isEmpty()) {
            return ResponseEntity.ok(new RespuestaMensajeDTO("No hay mensajes registrados", null));
        }

        return ResponseEntity.ok(new RespuestaMensajeDTO("Lista de mensajes", mensajes));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaMensajeDTO> actualizarMensaje(@PathVariable Long id, @RequestBody Mensajeria mensajeActualizado) {
        Optional<Mensajeria> mensajeExistente = mensajeriaService.obtenerMensajePorId(id);

        if (mensajeExistente.isPresent()) {
            Mensajeria mensaje = mensajeExistente.get();
            mensaje.setAsunto(mensajeActualizado.getAsunto());
            mensaje.setCorreoDestinatario(mensajeActualizado.getCorreoDestinatario());
            mensaje.setCuerpoCorreo(mensajeActualizado.getCuerpoCorreo());
            mensaje.setFechaEnvio(mensajeActualizado.getFechaEnvio());

            Mensajeria mensajeGuardado = mensajeriaService.guardarMensaje(mensaje);
            return ResponseEntity.ok(new RespuestaMensajeDTO("Mensaje actualizado correctamente", mensajeGuardado));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new RespuestaMensajeDTO("Mensaje no encontrado", null));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaMensajeDTO> eliminarMensaje(@PathVariable Long id) {
        if (!mensajeriaRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaMensajeDTO("Mensaje no encontrado", null));
        }

        mensajeriaRepository.deleteById(id);
        return ResponseEntity.ok(new RespuestaMensajeDTO("Mensaje eliminado correctamente", null));
    }
}
