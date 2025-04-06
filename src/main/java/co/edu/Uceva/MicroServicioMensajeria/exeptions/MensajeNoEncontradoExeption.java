package co.edu.Uceva.MicroServicioMensajeria.exeptions;

public class MensajeNoEncontradoExeption extends RuntimeException {
    public MensajeNoEncontradoExeption(String mensaje) {
        super(mensaje);
    }
}
