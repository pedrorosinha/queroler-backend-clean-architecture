package com.usuario.quero_ler.core.utils;

import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.usuario.quero_ler.core.exceptions.DataInvalidaException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.exc.InvalidFormatException;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatasTest {

        public record DtoDate(LocalDate data) {
        }

        public record DtoDateTime(LocalDateTime data) {
        }

        @Test
        void localDate_serializa_e_desserializa_no_formato_ddMMyyyy() {
                LocalDate date = LocalDate.of(2015, 6, 3);
                String expected = "03/06/2015";

                ObjectMapper mapper = JsonMapper.builder()
                                .withConfigOverride(LocalDate.class,
                                                cfg -> cfg.setFormat(Value.forPattern("dd/MM/yyyy")))
                                .build();

                String json = assertDoesNotThrow(() -> mapper.writeValueAsString(new DtoDate(date)));
                assertTrue(json.contains("\"data\":\"" + expected + "\""));

                DtoDate parsed = assertDoesNotThrow(
                                () -> mapper.readValue("{\"data\":\"" + expected + "\"}", DtoDate.class));
                assertEquals(date, parsed.data());
        }

        @Test
        void localDateTime_serializa_e_desserializa_no_formato_ddMMyyyy_HHmmss() {
                LocalDateTime now = LocalDateTime.of(2026, 5, 28, 14, 30, 15);
                String expected = "28/05/2026 14:30:15";

                ObjectMapper mapper = JsonMapper.builder()
                                .withConfigOverride(LocalDateTime.class,
                                                cfg -> cfg.setFormat(Value.forPattern("dd/MM/yyyy HH:mm:ss")))
                                .build();

                String json = assertDoesNotThrow(() -> mapper.writeValueAsString(new DtoDateTime(now)));
                assertTrue(json.contains("\"data\":\"" + expected + "\""));

                DtoDateTime parsed = assertDoesNotThrow(
                                () -> mapper.readValue("{\"data\":\"" + expected + "\"}", DtoDateTime.class));
                assertEquals(now, parsed.data());
        }

        @Test
        void quando_formato_for_invalido_deve_referenciar_DataInvalidaException() {
                ObjectMapper mapper = JsonMapper.builder()
                                .withConfigOverride(LocalDate.class,
                                                cfg -> cfg.setFormat(Value.forPattern("dd/MM/yyyy")))
                                .build();

                String invalido = "{\"data\":\"2015-06-03\"}";

                assertThrows(InvalidFormatException.class, () -> mapper.readValue(invalido, DtoDate.class));

                DataInvalidaException ex = new DataInvalidaException("Formato de data inválido");

                assertEquals("Formato de data inválido", ex.getMessage());
        }

}
