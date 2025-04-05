package co.edu.Uceva.MicroServicioMensajeria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "mensajes")
@Getter
@Setter
@NoArgsConstructor

public class Mensajeria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El asunto no puede estar vacio")
    private String asunto;

    @Column(nullable = false)
    @Email(message = "Correo no valido")
    @NotBlank(message = "El correo es obligatorio")
    private String correoDestinatario;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "El cuerpo del correo no puede estar vacio")
    private String cuerpoCorreo;

    @Column(nullable = false)
    private LocalDate fechaEnvio;
}
