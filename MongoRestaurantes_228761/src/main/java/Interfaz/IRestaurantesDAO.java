package Interfaz;

import Negocio.Restaurante;
import java.util.List;

public interface IRestaurantesDAO {
    boolean agregar(Restaurante restaurante);
    boolean actualizar(Restaurante restaurante);
    boolean eliminar(String id);
    Restaurante consultar(String id);
    List<Restaurante> consultarTodos();
    List<Restaurante> consultarPorCategoria(String categoria);
    boolean agregarCategoria(String nombreRestaurante, String categoria);
}