package exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DataIntegrityException extends IllegalArgumentException {

    private String fieldName;
}
