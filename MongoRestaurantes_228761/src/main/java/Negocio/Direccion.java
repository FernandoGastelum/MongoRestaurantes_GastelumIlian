package Negocio;

import org.bson.Document;

public class Direccion {
    private String calle;
    private String ciudad;
    private String codigoPostal;

    public Direccion(String calle, String ciudad, String codigoPostal) {
        this.calle = calle;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
    }

    public Document toDocument() {
        return new Document("calle", calle)
                .append("ciudad", ciudad)
                .append("codigoPostal", codigoPostal);
    }

    public static Direccion fromDocument(Document doc) {
        return new Direccion(
                doc.getString("calle"),
                doc.getString("ciudad"),
                doc.getString("codigoPostal")
        );
    }
}