/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.facitec.mec.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import py.edu.facitec.mec.model.Cliente;
import py.edu.facitec.mec.util.ConexionManager;

/**
 *
 * @author Usuario
 */
public class ClienteDaoImpl implements ClienteDao {

    @Override
    public void guardar(Cliente cliente) {
    
            String sql = "insert into clientes (nombres, apellidos, direccion, credito) "
                    + "values ('"+cliente.getNombre()+"', '"+cliente.getApellido()+"', "
                        + "'"+cliente.getDireccion()+"', "+cliente.getCredito()+" )";
            
            System.out.println("SQL = "+ sql); // impresion por consola
            
            //conectar
            ConexionManager.conectar();
            
        try {
            //ejecutar sql
            ConexionManager.stm.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //desconectar
            ConexionManager.desconectar();
        
    }

    @Override
    public Cliente buscarClientePorCodigo(int codigo) {
       //de consultar
       //se recibe el codigo y hacer la consulta en sql
       String sql = "select nombres, apellidos, direccion from clientes where codigo = "+codigo+" ";
       
       Cliente cliente = null ; //declaracion pero sin referencia en la memoria
       ConexionManager.conectar(); // conectar a BD
       
       ResultSet  rs;
        try {
            rs = ConexionManager.stm.executeQuery(sql); // resultSet es una interfaz maneja los resultados, encargado de almacenar valores
        //si tenes un resultado abajo, se pregunta si hay un resultado
       if (rs.next()){ // si tuvo resutado el sql
           cliente = new Cliente();//instanciamos la clase Cliente
           cliente.setNombre(rs.getString("nombres")); // seteamos los valores a los atributos, se refiere a nombre de la columna
           cliente.setApellido(rs.getString("apellidos"));
           cliente.setDireccion(rs.getString("direccion"));
       }
       
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConexionManager.desconectar(); // cerramos la conexion
        
       return cliente; // retornamos el objeto cliente
       
       
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
       String sql = "update clientes set nombres='"+cliente.getNombre()+"', "
               + "apellidos='"+cliente.getApellido()+"', direccion='"+cliente.getDireccion()+"' "
               + "where codigo="+cliente.getCodigo()+" ";
       boolean resultado = false; // declarar e inicializar una variable 
        //Conectar
        ConexionManager.conectar();
        try {
            //Ejecutar el sql
        resultado = ConexionManager.stm.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        //desconectar
        ConexionManager.desconectar();
       
        return resultado;
    }

    @Override
    public void eliminar(int codigo) {
        //sql
        String sql = "delete from clientes where codigo ="+codigo+" ";
        
        ConexionManager.conectar();
        try {
        //Ejecutar el sql
        ConexionManager.stm.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        //desconectar
        ConexionManager.desconectar();
       
    }
    @Override //Collection Generic <> ->
    public List<Cliente> buscarClientePorFiltro(String filtro) {
        String sql = "select codigo, nombres,apellidos,direccion from clientes "
                + "where nombres LIKE '%"+filtro+"' or apellidos LIKE '"+filtro+"%' "
                + "or direccion LIKE '%"+filtro+"%' ";
       
        List<Cliente> lista = new ArrayList<>(); //creamos una lista
        //conectar
        ConexionManager.conectar();
        
        ResultSet rs; // este se usa para acceder o recorrer a la tabla  imaginaria
            try {
                rs = ConexionManager.stm.executeQuery(sql);
                
                if (rs.next()){  //si tenemos resultado
                    Cliente c = new Cliente(); // creamos un objeto
                    c.setCodigo(rs.getInt("codigo"));
                    c.setNombre(rs.getString("nombres"));
                    c.setApellido(rs.getString("apellidos"));
                    c.setDireccion(rs.getString("Direccion"));
            //agregar a la lista
            lista.add(c);
            }
                
            } catch (SQLException ex) {
                Logger.getLogger(ClienteDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            //desconectar
            ConexionManager.desconectar();
            return lista;
            
           
        
        
    }
       }
    
    



