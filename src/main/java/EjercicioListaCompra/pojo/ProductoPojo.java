package EjercicioListaCompra.pojo;

import EjercicioListaCompra.interfaces.ProductoDAO;
import EjercicioListaCompra.model.Producto;
import EjercicioListaCompra.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProductoPojo implements ProductoDAO {

    //constructor
    public ProductoPojo() {
    }

    //metodos
    @Override
    public List<Producto> listarTodosLosProductos() {  //listar: mostrará todos los suministros que nos quedan.
        try (Session session = HibernateUtil.getSessionFactory().openSession();){  //para hacer la conexión con la database.
            Query<Producto> query = session.createQuery("FROM Producto", Producto.class);  //ponemos "FROM y el nombre de la case" y después se pone la clase a la que nos referimos.
            return query.getResultList();  //devuelve una lista genérica con todos los datos de la tabla. Si se utilzia ".list()" es lo mismo que ".getResultList()".
        } catch(Exception ex) {
            System.err.println(ex);
            return null;  //si salta una exception devuelve null que es como no devolver nada.
            //también pudes retornar un ArrayList vacío pero luego tienes que controlarlo cuando lo muestres.
        }
    }

    @Override
    public void restarXProducto(Producto p) {
        /* usar x suministro: actualizará la base de datos reduciendo en x el suministo pasado por parámetro. En caso de que la cantidad de
                                suministros almacenados sea igual a x se deberá eliminar el suministro del inventario. En caso de que x sea mayor al número de
                                suministros se deberá notificar como un error y no realizar ninguna acción. */

        Transaction tx = null;  //inicializamos la transacción a null. La transacción solo se hace si se ejecuta ttodo el código, si falla algo no hace nada.

        try (Session session = HibernateUtil.getSessionFactory().openSession();){  //para hacer la conexión con la database.
            tx = session.beginTransaction();

            if (p.getId() != 0){  //si el id es distinto de 0....
                session.merge(p);  //actualizamos el producto.

                if (p.getCantidad() <= 0) {  //si la cantidad de ese producto es menor o igual a 0, lo eliminamos.
                    session.remove(p);
                }
            }
            tx.commit();  //para completar la transacción.
        } catch (Exception ex) {
            if (tx != null) {  //si la transacción es distinta de null que significa que está abierta y que no se ha completado....
                tx.rollback();  //esto va a deshacer lo que ha hecho antes y va a volver a como estaba.
            }
            System.err.println(ex);
        }
    }

    @Override
    public List<Producto> mostrarProducto(String nombre) {  //hay suministro: mostrar cuantos suministros nos quedan que contengan la cadena de texto pasada por parámetro.
        try (Session session = HibernateUtil.getSessionFactory().openSession();){  //para hacer la conexión con la database.
            Query<Producto> query = session.createQuery("FROM Producto WHERE nombre = :valorNombre", Producto.class);  //el ":value" va a ser único (no se puede repetir) y se va a cambiar por el valor que yo quiera poner.
            query.setParameter("valorNombre", nombre);  //cambiamos el ":valor" por el nombre que nos pasan. Hacemos un setParameter por cada valor que queramos cambiar.
            return query.getResultList();  //retorna la query de usuarios en forma de lista.
        } catch (Exception ex) {
            System.err.println(ex);
            return null;  //si salta una exception devuelve null que es como no devolver nada.
            //también puedes retornar un ArrayList vacío pero luego tienes que controlarlo cuando lo muestres.
        }
    }

    @Override
    public void addXProducto(Producto p) {  //adquirir x suministro: insetará o actualizará el suministro con o en x unidades. Igual que cuando lo lee del fichero.
        Transaction tx = null;  //inicializamos la transacción a null. La transacción solo se hace si se ejecuta ttodo el código, si falla algo no hace nada.

        try (Session session = HibernateUtil.getSessionFactory().openSession();){  //para hacer la conexión con la database.
            tx = session.beginTransaction();
            session.persist(p);  //esto es como hacer un insert para insertar el producto a la tabla.
            tx.commit();  //para completar la transacción.
        } catch (Exception ex) {
            if (tx != null) {  //si la transacción es distinta de null que significa que está abierta y que no se ha completado....
                tx.rollback();  //esto va a deshacer lo que ha hecho antes y va a volver a como estaba.
            }
            System.err.println(ex);
        }
    }

//    @Override
//    public void exitProgram() {  //salir: finaliza el programa.
//        System.exit(0);
//    }

}
