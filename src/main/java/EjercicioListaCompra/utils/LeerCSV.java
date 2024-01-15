package EjercicioListaCompra.utils;

import EjercicioListaCompra.model.Producto;
import EjercicioListaCompra.pojo.ProductoPojo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LeerCSV {
    File f;

    //constructor
    public LeerCSV(File f) {
        this.f = f;
    }

    //metodos
    public ArrayList<Producto> insertarDatosDelCSV() {
        String linea = "";
        Producto p;
        ProductoPojo pp = new ProductoPojo();
//        List<Producto> productos = pp.listarTodosLosProductos();  //llamamos al método de listar todos los productos y nos guardamos la Lista que devuelve en una List.
//        List<Producto> productosNuevos = null;
        ArrayList<Producto> productosDelCSV = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(this.f));) {
            br.readLine();  //para quitar la cabecera del CSV.
            while ((linea = br.readLine()) != null) {  //mientras que haya datos en la linea siguiente...
                String replaceAll = linea.replaceAll(";", ",").toLowerCase(); //para quitar todos los ";" que aparezcan en la línea y convertimos ttodo en Minúsculas.
                String[] separarDatos = replaceAll.split(",");

                System.out.println(separarDatos[0] + " " + separarDatos[1]);

                p = new Producto(Integer.parseInt(separarDatos[0]), separarDatos[1]);
//                productosNuevos.add(p);
                productosDelCSV.add(p);

//                int id = pp.getIdPorNombre(p);
//                System.out.println("Id: " + id);
//
//                if (id != p.getId()) {
//                    pp.addXProducto(p);  //añadimos el poducto llamando al método.
//                    System.out.println("Añadiendo el producto \"" + p.getNombre() + "\" a la database");
//                } else {
//                    pp.actualizarProducto(p);  //actualizamos el producto llamando al método.
//                    System.out.println("Actualizando el producto \"" + p.getNombre() + "\"");
//                }

//                for (Producto producto : productos) {  //nos recorremos la lista de productos que ya existen en la database.
//                    if (p.getId() != producto.getId()){  //si el id del producto que queremos añadir es distinto que el producto que nos estamos recorriendo....
//                        pp.addXProducto(p);  //añadimos el poducto llamando al método.
//                        System.out.println("Añadiendo el producto \"" + p.getNombre() + "\" a la database");
//                    } else {  //si son iguales los id entonces....
//                        pp.actualizarProducto(p);  //actualizamos el producto llamando al método.
//                        System.out.println("Actualizando el producto \"" + p.getNombre() + "\"");
//                    }
//                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Fichero \""+ this.f +"\" no encontrado.");
        } catch (Exception ex) {
            System.err.println("ERROR: " + ex);
        }
        return productosDelCSV;
    }
}
