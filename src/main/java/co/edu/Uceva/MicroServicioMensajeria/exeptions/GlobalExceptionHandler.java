package co.edu.Uceva.MicroServicioMensajeria.exeptions;

import co.edu.Uceva.MicroServicioMensajeria.dto.RespuestaMensajeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// Le dice a spring que manejara exepciones de tipo rest
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja los errores de validacion de los datos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarErroresDeValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        // Recorre todos los errores encontrados en el proceso de validación
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField(); // Obtiene el nombre del campo que falló
            String mensaje = error.getDefaultMessage(); // Obtiene el mensaje de error
            errores.put(campo, mensaje); // Guarda el campo y el mensaje en el mapa
        });

        // Devuelve un ResponseEntity con los errores y un estado 400 (Bad Request)
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MensajeNoEncontradoExeption.class)
    public ResponseEntity<RespuestaMensajeDTO> manejarNoEncontrado(MensajeNoEncontradoExeption ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new RespuestaMensajeDTO(ex.getMessage(), null));
    }
}
