package EjercicioListaCompra.pojo;

import EjercicioListaCompra.interfaces.ProductoDAO;
import EjercicioListaCompra.model.Producto;
import EjercicioListaCompra.utils.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()){  //para hacer la conexión con la database.
            Query<Producto> query = session.createQuery("FROM Producto", Producto.class);  //ponemos "FROM y el nombre de la clase" y después se pone la clase a la que nos referimos.
            return query.getResultList();  //devuelve una lista genérica con todos los datos de la tabla. Si se utilzia ".list()" es lo mismo que ".getResultList()".
        } catch(Exception ex) {
            System.err.println(ex);
            return null;  //si salta una exception devuelve null que es como no devolver nada.
            //también pudes retornar un ArrayList vacío pero luego tienes que controlarlo cuando lo muestres.
        }
    }

    @Override
    public void restarXProducto(Producto producto) {
        /* usar x suministro: actualizará la base de datos reduciendo en x el suministo pasado por parámetro.
                                En caso de que la cantidad de suministros almacenados sea igual a x se deberá eliminar el suministro del inventario.
                                En caso de que x sea mayor al número de suministros se deberá notificar como un error y no realizar ninguna acción. */

        Transaction tx = null;  //inicializamos la transacción a null. La transacción solo se hace si se ejecuta ttodo el código, si falla algo no hace nada.

        try (Session session = HibernateUtil.getSessionFactory().openSession()){  //para hacer la conexión con la database.
            tx = session.beginTransaction();

            if (producto.getId() != 0){  //si el id es distinto de 0....
                session.merge(producto);  //actualizamos el producto con merge.

                if (producto.getCantidad() <= 0) {  //si la cantidad de ese producto es menor o igual a 0, lo eliminamos.
                    session.remove(producto);
                }
            }
            tx.commit();  //para completar la transacción.
        } catch (Exception ex) {
            if (tx != null) {  //si la transacción es distinta de null que significa que está abierta y que no se ha completado....
                tx.rollback();  //esto va a deshacer lo que ha hecho antes y va a volver a como estaba.
            }
            System.err.println("No se puede coger el producto \"" + producto.getNombre() + "\"");
        }
    }

    @Override
    public List<Producto> mostrarProducto(String nombre) {  //hay suministro: mostrar cuantos suministros nos quedan que contengan la cadena de texto pasada por parámetro.
        try (Session session = HibernateUtil.getSessionFactory().openSession()){  //para hacer la conexión con la database.
            Query<Producto> query = session.createQuery("FROM Producto WHERE nombre = :valorNombre", Producto.class);  //el ":value" va a ser único (no se puede repetir) y se va a cambiar por el valor que yo quiera poner.
            query.setParameter("valorNombre", nombre);  //cambiamos el ":valor" por el nombre que nos pasan. Hacemos un setParameter por cada valor que queramos cambiar.
            return query.getResultList();  //retorna la query de usuarios en forma de lista.
        } catch (Exception ex) {
            System.err.println("No se puede mostrar el producto con nombre \"" + nombre + "\"");
            return null;  //si salta una exception devuelve null que es como no devolver nada.
            //también puedes retornar un ArrayList vacío pero luego tienes que controlarlo cuando lo muestres.
        }
    }

    @Override
    public void addXProducto(Producto producto) {  //adquirir x suministro: insetará o actualizará el suministro con o en x unidades. Igual que cuando lo lee del fichero.
        Transaction tx = null;  //inicializamos la transacción a null. La transacción solo se hace si se ejecuta ttodo el código, si falla algo no hace nada.

        try (Session session = HibernateUtil.getSessionFactory().openSession()){  //para hacer la conexión con la database.
            tx = session.beginTransaction();
            session.persist(producto);  //esto es como hacer un insert para insertar el producto a la tabla.
            tx.commit();  //para completar la transacción.
        } catch (Exception ex) {
            if (tx != null) {  //si la transacción es distinta de null que significa que está abierta y que no se ha completado....
                tx.rollback();  //esto va a deshacer lo que ha hecho antes y va a volver a como estaba.
            }
            System.err.println("ERROR al añadir el producto \"" + producto.getNombre() + "\"");
        }
    }

    public void actualizarProducto(Producto producto) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            producto.setId(getIdPorNombre(producto));  //antes de actualizar hay que settear el mismo "id" del producto de la database porque sino lo toma como un producto diferente.
            session.merge(producto);  //esto es como hacer un update para actualizar el producto a la tabla.
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("ERROR actualizando el producto \"" + producto.getNombre() + "\"");
        }
    }

    @Override
    public int getIdPorNombre(Producto producto) {
        String nombre = producto.getNombre();

        try (Session session = HibernateUtil.getSessionFactory().openSession()){  //para hacer la conexión con la database.
            Query<Producto> query = session.createQuery("FROM Producto WHERE nombre = :valorNombre", Producto.class);  //el ":value" va a ser único (no se puede repetir) y se va a cambiar por el valor que yo quiera poner.
            query.setParameter("valorNombre", nombre);  //cambiamos el ":valor" por el nombre que nos pasan. Hacemos un setParameter por cada valor que queramos cambiar.
            return query.getFirstResult();  //retorna el primer resultado que sale que es un int.
        } catch (Exception ex) {
            System.err.println("ERROR al obtener el id del producto \"" + producto.getNombre() + "\"");
            return -1;  //si salta una exception devuelve -1 que es como no devolver nada.
        }
    }

    @Override
    public String getHelp(){
        return "---Listado de comandos---\n"
                + "\t listar -> te tiene que devolver un listado de todos los productos que hay.\n"
                + "\t usar -> para utilizar un producto en concreto.\n"
                + "\t hay -> te tiene que devolver un listado de los productos que empiezan por el nombre que has puesto. Si metes un espacio y un enter te tienen que salir todos los productos.\n"
                + "\t adquirir -> para añadir un producto en concreto.\n"
                + "\t exit -> para salir del programa.\n";
    }

    @Override
    public List<Producto> getProductByNameCriteria (Producto nombreProducto) {
        try (Session session = HibernateUtil.getSessionFactory().openSession();){  //para hacer la conexión con la database.
            CriteriaBuilder cb = session.getCriteriaBuilder();  //el CriteriaBuilder lo que nos permite es realizar modificaciones sobre el select.
            CriteriaQuery<Producto> query = cb.createQuery(Producto.class);  //se tiene que poner la clase general, si quieres que devuelva un int, se pone Integer o Long.
            Root<Producto> rootProduct = query.from(Producto.class);  //se utilza para ver de que clase sacamos la información.

            query.select(rootProduct.get("nombre")).where(cb.equal(rootProduct.get("nombre"), nombreProducto));  //se pone "nombre" que es el nombre de la variable de la clase Producto.

            /*
            select nombre, cantidad
            from Productos
            where nombre like %pa% and amont > 10;
             */
            //query.multiselect(rootProduct.get("nombre"), rootProduct.get("cantidad")).where(cb.and(cb.like(rootProduct.get("nombre"), "%" + "%"), cb.greaterThan(rootProduct.get("cantidad"), 10))).groupBy(rootProduct.get("nombre"));

            return session.createQuery(query).getResultList();
        } catch (Exception ex) {
            System.err.println(ex);
            return null;  //si salta una exception devuelve null que es como no devolver nada.
            //también pudes retornar un ArrayList vacío pero luego tienes que controlarlo cuando lo muestres.
        }
    }


//    @Override
//    public void exitProgram() {  //salir: finaliza el programa.
//        System.exit(0);
//    }

}
