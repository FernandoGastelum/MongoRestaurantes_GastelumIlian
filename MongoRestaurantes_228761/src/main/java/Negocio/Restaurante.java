/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author gaspa
 */
public class Restaurante {
    private String id;
    private String nombre;
    private float rating;
    private Direccion direccion;
    private List<String> categorias;

    public Restaurante(String id, String nombre, float rating, Direccion direccion, List<String> categorias) {
        this.id = id;
        this.nombre = nombre;
        this.rating = rating;
        this.direccion = direccion;
        this.categorias = categorias;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public float getRating() { return rating; }
    public Direccion getDireccion() { return direccion; }
    public List<String> getCategorias() { return categorias; }

    public static Restaurante fromDocument(Document doc) {
        return new Restaurante(
            doc.getObjectId("_id").toString(),
            doc.getString("nombre"),
            doc.getDouble("rating").floatValue(),
            Direccion.fromDocument((Document) doc.get("direccion")),
            doc.getList("categorias", String.class)
        );
    }

    public Document toDocument() {
        return new Document("_id", id != null ? new ObjectId(id) : new ObjectId())
                .append("nombre", nombre)
                .append("rating", rating)
                .append("direccion", direccion.toDocument())
                .append("categorias", categorias);
    }
}
