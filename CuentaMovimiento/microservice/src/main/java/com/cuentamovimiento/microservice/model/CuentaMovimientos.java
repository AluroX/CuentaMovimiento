package com.cuentamovimiento.microservice.model;

import java.util.List;

public class CuentaMovimientos {

    private Cuenta cuenta;
    private List<Movimiento> movimientos;

    public CuentaMovimientos(Cuenta cuenta, List<Movimiento> movimientos) {
        this.cuenta = cuenta;
        this.movimientos = movimientos;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }
}
