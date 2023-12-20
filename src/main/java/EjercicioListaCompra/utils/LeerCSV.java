package EjercicioListaCompra.utils;

import EjercicioListaCompra.model.Producto;
import EjercicioListaCompra.pojo.ProductoPojo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class LeerCSV {
//    .toUpperCase

    File f;

    //constructor
    public LeerCSV(File f) {
        this.f = f;
    }


    public void insertarDatosDelCSV() {
        String linea = "";
        Producto p;

        try (BufferedReader br = new BufferedReader(new FileReader(this.f));){
            br.readLine();  //para quitarme la cabecera del CSV.
            while ((linea = br.readLine()) != null) {  //mientras que haya datos después...
                String replaceAll = linea.replaceAll(";", "").toLowerCase(); //para quitar todos los ";" que aparezcan en la línea y convertimos ttodo en Minúsculas.

                String[] separarDatos = replaceAll.split(";");

                p = new Producto(Integer.parseInt(separarDatos[0]), separarDatos[1]);

                ProductoPojo pp = new ProductoPojo();
                pp.addXProducto(p);
            }
        } catch (Exception ex) {
            System.err.println("ERROR al leer el fichero CSV: " + ex);
        }
    }

}
