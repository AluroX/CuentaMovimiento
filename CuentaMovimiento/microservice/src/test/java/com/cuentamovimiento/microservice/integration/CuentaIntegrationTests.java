package com.cuentamovimiento.microservice.integration;

import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cuentamovimiento.microservice.model.Cuenta;
import com.cuentamovimiento.microservice.repository.CuentaRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class CuentaIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CuentaRepository cuentaRepository;

    @BeforeEach
    public void setUp() {
        cuentaRepository.deleteAll();
    }

    @Test
    public void testCreateCuenta() throws Exception {
        String cuentaJson = "{\"numeroCuenta\":\"12345\",\"tipoCuenta\":\"Ahorros\",\"saldoInicial\":1000.0,\"estado\":\"activo\"}";

        mockMvc.perform(post("/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cuentaJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroCuenta", is("12345")))
                .andExpect(jsonPath("$.tipoCuenta", is("Ahorros")))
                .andExpect(jsonPath("$.saldoInicial", is(1000.0)))
                .andExpect(jsonPath("$.estado", is("activo")));
    }

    @Test
    public void testGetCuentaById() throws Exception {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("12345");
        cuenta.setTipoCuenta("Ahorros");
        cuenta.setSaldoInicial(1000.0);
        cuenta.setEstado("activo");
        cuentaRepository.save(cuenta);

        mockMvc.perform(get("/cuentas/{id}", cuenta.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCuenta", is("12345")))
                .andExpect(jsonPath("$.tipoCuenta", is("Ahorros")))
                .andExpect(jsonPath("$.saldoInicial", is(1000.0)))
                .andExpect(jsonPath("$.estado", is("activo")));
    }

    @Test
    public void testUpdateCuenta() throws Exception {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("12345");
        cuenta.setTipoCuenta("Ahorros");
        cuenta.setSaldoInicial(1000.0);
        cuenta.setEstado("activo");
        cuentaRepository.save(cuenta);

        String updatedCuentaJson = "{\"numeroCuenta\":\"12345\",\"tipoCuenta\":\"Corriente\",\"saldoInicial\":2000.0,\"estado\":\"inactivo\"}";

        mockMvc.perform(put("/cuentas/{id}", cuenta.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedCuentaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCuenta", is("12345")))
                .andExpect(jsonPath("$.tipoCuenta", is("Corriente")))
                .andExpect(jsonPath("$.saldoInicial", is(2000.0)))
                .andExpect(jsonPath("$.estado", is("inactivo")));
    }

    @Test
    public void testDeleteCuenta() throws Exception {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("12345");
        cuenta.setTipoCuenta("Ahorros");
        cuenta.setSaldoInicial(1000.0);
        cuenta.setEstado("activo");
        cuentaRepository.save(cuenta);

        mockMvc.perform(delete("/cuentas/{id}", cuenta.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/cuentas/{id}", cuenta.getId()))
                .andExpect(status().isNotFound());
    }
}
