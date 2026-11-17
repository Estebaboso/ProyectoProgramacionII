package co.uniquindio.edu.co.cinemauq.Estructurales;

public class ProductoIndividual implements Producto {
    private String nombre;
    private int id;
    private double precio;
    private int stock;

    public ProductoIndividual(String nombre, int id, double precio, int stock) {
        this.nombre = nombre;
        this.id = id;
        this.precio = precio;
        setStock(stock);
    }


    public ProductoIndividual(String nombre, int id, double precio) {
        this(nombre, id, precio, 0);
    }

    @Override
    public double obtenerPrecio() { return precio; }

    @Override
    public int obtenerPuntos() { return 50; }

    public boolean descontarStock(int cantidad) {
        if (cantidad <= 0 || stock < cantidad) return false;
        stock -= cantidad;
        return true;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) {
        if (stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo.");
        this.stock = stock;
    }
}