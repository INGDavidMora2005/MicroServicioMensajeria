package co.edu.Uceva.MicroServicioMensajeria.dto;

public class RespuestaMensajeDTO {

    private String mensaje;
    private Object datos;

    public RespuestaMensajeDTO() {
    }

    public RespuestaMensajeDTO(String mensaje, Object datos) {
        this.mensaje = mensaje;
        this.datos = datos;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Object getDatos() {
        return datos;
    }

    public void setDatos(Object datos) {
        this.datos = datos;
    }
}
