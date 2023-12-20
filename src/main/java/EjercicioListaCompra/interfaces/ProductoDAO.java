package EjercicioListaCompra.interfaces;

import EjercicioListaCompra.model.Producto;

public interface ProductoDAO {
    public void listarTodosLosProductos();

    public void restarXProducto(Producto p);

    public void mostrarProducto(String nombre);

    public void addXProducto(Producto p);

    public void exitProgram();
}
