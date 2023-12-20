package EjercicioListaCompra.interfaces;

import EjercicioListaCompra.model.Producto;

import java.util.List;

public interface ProductoDAO {
    public List<Producto> listarTodosLosProductos();

    public void restarXProducto(Producto p);

    public List<Producto> mostrarProducto(String nombre);

    public void addXProducto(Producto p);

//    public void exitProgram();

}
