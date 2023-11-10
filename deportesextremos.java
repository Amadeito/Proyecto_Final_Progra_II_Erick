
package DEPORTES_EXTREMOS;


class deportesextremos {
    private String nombre;
    private double precio;
    private int cantidad;
    
   public deportesextremos(String nombre, double precio, int cantidad) {
    this.nombre = nombre;
    this.precio = precio;
    this.cantidad = cantidad;
}

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }
    public int getCantidad() {
    return cantidad;
}
}