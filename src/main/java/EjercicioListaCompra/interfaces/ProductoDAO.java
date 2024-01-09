package EjercicioListaCompra.interfaces;

import EjercicioListaCompra.model.Producto;

import java.util.List;

public interface ProductoDAO {
    public List<Producto> listarTodosLosProductos();

    public void restarXProducto(Producto producto);

    public List<Producto> mostrarProducto(String nombre);

    public void addXProducto(Producto producto);

    public void actualizarProducto(Producto producto);

    public int getIdPorNombre(Producto producto);

    public String getHelp();

//    public void exitProgram();

}
