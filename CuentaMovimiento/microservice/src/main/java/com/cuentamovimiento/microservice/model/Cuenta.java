package com.cuentamovimiento.microservice.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroCuenta;
    private String tipoCuenta;
    private Double saldoInicial;
    private String estado;
    private Long clienteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(id, cuenta.id) && Objects.equals(numeroCuenta, cuenta.numeroCuenta)
                && Objects.equals(tipoCuenta, cuenta.tipoCuenta) && Objects.equals(saldoInicial, cuenta.saldoInicial)
                && Objects.equals(estado, cuenta.estado) && Objects.equals(clienteId, cuenta.clienteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeroCuenta, tipoCuenta, saldoInicial, estado, clienteId);
    }

    @Override
    public String toString() {
        return "Cuenta{"
                + "id=" + id
                + ", numeroCuenta='" + numeroCuenta + '\''
                + ", tipoCuenta='" + tipoCuenta + '\''
                + ", saldoInicial=" + saldoInicial
                + ", estado='" + estado + '\''
                + ", clienteId=" + clienteId
                + '}';
    }
}
