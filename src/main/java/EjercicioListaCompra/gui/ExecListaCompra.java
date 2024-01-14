/*
El presente trabajo tiene como objetivo verificar la correcta adquisición de las competencias iniciales relativas al uso de un ORM como Hibernate.
Para ello, es necesario cumplir los requisitos que se establecen en el siguiente enunciado y que serán validados respecto a la rúbrica adjunta. La entrega de este proyecto será tanto compartiendo un enlace al repositorio GitHub - donde se encuentren los ficheros del proyecto - a través del aula virtual, como subiendo el proyecto al aula. Ambas son necesarias para obtener la calificación máxima, en caso de solo realizar una de las dos solo se obtendrá la mitad de la calificación.
El trabajo puede ser realizado de forma individual o por parejas. En caso de ser por parejas se deberá notificar al profesor a través de un mensaje directo en el aula virtual o a través de un correo electrónico a ismael.perezroldan@educa.madrid.org.
En caso de hacerlo por parejas ambas partes deberán realizar commits en el proyecto, en caso de que solo una de las partes implicadas realice la mayoría de los commits, la calificación será proporcional.
El grupo que se forme será tanto para esta tarea como para la siguiente relativa al uso avanzado de ORM. NO SE PODRÁN MODIFICAR.

Enunciado
Dado el fichero adjunto con extensivo csv y separado por el carácter ";" con una lista de elementos
que han sido comprados y que tiene un formato tal que:
    1;rollo papel higiénico

Realiza la carga de dicha información haciendo uso de un ORM, en una tabla que satisfaga los requisitos de almacenamiento
y los requisitos de un ORM.

Es importante destacar que en caso de que se repita un elemento, no se tiene que volver a insertar, si no que se tiene que
actualizar incrementando el número de unidades de dicho elemento.
Por ejemplo:
    1;rollo papel higiénico <==== se realizará un insert

    7;rollo papel higiénico <==== se realizará un update

Al final se tendrá en la tabla que hay 8 unidades de papel higiénico.

Una vez realizada la carga de los datos se deberá acceder a ellos a través de palabras reservadas:
    listar: mostrará todos los suministros que nos quedan.
    usar x suministro: actualizará la base de datos reduciendo en x el suministo pasado por parámetro. En caso de que la cantidad de suministros almacenados sea igual a x se deberá eliminar el suministro del inventario. En caso de que x sea mayor al número de suministros se deberá notificar como un error y no realizar ninguna acción.
    hay suministro: mostrar cuantos suministros nos quedan que contengan la cadena de texto pasada por parámetro.
    adquirir x suministro: insetará o actualizará el suministro con o en x unidades. Igual que cuando lo lee del fichero.
    salir: finaliza el programa.
Todos el programa ignorará mayúsculas y minúsculas. Se deberá permitir la ejecución ilimitada del programa solo parando al escribir la palabra salir o parando el proceso de ejecución.
 */
package EjercicioListaCompra.gui;

import EjercicioListaCompra.model.Producto;
import EjercicioListaCompra.pojo.ProductoPojo;
import EjercicioListaCompra.utils.LeerCSV;

import java.io.File;
import java.util.Scanner;

public class ExecListaCompra {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        File f = new File("src/main/java/EjercicioListaCompra/ficheros/compra.csv");
        ProductoPojo pp = new ProductoPojo();
        LeerCSV leer = new LeerCSV(f);
        leer.insertarDatosDelCSV();
        Producto p = null;
        String comando = "";

        menu(sc, p, comando, pp);
    }

    public static void menu(Scanner sc, Producto p, String comando, ProductoPojo pp){
        int cantidad;
        String nombre;

        do {
            System.out.print("\nIntroducir comando: ");
            String txt = sc.nextLine();
            String[] trozos = txt.split(" ", 3);
            comando = trozos[0];

            if (comando.equalsIgnoreCase("listar")){
                pp.listarTodosLosProductos();
            } else if (comando.equalsIgnoreCase("usar")){
                cantidad = Integer.parseInt(trozos[1]);
                nombre = trozos[2];
                p = new Producto(cantidad, nombre);
                pp.restarXProducto(p);
            } else if (comando.equalsIgnoreCase("hay")) {
                nombre = trozos[1];
                pp.mostrarProducto(nombre);
            } else if (comando.equalsIgnoreCase("adquirir")) {
                cantidad = Integer.parseInt(trozos[1]);
                nombre = trozos[2];
                p = new Producto(cantidad, nombre);
                pp.addXProducto(p);
            } else if (comando.equalsIgnoreCase("help")) {
                System.out.println(pp.getHelp());
            }
        } while (!comando.equalsIgnoreCase("exit"));
        System.out.println("Saliendo del programa");
    }
}

/*
Pruebas para hacer:
    hay pollo
    adquirir 1 caldo pollo
 */