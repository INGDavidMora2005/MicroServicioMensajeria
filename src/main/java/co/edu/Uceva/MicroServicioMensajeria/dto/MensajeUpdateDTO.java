package co.edu.Uceva.MicroServicioMensajeria.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MensajeUpdateDTO {

    @NotBlank(message = "El asunto no puede estar vacio")
    private String asunto;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Correo no valido")
    private String correoDestinatario;

    @NotBlank(message = "El cuerpo del mensaje no puede estar vacio")
    private String cuerpoCorreo;

    private LocalDate fechaEnvio;
}
