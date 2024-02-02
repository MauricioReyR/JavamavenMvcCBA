package com.myinvntario.javamavenmvccba;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
/**
 *
 * @author Oscar Mauricio Rey
 */
public class CConexion {
    
    Connection conectar = null;
        String username = "root";
        String password = "admin";
        String bd = "calzadosBumerangA";
        String ip = "localhost";
        String puerto = "3306";
        
        String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
        
        public Connection EstablecerConexion(){
            
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conectar = DriverManager.getConnection(cadena,username,password);
                //para que no se muestre el mensaje a todo tiempo.
                //JOptionPane.showMessageDialog(null, "Conexion realizada con Exito");
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"error al conectar la base de datos" + e.toString());
            }
            return conectar;
        }   
}
