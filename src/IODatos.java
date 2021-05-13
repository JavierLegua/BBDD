/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author alumnodaw
 */


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class IODatos {
    
    private static final String RUTA = "jdbc:mysql://34.229.80.204:3306/javier";
    private static final String USU = "javier";
    private static final String PASS = "javier";
    
    public static void guardarBBDD(){
       
        
        try (Connection con = DriverManager.getConnection(RUTA, USU, PASS)){
            
            System.out.println("Conexion realizada");
            
            String sentencia = "INSERT INTO alumno VALUES('JAVIER', 18, null, null)";
            String sentencia1 = "INSERT INTO alumno VALUES('OSCAR', 18, null, null)";
            
            Statement st = con.createStatement();
            
            st.executeUpdate(sentencia);
            st.executeUpdate(sentencia1);
            
        } catch (SQLException ex) {
            Logger.getLogger(IODatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Conexion cerrada");
        
    }
    
    public static void consultarBBDD(){
        
        try (Connection con = DriverManager.getConnection(RUTA, USU, PASS);){
            
            String consulta = "Select * from alumno";
            
            Statement st = con.createStatement();
            
            ResultSet rs = st.executeQuery(consulta);
            
            while(rs.next()){
                System.out.println(rs.getString(1) + "--" + (rs.getInt("edad")));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(IODatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void insertarColumna(String columna){
        
        try (Connection con = DriverManager.getConnection(RUTA, USU, PASS);){
            
            String sentencia = "ALTER TABLE alumno ADD COLUMN " + columna + " varchar(20)";
            
            Statement st = con.createStatement();
            
            st.executeUpdate(sentencia);
            
        } catch (SQLException ex) {
            Logger.getLogger(IODatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public static ArrayList<Alumno> cargarDatosAlumno() {
        
        ArrayList<Alumno> vAlumnos= new ArrayList<>();
        
        try (Connection con = DriverManager.getConnection(RUTA, USU, PASS);){
            
                String sentencia = "select * from alumno";

                String nombre, direccion;
                int edad;
                
                Statement st = con.createStatement();
                
                ResultSet rs = st.executeQuery(sentencia);
                
                while(rs.next()){
                nombre= rs.getString(1);
                edad= rs.getInt(2);
                direccion= rs.getString(3);
                               
                vAlumnos.add(new Alumno(nombre, edad, direccion));
                
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(IODatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vAlumnos;
    }
    
    public static void insertarAlumno(Alumno a){
        
         try (Connection con = DriverManager.getConnection(RUTA, USU, PASS)){
            
            System.out.println("Conexion realizada");
            
            //String sentencia = "INSERT INTO ALUMNO VALUES('" + a.getNombre() + "'," + a.getEdad() + ",'" + a.getDireccion() + "')";
            String sentencia1 = "INSERT INTO ALUMNO VALUES(?,?,?)";
            
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sentencia1);
            
            //st.executeUpdate(sentencia1);
            ps.setString(1, a.getNombre());
            ps.setInt(2, a.getEdad());
            ps.setString(3, a.getDireccion());
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(IODatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Conexion cerrada");
        
        
    }
    
    public static void modificarNombreAlumno(String nombre){
           
         try (Connection con = DriverManager.getConnection(RUTA, USU, PASS)){
             
             String sentencia = "Update alumno set nombre = ? where nombre = ?;";
             
             PreparedStatement pt = con.prepareStatement(sentencia);
             pt.setString(1, nombre);
             pt.setString(2, "Magallon");
             
             pt.executeUpdate();
             
         } catch (SQLException ex) {
            Logger.getLogger(IODatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void guardarAlumno(){
        
        ArrayList<String> vTelefonos = new ArrayList();
        vTelefonos.add("111");
        vTelefonos.add("222");
        vTelefonos.add("333");
        
        try (Connection con = DriverManager.getConnection(RUTA, USU, PASS)){
            
            PreparedStatement ps = con.prepareStatement(
                    "insert into alumno values (?,?,?,?)");
            
            ps.setString(1, "pepe");
            ps.setInt(2, 30);
            ps.setString(3, "calle2");
            
            ByteArrayOutputStream salida = new ByteArrayOutputStream();
            ObjectOutputStream ot = new ObjectOutputStream(salida);
            
            ot.writeObject(vTelefonos);
            
            ps.setBytes(4, salida.toByteArray());
            
            ps.executeUpdate();
            
         } catch (SQLException ex) {
            Logger.getLogger(IODatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IODatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void cargarAlumno(){
        
        String telefonos = "";
        
        try (Connection con = DriverManager.getConnection(RUTA, USU, PASS)){
             
            PreparedStatement ps =  con.prepareStatement("Select * from alumno");
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                
                System.out.println(rs.getString(1) + " - " + rs.getInt(2) + " - " + rs.getString(3) + " - Telefonos: ");
                
                ByteArrayInputStream in = new ByteArrayInputStream(rs.getBytes(4));
                ObjectInputStream leer = new ObjectInputStream(in);
                
                telefonos = ((ArrayList<String>)leer.readObject()).toString();
                
                System.out.println(telefonos);
                
            }
             
         } catch (SQLException ex) {
            Logger.getLogger(IODatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IODatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IODatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
