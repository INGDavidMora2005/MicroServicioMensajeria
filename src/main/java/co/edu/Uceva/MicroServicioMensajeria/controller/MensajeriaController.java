package co.edu.Uceva.MicroServicioMensajeria.controller;

import co.edu.Uceva.MicroServicioMensajeria.dto.MensajeRequestDTO;
import co.edu.Uceva.MicroServicioMensajeria.dto.MensajeResponseDTO;
import co.edu.Uceva.MicroServicioMensajeria.dto.MensajeUpdateDTO;
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

// Esta es una clase Rest para manejar las solicitudes HTTP
@RestController
@RequestMapping("/mensajes") // La ruta del microservicio
public class MensajeriaController {

    private final MensajeriaService mensajeriaService;
    private final MensajeriaRepository mensajeriaRepository;

    // Por medio del constructor se hace la inyeccion de las dependencias
    public MensajeriaController(MensajeriaService mensajeriaService, MensajeriaRepository mensajeriaRepository) {
        this.mensajeriaService = mensajeriaService;
        this.mensajeriaRepository = mensajeriaRepository;
    }

    // Este metodo permite la creacion del mensaje
    @PostMapping
    public ResponseEntity<RespuestaMensajeDTO> crearMensaje(@Valid @RequestBody MensajeRequestDTO dto) {
        Mensajeria mensaje = new Mensajeria();
        mensaje.setAsunto(dto.getAsunto());
        mensaje.setCorreoDestinatario(dto.getCorreoDestinatario());
        mensaje.setCuerpoCorreo(dto.getCuerpoCorreo());
        mensaje.setFechaEnvio(dto.getFechaEnvio() != null ? dto.getFechaEnvio() : LocalDate.now());

        Mensajeria mensajeGuardado = mensajeriaService.guardarMensaje(mensaje);

        MensajeResponseDTO respuesta = new MensajeResponseDTO(
                mensajeGuardado.getId(),
                mensajeGuardado.getAsunto(),
                mensajeGuardado.getCorreoDestinatario(),
                mensajeGuardado.getCuerpoCorreo(),
                mensajeGuardado.getFechaEnvio()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RespuestaMensajeDTO("Mensaje guardado correctamente", respuesta));
    }

    // Este metodo permite buscar u obtener un mensaje por su id
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaMensajeDTO> obtenerMensajePorId(@PathVariable Long id) {
        Optional<Mensajeria> mensajeOpt = mensajeriaService.obtenerMensajePorId(id);

        if (mensajeOpt.isPresent()) {
            Mensajeria m = mensajeOpt.get();
            MensajeResponseDTO dto = new MensajeResponseDTO(
                    m.getId(),
                    m.getAsunto(),
                    m.getCorreoDestinatario(),
                    m.getCuerpoCorreo(),
                    m.getFechaEnvio()
            );

            return ResponseEntity.ok(new RespuestaMensajeDTO("Mensaje encontrado correctamente", dto));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaMensajeDTO("Mensaje no encontrado", null));
        }
    }

    // Este metodo permite buscar un mensaje por medio del correo del destinatario
    @GetMapping("/buscarPorCorreo")
    public ResponseEntity<RespuestaMensajeDTO> obtenerMensajePorCorreo(@RequestParam String correo) {
        List<MensajeResponseDTO> mensajes = mensajeriaRepository.findByCorreoDestinatario(correo)
                .stream()
                .map(m -> new MensajeResponseDTO(
                   m.getId(),
                   m.getAsunto(),
                   m.getCorreoDestinatario(),
                   m.getCuerpoCorreo(),
                   m.getFechaEnvio()))
                .toList();

        if (mensajes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new RespuestaMensajeDTO("No se encontraron mensajes para este correo", null));
        }

        return ResponseEntity.ok(new RespuestaMensajeDTO("Mensajes encontrados", mensajes));
    }

    // Este metodo permite encontrar un mensaje por medio de la fecha de envio
    @GetMapping("/buscarPorFecha")
    public ResponseEntity<RespuestaMensajeDTO> buscarPorFecha(@RequestParam String fecha) {
        LocalDate fechaEnvio = LocalDate.parse(fecha);
        List<MensajeResponseDTO> mensajes = mensajeriaRepository.findByFechaEnvio(fechaEnvio)
                .stream()
                .map(m -> new MensajeResponseDTO(
                        m.getId(),
                        m.getAsunto(),
                        m.getCorreoDestinatario(),
                        m.getCuerpoCorreo(),
                        m.getFechaEnvio()))
                .toList();

        if (mensajes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaMensajeDTO("No hay mensajes encontrados para esta fecha", null));
        }

        return ResponseEntity.ok(new RespuestaMensajeDTO("Mensajes encontrados", mensajes));
    }

    // Este metodo lista todos los mensajes
    @GetMapping
    public ResponseEntity<RespuestaMensajeDTO> listarMensajes() {
        List<MensajeResponseDTO> lista = mensajeriaService.listarMensajes()
                .stream()
                .map(m -> new MensajeResponseDTO(
                        m.getId(),
                        m.getAsunto(),
                        m.getCorreoDestinatario(),
                        m.getCuerpoCorreo(),
                        m.getFechaEnvio()))
                .toList();

        return ResponseEntity.ok(new RespuestaMensajeDTO("Mensajes encontrados", lista));
    }

    // Este metodo permite actualizar los campos de un mensaje ya creado
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaMensajeDTO> actualizarMensaje(@PathVariable Long id, @Valid @RequestBody MensajeUpdateDTO dto) {

        return mensajeriaService.obtenerMensajePorId(id)
                .map(mensajeExistente -> {
                    mensajeExistente.setAsunto(dto.getAsunto());
                    mensajeExistente.setCorreoDestinatario(dto.getCorreoDestinatario());
                    mensajeExistente.setCuerpoCorreo(dto.getCuerpoCorreo());
                    mensajeExistente.setFechaEnvio(dto.getFechaEnvio() != null ? dto.getFechaEnvio() : mensajeExistente.getFechaEnvio());

                    Mensajeria actualizado = mensajeriaService.guardarMensaje(mensajeExistente);

                    MensajeResponseDTO responseDTO = new MensajeResponseDTO(
                            actualizado.getId(),
                            actualizado.getAsunto(),
                            actualizado.getCorreoDestinatario(),
                            actualizado.getCuerpoCorreo(),
                            actualizado.getFechaEnvio()
                    );

                    return ResponseEntity.ok(new RespuestaMensajeDTO("Mensaje actualizado correctamente", responseDTO));
                })
                .orElse(ResponseEntity.status(404)
                        .body(new RespuestaMensajeDTO("Mensaje no encontrado", null)));
    }

    // Este metodo permite borrar un mensaje por su id
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
