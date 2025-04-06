package co.edu.Uceva.MicroServicioMensajeria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

// La clase representa un entidad JPA la cual va a mapear la tabla en la BD
@Entity
@Table(name = "mensajes") // El nombre se la tabla se va a llamar "mensajes"
@Getter // Con lombok se generan automaticamente los getters
@Setter // Con lombok se generan automaticamente los getters
@NoArgsConstructor

public class Mensajeria {

    @Id // El id es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // El id se autoincrementa
    private Long id;

    @Column(nullable = false) // No se permiten valores nulos para este campo
    @NotBlank(message = "El asunto no puede estar vacio")
    private String asunto;

    @Column(nullable = false) // No se permiten valores nulos para este campo
    @Email(message = "Correo no valido") // Esto nos permite validar un correo correcto
    @NotBlank(message = "El correo es obligatorio")
    private String correoDestinatario;

    @Column(nullable = false, columnDefinition = "TEXT") // Nos permite guardar un texto largo para el cuerpo del correo
    @NotBlank(message = "El cuerpo del correo no puede estar vacio")
    private String cuerpoCorreo;

    @Column(nullable = false) // No se permiten valores nulos para este campo
    private LocalDate fechaEnvio;
}
