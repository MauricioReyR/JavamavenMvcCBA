package com.myinvntario.javamavenmvccba;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
/**
 *
 * @author Oscar Mauricio Rey
 */
public class CProductos {
    
    //Creacion de variables
    private String referencia;
    private String nombre;
    private String modelo;
    private String talla;
    private String material;
    private String color;
    private Double PrecioCompra;
    private Double PrecioVenta;

    //Se deja un constructor vacio
    public String getReferencia() {
        return referencia;
    }
    //se grenera un constructos con todas las variables
    
    //Se generan automaticamente los Getter An d Setter
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getPrecioCompra() {
        return PrecioCompra;
    }

    public void setPrecioCompra(Double PrecioCompra) {
        this.PrecioCompra = PrecioCompra;
    }

    public Double getPrecioVenta() {
        return PrecioVenta;
    }

    public void setPrecioVenta(Double PrecioVenta) {
        this.PrecioVenta = PrecioVenta;
    }
    //se hace el metodo para insertar y se establecen los parametros de las variables.
    public void InsertarProductos(JTextField paramReferencia,JTextField paramNombre,JTextField paramModelo,
                                  JTextField paramTalla,JTextField paramMaterial, JTextField paramColor,
                                  JTextField paramPrecioCompra, JTextField paramPrecioVenta){      
        
        //se incorporan los valores de los valores recibidos 
        //por el formulario
        setReferencia(paramReferencia.getText());
        setNombre(paramNombre.getText());
        setModelo(paramModelo.getText());
        setTalla(paramTalla.getText());
        setMaterial(paramMaterial.getText());
        setColor(paramColor.getText());
        //se hacer el Cast de variables, se reciben tipo text y se hacen de tipo Dobles
        setPrecioCompra(Double.valueOf(paramPrecioCompra.getText()));
        setPrecioVenta(Double.valueOf(paramPrecioVenta.getText()));
        
        //se crea un objeto del ipo conexion para acceder a la base de datos
        CConexion objetoCConexion = new CConexion();
        
        //Se realizala consulta SQL para insertar los datos en el formulario
        //se dejan los valores de Values con? por que son parametros
        
        String consulta ="INSERT INTO productos (referencia,nombre,modelo,talla,material,color,precioCompra,precioVenta)"
                + "VALUES (?,?,?,?,?,?,?,?)";
        System.out.println(consulta);
        //se hace un try Catch para 
        
        try {
            //se enlaza la coneccion con la consulta
            CallableStatement cs = objetoCConexion.EstablecerConexion().prepareCall(consulta);
            
            //se llama las variables y se incorporan los parametros
            cs.setString(1,getReferencia());
            cs.setString(2,getNombre());
            cs.setString(3,getModelo());
            cs.setString(4,getTalla());
            cs.setString(5,getMaterial());
            cs.setString(6,getColor());
            cs.setDouble(7,getPrecioCompra());
            cs.setDouble(8,getPrecioVenta());
            //se ejecuta el codigo
            
            System.out.println(cs);
            cs.execute();
            
                    
            JOptionPane.showMessageDialog(null, "Se inserto Correctamente el producto");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "NO se inserto Correctamente el producto: " +e.toString());
        }
    }
    //secrea el metodo para mostrrProductos
    public void MostrarProductos(JTable paramTablaTotalProductos){
    
        //se prepara la conexion
        CConexion objetoConexion = new CConexion();
        
        //se crea un modelo para la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        
        //se ordena la tabla alfanumeriamente
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        
        paramTablaTotalProductos.setRowSorter(OrdenarTabla);
        
        
        String sql="";
        
        modelo.addColumn("referencia");
        modelo.addColumn("nombre");
        modelo.addColumn("modelo");
        modelo.addColumn("talla");
        modelo.addColumn("material");
        modelo.addColumn("color");
        modelo.addColumn("precioCompra");
        modelo.addColumn("PrecioVenta");
        
        paramTablaTotalProductos.setModel(modelo);
        
        sql = "SELECT * FROM productos";
        
        String[] datos = new String[8];
        Statement st;
        
        try {
            st = objetoConexion.EstablecerConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            //recorrer la tabla
            while (rs.next()) {
                
                datos[0]= rs.getString(1);
                datos[1]= rs.getString(2);
                datos[2]= rs.getString(3);
                datos[3]= rs.getString(4);
                datos[4]= rs.getString(5);
                datos[5]= rs.getString(6);
                datos[6]= rs.getString(7);
                datos[7]= rs.getString(8);   
                
                modelo.addRow(datos);
            }
            paramTablaTotalProductos.setModel(modelo);                  
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pueden mostrar los registros de la tabla: " +e.toString());
            
        }      
    }
    
    public void SeleccionarProductos(JTable paramTablaProductos, JTextField paramReferencia, JTextField paramNombre,
                                    JTextField paramModelo,JTextField paramTalla,JTextField paramMaterial,
                                    JTextField paramColor,JTextField paramPrecioCompra,JTextField paramPrecioVenta){
        
        try {
            int fila = paramTablaProductos.getSelectedRow();
            
            if (fila >= 0){
                
                paramReferencia.setText((String) (paramTablaProductos.getValueAt(fila, 0).toString()));
                paramNombre.setText((String) (paramTablaProductos.getValueAt(fila, 1).toString()));
                paramModelo.setText((String) (paramTablaProductos.getValueAt(fila, 2).toString()));
                paramTalla.setText((String) (paramTablaProductos.getValueAt(fila, 3).toString()));
                paramMaterial.setText((String) (paramTablaProductos.getValueAt(fila, 4).toString()));
                paramColor.setText((String) (paramTablaProductos.getValueAt(fila, 5).toString()));
                paramPrecioCompra.setText((String) (paramTablaProductos.getValueAt(fila, 6).toString()));
                paramPrecioVenta.setText((String) (paramTablaProductos.getValueAt(fila, 7).toString()));
            }else{
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error e seleccion, error : " +e.toString());
        }
        //este se coloca para solucionar un error con el metodo ModificarProductos 
        //el cual no cargaba los cambios hechos en la tabla.
        //se deshabilita el cambio de referencia en el formulario.
        paramReferencia.setEditable(false);
    
    }
 
   public void ModificarProductos(JTextField paramReferencia,JTextField paramNombre,JTextField paramModelo,
                                  JTextField paramTalla,JTextField paramMaterial, JTextField paramColor,
                                  JTextField paramPrecioCompra, JTextField paramPrecioVenta){      
        
        //se incorporan los valores de los valores recibidos 
        //por el formulario 
        
        setReferencia(paramReferencia.getText());
        setNombre(paramNombre.getText());
        setModelo(paramModelo.getText());
        setTalla(paramTalla.getText());
        setMaterial(paramMaterial.getText());
        setColor(paramColor.getText());
        //se hacer el Cast de variables, se reciben tipo text y se hacen de tipo Dobles
        setPrecioCompra(Double.valueOf(paramPrecioCompra.getText()));
        setPrecioVenta(Double.valueOf(paramPrecioVenta.getText()));
        
        //se crea un objeto del ipo conexion para acceder a la base de datos
        CConexion objetoCConexion = new CConexion();
        
        //Se realizala consulta SQL para insertar los datos en el formulario
        //se dejan los valores de Values con? por que son parametros
        
        //String consulta ="UPDATE productos SET (referencia,nombre,modelo,talla,material,color,precioCompra,precioVenta)"
          //      + "VALUES (?,?,?,?,?,?,?,?)" +" WHERE productos.referencia = ?";
        //se hace un try Catch para
        
        String consulta = "UPDATE productos SET productos.referencia = ?,productos.nombre = ?,productos.modelo = ?, productos.talla =?,productos.material = ?,productos.color =?, productos.precioCompra = ?,productos.precioVenta = ? WHERE productos.referencia = ?";
 
        System.out.println(consulta);
        
        try {
            //se enlaza la coneccion con la consulta
            CallableStatement cs = objetoCConexion.EstablecerConexion().prepareCall(consulta);
            
            //se llama las variables y se incorporan los parametros
            cs.setString(1,getReferencia());
            cs.setString(2, getNombre());
            cs.setString(3,getModelo());
            cs.setString(4,getTalla());
            cs.setString(5,getMaterial());
            cs.setString(6,getColor());
            cs.setDouble(7,getPrecioCompra());
            cs.setDouble(8,getPrecioVenta());
            cs.setString(9,getReferencia());
            //se ejecuta el codigo
            System.out.println(cs);
            cs.execute();
                    
            JOptionPane.showMessageDialog(null, "Se inserto Correctamente el producto");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "NO se inserto Correctamente el producto: " +e.toString());
        }
    }
    public void EliminarProductos(JTextField paramReferencia){
        
        setReferencia(paramReferencia.getText());
        
        CConexion objetoConexion = new CConexion();
        
        String consulta = "DELETE FROM productos WHERE productos.referencia =?;";
        
        try {
            
             CallableStatement cs = objetoConexion.EstablecerConexion().prepareCall(consulta);
             cs.setString(1,getReferencia());
             cs.execute();
             
             JOptionPane.showMessageDialog(null, "Se elimino correctamente el Producto");
             
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se elimino correctamente el producto, error : +e");
        }
        
        
        
    }

    void LimpiarFormulario(JTextField txtReferencia, JTextField txtNombre, JTextField txtModelo, JTextField txtTalla, JTextField txtMaterial, JTextField txtColor, JTextField txtPrecioC, JTextField txtPrecioV) {
       txtReferencia.setText("");
       txtNombre.setText("");
       txtModelo.setText("");
       txtTalla.setText("");
       txtMaterial.setText("");
       txtColor.setText("");
       txtPrecioC.setText("");
       txtPrecioV.setText("");
    }
    
}
        
    


