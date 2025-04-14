package co.edu.Uceva.MicroServicioMensajeria.controller;

import co.edu.Uceva.MicroServicioMensajeria.dto.*;
import co.edu.Uceva.MicroServicioMensajeria.model.Mensajeria;
import co.edu.Uceva.MicroServicioMensajeria.repository.MensajeriaRepository;
import co.edu.Uceva.MicroServicioMensajeria.service.MensajeriaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/mensajeria-service")
public class MensajeriaController {

    private final MensajeriaService mensajeriaService;
    private final MensajeriaRepository mensajeriaRepository;

    public MensajeriaController(MensajeriaService mensajeriaService, MensajeriaRepository mensajeriaRepository) {
        this.mensajeriaService = mensajeriaService;
        this.mensajeriaRepository = mensajeriaRepository;
    }

    @PostMapping("/mensajerias")
    public ResponseEntity<RespuestaMensajeDTO> crearMensaje(@Valid @RequestBody MensajeRequestDTO dto) {
        Mensajeria mensaje = new Mensajeria();
        mensaje.setAsunto(dto.getAsunto());
        mensaje.setCorreoDestinatario(dto.getCorreoDestinatario());
        mensaje.setCuerpoCorreo(dto.getCuerpoCorreo());
        mensaje.setFechaEnvio(dto.getFechaEnvio() != null ? dto.getFechaEnvio() : LocalDate.now());

        if (dto.getAsunto().isEmpty() || dto.getCorreoDestinatario().isEmpty() || dto.getCuerpoCorreo().isEmpty()) {
            return ResponseEntity.badRequest().body(new RespuestaMensajeDTO("Todos los campos son obligatorios", null));
        }

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

    @GetMapping("/mensajerias")
    public ResponseEntity<RespuestaListaMensajesDTO> obtenerMensajes() {
        List<MensajeResponseDTO> lista = mensajeriaService.listarMensajes()
                .stream()
                .map(m -> new MensajeResponseDTO(
                        m.getId(),
                        m.getAsunto(),
                        m.getCorreoDestinatario(),
                        m.getCuerpoCorreo(),
                        m.getFechaEnvio()))
                .toList();

        return ResponseEntity.ok(new RespuestaListaMensajesDTO("Mensajes encontrados", lista));
    }

    @GetMapping("/mensajerias/{id}")
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

    @GetMapping("/mensajerias/page/{pagina}")
    public ResponseEntity<MensajePageResponseDTO> obtenerMensajesPaginados(@PathVariable int pagina) {
        Pageable pageable = PageRequest.of(pagina, 10, Sort.by("fechaEnvio"));
        var page = mensajeriaService.obtenerMensajesPaginados(pageable);

        List<MensajeResponseDTO> contenido = page.getContent().stream()
                .map(MensajeResponseDTO::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new MensajePageResponseDTO(
                contenido,
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber()
        ));
    }

    @DeleteMapping("/mensajerias")
    public ResponseEntity<RespuestaMensajeDTO> eliminarMensaje(@RequestParam Long id) {
        if (!mensajeriaRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaMensajeDTO("Mensaje no encontrado", null));
        }

        mensajeriaRepository.deleteById(id);
        return ResponseEntity.ok(new RespuestaMensajeDTO("Mensaje eliminado correctamente", null));
    }
}