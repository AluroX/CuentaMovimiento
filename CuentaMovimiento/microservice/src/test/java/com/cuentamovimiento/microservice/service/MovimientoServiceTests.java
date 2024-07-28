package com.cuentamovimiento.microservice.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.cuentamovimiento.microservice.exception.SaldoException;
import com.cuentamovimiento.microservice.model.Cuenta;
import com.cuentamovimiento.microservice.model.Movimiento;
import com.cuentamovimiento.microservice.repository.CuentaRepository;
import com.cuentamovimiento.microservice.repository.MovimientoRepository;

public class MovimientoServiceTests {

    @InjectMocks
    private MovimientoService movimientoService;

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSaveMovimientoWithSufficientBalance() {
        Movimiento movimiento = new Movimiento();
        movimiento.setNumeroCuenta("123");
        movimiento.setValor(100.0);

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("123");
        cuenta.setSaldoInicial(200.0);

        when(cuentaRepository.findByNumeroCuenta("123")).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.save(any(Movimiento.class))).thenReturn(movimiento);

        Movimiento savedMovimiento = movimientoService.save(movimiento);

        assertNotNull(savedMovimiento);
        assertEquals(300.0, cuenta.getSaldoInicial());
        verify(cuentaRepository, times(1)).save(cuenta);
        verify(movimientoRepository, times(1)).save(movimiento);
    }

    @Test
    public void shouldnotSaveMovimientoWithInsufficientBalance() {
        Movimiento movimiento = new Movimiento();
        movimiento.setNumeroCuenta("123");
        movimiento.setValor(-300.0);

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("123");
        cuenta.setSaldoInicial(200.0);

        when(cuentaRepository.findByNumeroCuenta("123")).thenReturn(Optional.of(cuenta));

        SaldoException exception = assertThrows(SaldoException.class, () -> {
            movimientoService.save(movimiento);
        });

        assertEquals("Saldo no disponible", exception.getMessage()); 
    }

    @Test
    public void shouldDeleteMovimiento() {
        doNothing().when(movimientoRepository).deleteById(1L);

        movimientoService.delete(1L);

        verify(movimientoRepository, times(1)).deleteById(1L);
    }
}
