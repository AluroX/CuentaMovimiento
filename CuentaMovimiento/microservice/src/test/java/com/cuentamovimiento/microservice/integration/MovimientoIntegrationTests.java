package com.cuentamovimiento.microservice.integration;


import java.text.SimpleDateFormat;

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
import com.cuentamovimiento.microservice.model.Movimiento;
import com.cuentamovimiento.microservice.repository.CuentaRepository;
import com.cuentamovimiento.microservice.repository.MovimientoRepository;


@SpringBootTest
@AutoConfigureMockMvc
public class MovimientoIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @BeforeEach
    public void setUp() {
        movimientoRepository.deleteAll();
        cuentaRepository.deleteAll();

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("12345");
        cuenta.setTipoCuenta("Ahorros");
        cuenta.setSaldoInicial(1000.0);
        cuenta.setEstado("activo");
        cuentaRepository.save(cuenta);
    }

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void testCreateMovimiento() throws Exception {
        String movimientoJson = "{\"fecha\":\"2023-01-01\",\"tipoMovimiento\":\"Deposito\",\"valor\":100.0,\"saldo\":1100.0,\"numeroCuenta\":\"12345\"}";

        mockMvc.perform(post("/movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(movimientoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoMovimiento", is("Deposito")))
                .andExpect(jsonPath("$.valor", is(100.0)))
                .andExpect(jsonPath("$.saldo", is(1100.0)))
                .andExpect(jsonPath("$.numeroCuenta", is("12345")));
    }

    @Test
    public void testGetMovimientoById() throws Exception {
        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(dateFormat.parse("2024-01-01"));
        movimiento.setTipoMovimiento("Deposito");
        movimiento.setValor(100.0);
        movimiento.setSaldo(1100.0);
        movimiento.setNumeroCuenta("12345");
        movimientoRepository.save(movimiento);

        mockMvc.perform(get("/movimientos/{id}", movimiento.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoMovimiento", is("Deposito")))
                .andExpect(jsonPath("$.valor", is(100.0)))
                .andExpect(jsonPath("$.saldo", is(1100.0)))
                .andExpect(jsonPath("$.numeroCuenta", is("12345")));
    }

    @Test
    public void testUpdateMovimiento() throws Exception {
        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(dateFormat.parse("2024-01-01"));
        movimiento.setTipoMovimiento("Deposito");
        movimiento.setValor(100.0);
        movimiento.setSaldo(1100.0);
        movimiento.setNumeroCuenta("12345");
        movimientoRepository.save(movimiento);

        String updatedMovimientoJson = "{\"fecha\":\"2024-01-01\",\"tipoMovimiento\":\"Retiro\",\"valor\":50.0,\"saldo\":1050.0,\"numeroCuenta\":\"12345\"}";

        mockMvc.perform(put("/movimientos/{id}", movimiento.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedMovimientoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoMovimiento", is("Retiro")))
                .andExpect(jsonPath("$.valor", is(50.0)))
                .andExpect(jsonPath("$.saldo", is(1050.0)))
                .andExpect(jsonPath("$.numeroCuenta", is("12345")));
    }

    @Test
    public void testDeleteMovimiento() throws Exception {
        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(dateFormat.parse("2024-01-01"));
        movimiento.setTipoMovimiento("Deposito");
        movimiento.setValor(100.0);
        movimiento.setSaldo(1100.0);
        movimiento.setNumeroCuenta("12345");
        movimientoRepository.save(movimiento);

        mockMvc.perform(delete("/movimientos/{id}", movimiento.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/movimientos/{id}", movimiento.getId()))
                .andExpect(status().isNotFound());
    }
}
