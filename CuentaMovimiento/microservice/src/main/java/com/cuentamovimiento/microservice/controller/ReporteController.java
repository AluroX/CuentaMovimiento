package com.cuentamovimiento.microservice.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cuentamovimiento.microservice.model.Cuenta;
import com.cuentamovimiento.microservice.model.CuentaMovimientos;
import com.cuentamovimiento.microservice.model.Movimiento;
import com.cuentamovimiento.microservice.service.CuentaService;
import com.cuentamovimiento.microservice.service.MovimientoService;

@RestController
public class ReporteController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping("/reportes")
    public ResponseEntity<?> getReporte(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin) {

        List<Cuenta> cuentas = cuentaService.findByClienteId(clienteId);
        if (cuentas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<CuentaMovimientos> reporteCuentas = cuentas.stream().map(cuenta -> {
            List<Movimiento> movimientos = movimientoService.findByNumeroCuentaAndFechaBetween(
                    cuenta.getNumeroCuenta(), fechaInicio, fechaFin);
            return new CuentaMovimientos(cuenta, movimientos);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(reporteCuentas);
    }

}
