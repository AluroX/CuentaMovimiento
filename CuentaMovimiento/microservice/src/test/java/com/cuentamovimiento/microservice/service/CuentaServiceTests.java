package com.cuentamovimiento.microservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.cuentamovimiento.microservice.model.Cuenta;
import com.cuentamovimiento.microservice.repository.CuentaRepository;

public class CuentaServiceTests {

    @InjectMocks
    private CuentaService cuentaService;

    @Mock
    private CuentaRepository cuentaRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldFindAllCuentas() {
        Cuenta cuenta1 = new Cuenta();
        cuenta1.setNumeroCuenta("123");
        Cuenta cuenta2 = new Cuenta();
        cuenta2.setNumeroCuenta("678");

        when(cuentaRepository.findAll()).thenReturn(Arrays.asList(cuenta1, cuenta2));

        List<Cuenta> cuentas = cuentaService.findAll();

        assertNotNull(cuentas);
        assertEquals(2, cuentas.size());
        verify(cuentaRepository, times(1)).findAll();
    }

    @Test
    public void shouldFindById() {
        Cuenta cuenta = new Cuenta();
        cuenta.setId(1L);
        cuenta.setNumeroCuenta("123");

        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));

        Optional<Cuenta> foundCuenta = cuentaService.findCuentaById(1L);

        assertTrue(foundCuenta.isPresent());
        assertEquals("123", foundCuenta.get().getNumeroCuenta());
        verify(cuentaRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldSaveCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("123");
        cuenta.setSaldoInicial(1000.0);

        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);

        Cuenta savedCuenta = cuentaService.save(cuenta);

        assertNotNull(savedCuenta);
        verify(cuentaRepository, times(1)).save(cuenta);
    }

    @Test
    public void shouldDeleteCuenta() {
        doNothing().when(cuentaRepository).deleteById(1L);

        cuentaService.delete(1L);

        verify(cuentaRepository, times(1)).deleteById(1L);
    }
}
