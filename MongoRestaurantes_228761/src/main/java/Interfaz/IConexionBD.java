/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaz;

import com.mongodb.client.MongoDatabase;

/**
 *
 * @author gaspa
 */
public interface IConexionBD {
    MongoDatabase crearConexion();
}
