package co.edu.Uceva.MicroServicioMensajeria.dto;

import co.edu.Uceva.MicroServicioMensajeria.model.Mensajeria;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MensajeResponseDTO {
    private Long id;
    private String asunto;
    private String correoDestinatario;
    private String cuerpoCorreo;
    private LocalDate fechaEnvio;

    public MensajeResponseDTO(Long id, String asunto, String correoDestinatario, String cuerpoCorreo, LocalDate fechaEnvio) {
        this.id = id;
        this.asunto = asunto;
        this.correoDestinatario = correoDestinatario;
        this.cuerpoCorreo = cuerpoCorreo;
        this.fechaEnvio = fechaEnvio;
    }

    public static MensajeResponseDTO fromModel(Mensajeria mensaje) {
        return new MensajeResponseDTO(
                mensaje.getId(),
                mensaje.getAsunto(),
                mensaje.getCorreoDestinatario(),
                mensaje.getCuerpoCorreo(),
                mensaje.getFechaEnvio()
        );
    }

}
