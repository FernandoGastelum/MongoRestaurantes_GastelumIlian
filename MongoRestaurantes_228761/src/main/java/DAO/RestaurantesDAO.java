package DAO;

import Interfaz.IConexionBD;
import Interfaz.IRestaurantesDAO;
import Negocio.Restaurante;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

public class RestaurantesDAO implements IRestaurantesDAO {
    private final IConexionBD conexion;

    public RestaurantesDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    private MongoCollection<Document> getColeccion() {
        MongoDatabase db = conexion.crearConexion();
        return db.getCollection("restaurants");
    }

    @Override
    public boolean agregar(Restaurante restaurante) {
        try {
            Document doc = restaurante.toDocument();
            getColeccion().insertOne(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Restaurante restaurante) {
        try {
            Document filtro = new Document("_id", new ObjectId(restaurante.getId()));
            Document actualizacion = new Document("$set",
                    new Document("nombre", restaurante.getNombre())
                            .append("rating", restaurante.getRating())
                            .append("direccion", restaurante.getDireccion().toDocument()));
            getColeccion().updateOne(filtro, actualizacion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(String id) {
        try {
            getColeccion().deleteOne(new Document("_id", new ObjectId(id)));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Restaurante consultar(String id) {
        Document doc = getColeccion().find(new Document("_id", new ObjectId(id))).first();
        return doc != null ? Restaurante.fromDocument(doc) : null;
    }

    @Override
    public List<Restaurante> consultarTodos() {
        List<Restaurante> restaurantes = new ArrayList<>();
        for (Document doc : getColeccion().find()) {
            restaurantes.add(Restaurante.fromDocument(doc));
        }
        return restaurantes;
    }
    @Override
    public List<Restaurante> consultarPorCategoria(String categoria) {
        List<Restaurante> restaurantes = new ArrayList<>();
        MongoCollection<Document> coleccion = getColeccion();

        // Realizamos la consulta donde el campo "categorias" contiene la categoría especificada
        FindIterable<Document> resultados = coleccion.find(Filters.eq("categorias", categoria));

        for (Document doc : resultados) {
            restaurantes.add(Restaurante.fromDocument(doc));
        }
        return restaurantes;
    }
    @Override
    public boolean agregarCategoria(String nombreRestaurante, String categoria) {
        MongoCollection<Document> coleccion = getColeccion();

        // Utiliza el operador $addToSet para agregar la categoría solo si no está ya presente
        Document update = new Document("$addToSet", new Document("categorias", categoria));
        Document filtro = new Document("nombre", nombreRestaurante);

        UpdateResult resultado = coleccion.updateOne(filtro, update);

        return resultado.getModifiedCount() > 0;
    }
}