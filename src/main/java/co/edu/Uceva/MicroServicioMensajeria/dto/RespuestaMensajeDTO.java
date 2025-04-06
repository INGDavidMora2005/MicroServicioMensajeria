package co.edu.Uceva.MicroServicioMensajeria.dto;

/* Esta clase mejora la respuesta del microservicio cuando se ejecuta una accion
   es decir, responde de manera amigable y permite saber si la peticion fue exitosa o no. */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespuestaMensajeDTO {

    private String mensaje;
    private Object datos;

    public RespuestaMensajeDTO() {
    }

    public RespuestaMensajeDTO(String mensaje, Object datos) {
        this.mensaje = mensaje;
        this.datos = datos;
    }
}
