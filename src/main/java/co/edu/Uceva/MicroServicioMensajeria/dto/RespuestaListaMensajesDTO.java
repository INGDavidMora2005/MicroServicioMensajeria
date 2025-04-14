package co.edu.Uceva.MicroServicioMensajeria.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RespuestaListaMensajesDTO {
    private String mensaje;
    private List<MensajeResponseDTO> datos;

    public RespuestaListaMensajesDTO(String mensaje, List<MensajeResponseDTO> datos) {
        this.mensaje = mensaje;
        this.datos = datos;
    }
}

