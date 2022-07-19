package org.santiagolopez.bean;


public class Proveedor {
   private int codigoProveedor;
   private String nitProveedor;
   private String servicioPrestado;
   private String telefonoProveedor;
   private String direccionProveedor;
   private double saldoFavor;
   private double saldoContra;
   private int codigoAdministracion;
   
   public Proveedor(){
       
   }
   
   public Proveedor(int codigoProveedor, String nitProveedor, String servicioPrestado, 
                    String telefonoProveedor, String direccionProveedor, double saldoFavor, 
                    double saldoContra, int codigoAdministracion){
       this.codigoProveedor= codigoProveedor;
       this.nitProveedor= nitProveedor;
       this.servicioPrestado = servicioPrestado;
       this.telefonoProveedor = telefonoProveedor;
       this.direccionProveedor = direccionProveedor;
       this.saldoFavor = saldoFavor;
       this.saldoContra = saldoContra;
       this.codigoAdministracion = codigoAdministracion;  
   }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getNitProveedor() {
        return nitProveedor;
    }

    public void setNitProveedor(String nitProveedor) {
        this.nitProveedor = nitProveedor;
    }

    public String getServicioPrestado() {
        return servicioPrestado;
    }

    public void setServicioPrestado(String servicioPrestado) {
        this.servicioPrestado = servicioPrestado;
    }

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public double getSaldoFavor() {
        return saldoFavor;
    }

    public void setSaldoFavor(double saldoFavor) {
        this.saldoFavor = saldoFavor;
    }

    public double getSaldoContra() {
        return saldoContra;
    }

    public void setSaldoContra(double saldoContra) {
        this.saldoContra = saldoContra;
    }

    public int getCodigoAdministracion() {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) {
        this.codigoAdministracion = codigoAdministracion;
    }

    @Override
    public String toString() {
        return getCodigoProveedor() +  " | "  +  getServicioPrestado() + " | " + getNitProveedor();
    }
   
   
    
}
