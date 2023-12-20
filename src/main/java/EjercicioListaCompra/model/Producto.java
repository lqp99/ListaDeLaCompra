package EjercicioListaCompra.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Producto {
    @Id  //siempre tiene que estar encima del atributo que quieras que sea el id (Primary Key).
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //para que se auto incremente solo.
    @Column(name = "id")  //para que la columna de la tabla se llame como le digas.
    private int id;

    @Column(name = "cantidad")  //para que la columna de la tabla se llame como le digas.
    private String cantidad;

    @Column(name = "nombre")  //para que la columna de la tabla se llame como le digas.
    private String nombre;


    //constructor
    public Producto() {
    }

    public Producto(String cantidad, String nombre) {
        this.cantidad = cantidad;
        this.nombre = nombre;
    }

    //toString
    @Override
    public String toString() {
        return "Producto{" +
                "cantidad='" + cantidad + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    //getters and setters
    public int getId() {
        return id;
    }
    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
