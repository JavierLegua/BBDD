
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alumnodaw
 */



public class BBDD {
    
    public static void main(String[] args) {
       
        //IODatos.guardarBBDD();
        
        //IODatos.consultarBBDD();
        
        //IODatos.insertarColumna("direccion");
        
        ArrayList<Alumno> vAlumnos = IODatos.cargarDatosAlumno();
        
        System.out.println(vAlumnos);
       
    }
    
}
