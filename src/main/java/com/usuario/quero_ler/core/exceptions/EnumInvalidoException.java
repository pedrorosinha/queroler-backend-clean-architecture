package com.usuario.quero_ler.core.exceptions;

import java.util.Arrays;

public class EnumInvalidoException extends RuntimeException{
    public EnumInvalidoException(Class<?> enumClass) {
        super("Valor inválido. Valores permitidos: " +
                Arrays.toString(enumClass.getEnumConstants()));
    }
}
