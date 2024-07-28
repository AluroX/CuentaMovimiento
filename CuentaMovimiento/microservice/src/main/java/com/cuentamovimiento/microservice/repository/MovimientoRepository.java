package com.cuentamovimiento.microservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cuentamovimiento.microservice.model.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByNumeroCuentaAndFechaBetween(String numeroCuenta, Date fechaInicio, Date fechaFin);
}
