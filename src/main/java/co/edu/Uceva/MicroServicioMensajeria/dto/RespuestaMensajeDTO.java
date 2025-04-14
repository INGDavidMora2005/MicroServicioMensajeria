package co.edu.Uceva.MicroServicioMensajeria.dto;

/* Esta clase mejora la respuesta del microservicio cuando se ejecuta una accion
   es decir, responde de manera amigable y permite saber si la peticion fue exitosa o no. */

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RespuestaMensajeDTO {
    private String mensaje;
    private MensajeResponseDTO datos;

    public RespuestaMensajeDTO(String mensaje, MensajeResponseDTO datos) {
        this.mensaje = mensaje;
        this.datos = datos;
    }

    public RespuestaMensajeDTO(String mensaje) {
        this.mensaje = mensaje;
        this.datos = null;
    }
}

