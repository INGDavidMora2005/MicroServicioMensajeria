package co.edu.Uceva.MicroServicioMensajeria.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MensajeRequestDTO {

    @NotBlank(message = "El asunto es obligatorio, No puede estar vacio.")
    @Size(max = 100, message = "El asunto no debe superar los 100 caracteres.")
    private String asunto;

    @NotBlank(message = "El correo del destinatario es obligatorio, No puede estar vacio.")
    @Email(message = "El correo debe tener un formato valido.")
    private String correoDestinatario;

    @NotBlank(message = "El cuerpo del correo es obligatorio, No puede estar vacio.")
    private String cuerpoCorreo;

    private LocalDate fechaEnvio;
}
