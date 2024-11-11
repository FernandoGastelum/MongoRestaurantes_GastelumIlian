package Negocio;

import Interfaz.IConexionBD;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionBD implements IConexionBD {
    private final String cadenaConexion;
    private final String usuario;
    private final String contrasenia;

    public ConexionBD(String cadenaConexion, String usuario, String contrasenia) {
        this.cadenaConexion = cadenaConexion;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

    @Override
    public MongoDatabase crearConexion() {
        MongoClient cliente = MongoClients.create(cadenaConexion);
        return cliente.getDatabase("restaurantDB");
    }
}