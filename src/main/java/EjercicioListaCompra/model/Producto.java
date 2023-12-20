package EjercicioListaCompra.model;

import jakarta.persistence.*;

@Entity  //define que es una entidad dentro de una database.
@Table(name = "productos")  //para poner el nombre de la Tabla. Para que no se ponga como el nombre de la clase, se lo especificamos nosotros.
public class Producto {
    @Id  //siempre tiene que estar encima del atributo que quieras que sea el id (Primary Key).
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //para que se auto incremente solo.
    @Column(name = "id")  //para que la columna de la tabla se llame como le digas.
    private int id;

    @Column(name = "cantidad")  //para que la columna de la tabla se llame como le digas.
    private int cantidad;

    @Column(name = "nombre")  //para que la columna de la tabla se llame como le digas.
    private String nombre;


    //constructor
    public Producto(int cantidad, String nombre) {
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
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
