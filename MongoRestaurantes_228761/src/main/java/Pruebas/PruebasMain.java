/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas;

import DAO.RestaurantesDAO;
import Interfaz.IConexionBD;
import Interfaz.IRestaurantesDAO;
import Negocio.ConexionBD;
import Negocio.Direccion;
import Negocio.Restaurante;
import java.util.List;

/**
 *
 * @author gaspa
 */
public class PruebasMain {
    public static void main(String[] args) {
        IConexionBD conexionBD = new ConexionBD("mongodb://localhost:27017", "root", "12345");
        IRestaurantesDAO dao = new RestaurantesDAO(conexionBD);

        // Insertar tres restaurantes
        insertarRestaurantes(dao);

        // Consultar restaurantes con más de 4 estrellas de rating
        consultarRestaurantesConAltaRating(dao);

        // Consultar restaurantes que incluyan la categoría "pizza"
        consultarRestaurantesConCategoriaPizza(dao);

        // Consultar restaurantes que incluyan "sushi" en el nombre
        consultarRestaurantesConNombreSushi(dao);

        // Agregar una categoría extra al restaurante "Sushilito"
        agregarCategoriaASushilito(dao);

        // Eliminar un restaurante por su id
        eliminarRestaurantePorId(dao, "6732695cdbfc7e195d87d1b5");

        // Eliminar restaurantes con 4.5 estrellas o menos
        eliminarRestaurantesConBajaRating(dao);
    }
    public static void insertarRestaurantes(IRestaurantesDAO dao) {
        Direccion direccion1 = new Direccion("Calle Falsa 123", "Ciudad X", "99999");
        Direccion direccion2 = new Direccion("Avenida Siempre Viva 742", "Ciudad Y", "88888");
        Direccion direccion3 = new Direccion("Boulevard de la Luz 123", "Ciudad Z", "77777");

        Restaurante restaurante1 = new Restaurante(null, "Italian Delight", 4.7f, direccion1, List.of("italian", "pizza"));
        Restaurante restaurante2 = new Restaurante(null, "Sushi Zen", 4.5f, direccion2, List.of("sushi", "japanese"));
        Restaurante restaurante3 = new Restaurante(null, "Burger World", 4.3f, direccion3, List.of("burger", "fast food"));

        dao.agregar(restaurante1);
        dao.agregar(restaurante2);
        dao.agregar(restaurante3);
    }
    public static void consultarRestaurantesConAltaRating(IRestaurantesDAO dao) {
        System.out.println("Restaurantes con más de 4 estrellas:");
        List<Restaurante> altaRating = dao.consultarTodos().stream()
                .filter(r -> r.getRating() > 4)
                .toList();
        altaRating.forEach(r -> System.out.println(r.getNombre()));
    }
    public static void consultarRestaurantesConCategoriaPizza(IRestaurantesDAO dao) {
        System.out.println("\nRestaurantes con la categoría 'pizza':");
        List<Restaurante> restaurantesPizza = dao.consultarPorCategoria("pizza");
        restaurantesPizza.forEach(r -> System.out.println(r.getNombre()));
    }
    public static void consultarRestaurantesConNombreSushi(IRestaurantesDAO dao) {
        System.out.println("\nRestaurantes con 'sushi' en su nombre:");
        List<Restaurante> restaurantesSushi = dao.consultarTodos().stream()
                .filter(r -> r.getNombre().toLowerCase().contains("sushi"))
                .toList();
        restaurantesSushi.forEach(r -> System.out.println(r.getNombre()));
    }
    public static void agregarCategoriaASushilito(IRestaurantesDAO dao) {
        boolean resultado = dao.agregarCategoria("Sushilito", "seafood");

        if (resultado) {
            System.out.println("\nCategoría 'seafood' agregada a 'Sushilito'.");
        } else {
            System.out.println("\nNo se encontró 'Sushilito' o ya tiene la categoría 'seafood'.");
        }
    }
    public static void eliminarRestaurantePorId(IRestaurantesDAO dao, String id) {
        if (dao.eliminar(id)) {
            System.out.println("\nRestaurante eliminado con ID: " + id);
        } else {
            System.out.println("\nNo se pudo eliminar el restaurante con ID: " + id);
        }
    }
    public static void eliminarRestaurantesConBajaRating(IRestaurantesDAO dao) {
        List<Restaurante> bajaRating = dao.consultarTodos().stream()
                .filter(r -> r.getRating() <= 4.5)
                .toList();

        bajaRating.forEach(r -> dao.eliminar(r.getId()));
        System.out.println("\nRestaurantes con 3 estrellas o menos eliminados.");
    }
}

