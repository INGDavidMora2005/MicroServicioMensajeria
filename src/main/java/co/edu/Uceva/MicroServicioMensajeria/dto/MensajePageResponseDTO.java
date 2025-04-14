package co.edu.Uceva.MicroServicioMensajeria.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MensajePageResponseDTO {
    private List<MensajeResponseDTO> contenido;
    private long totalElementos;
    private int totalPaginas;
    private int paginaActual;

    public MensajePageResponseDTO(List<MensajeResponseDTO> contenido, long totalElementos, int totalPaginas, int paginaActual) {
        this.contenido = contenido;
        this.totalElementos = totalElementos;
        this.totalPaginas = totalPaginas;
        this.paginaActual = paginaActual;
    }
}
