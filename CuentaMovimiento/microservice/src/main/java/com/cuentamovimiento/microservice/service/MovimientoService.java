package com.cuentamovimiento.microservice.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cuentamovimiento.microservice.exception.SaldoException;
import com.cuentamovimiento.microservice.model.Cuenta;
import com.cuentamovimiento.microservice.model.Movimiento;
import com.cuentamovimiento.microservice.repository.CuentaRepository;
import com.cuentamovimiento.microservice.repository.MovimientoRepository;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;
    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private MovimientoSender movimientoSender;

    public List<Movimiento> findAll() {
        return movimientoRepository.findAll();
    }

    public Optional<Movimiento> findMovimientoById(Long id) {
        return movimientoRepository.findById(id);
    }

    public Movimiento save(Movimiento movimiento) {
        Optional<Cuenta> cuentaOption = cuentaRepository.findByNumeroCuenta(movimiento.getNumeroCuenta());
        if (!cuentaOption.isPresent()) {
            throw new RuntimeException("Cuenta no encontrada");
        }

        Cuenta cuenta = cuentaOption.get();
        double nuevoSaldo = cuenta.getSaldoInicial() + movimiento.getValor();

        if (nuevoSaldo < 0) {
            throw new SaldoException("Saldo no disponible");
        }

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        movimiento.setSaldo(nuevoSaldo);
        return movimientoRepository.save(movimiento);
    }

    //Envia mensaje de RabbitMq
    public Movimiento createMovimiento(Movimiento movimiento) {
        Movimiento savedMovimiento = movimientoRepository.save(movimiento);
        movimientoSender.sendMovimiento("Movimiento creado: " + savedMovimiento.toString());
        return savedMovimiento;
    }

    public void delete(Long id) {
        movimientoRepository.deleteById(id);
    }

    public List<Movimiento> findByNumeroCuentaAndFechaBetween(String numeroCuenta, Date fechaInicio, Date fechaFin) {
        return movimientoRepository.findByNumeroCuentaAndFechaBetween(numeroCuenta, fechaInicio, fechaFin);
    }
}
