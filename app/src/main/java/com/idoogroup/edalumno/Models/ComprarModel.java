package com.idoogroup.edalumno.Models;

public class ComprarModel {

    public String amount;
    public String product;
    public int cantidad;

    public ComprarModel() {
    }

    public ComprarModel(String amount, String product, int cantidad) {
        this.amount = amount;
        this.product = product;
        this.cantidad = cantidad;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
