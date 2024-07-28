package com.cuentamovimiento.microservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cuentamovimiento.microservice.model.Cuenta;
import com.cuentamovimiento.microservice.repository.CuentaRepository;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    public Optional<Cuenta> findCuentaById(Long id) {
        return cuentaRepository.findById(id);
    }

    public Cuenta save(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public Cuenta update(String id, Cuenta cuenta) {
        cuenta.setNumeroCuenta(id);
        return cuentaRepository.save(cuenta);
    }

    public void delete(Long id) {
        cuentaRepository.deleteById(id);
    }

    public List<Cuenta> findByClienteId(Long clienteId) {
        return cuentaRepository.findByClienteId(clienteId);
    }
}
