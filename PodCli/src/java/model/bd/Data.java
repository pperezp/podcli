package model.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private Conexion con;
    
    public Data() throws SQLException, ClassNotFoundException {
        
        con = new Conexion(
                "localhost", 
                "podcli",//nombre BD
                "root",
                ""//Password
        ); 
        
        //Metodos:
        
            //1)Antecedentes personales
        
            //2)Antecentes morbidos
        
            //3)Examen fisicos
        
            /*Registrar Paciente(){
                1
                2
                3
              }
                */
            
            //atencion Podologica
            
            //Buscar paciente para listar (por rut, nombre, apellido y más??)
            
            //Buscar Paciente (atencion Podologica)
        
            // listar/get ficha - estado civil - perfil
            
    }
    

    public List<EstadoCivil> getEstadoCivil() throws SQLException{
                List<EstadoCivil> list = new ArrayList<>();
                
                String query = "select * from estado civil";
                
                ResultSet rs = con.ejecutarSelect(query);
                
                EstadoCivil es;
                
                while(rs.next()){
                    es = new EstadoCivil();
                    
                    es.setId(rs.getInt(1));
                    es.setNombre(rs.getString(2));
                    
                    list.add(es);
                }
                con.desconectar();
                
                return list;
                
            }

    public void registrarPaciente(Paciente p) throws SQLException{
            
        con.ejecutar("INSERT INTO paciente VALUES (null, '"+p.getRut()+"', '"+p.getNombre()+"',"
                + " '"+p.getSexo()+"', '"+p.getDomicilio()+"', '"+p.getFechaNacimiento()+"',"
                + " '"+p.getEstadoCivil()+"', '"+p.getActividad()+"', '"+p.getTelefonos()+"');");
    }
    
    
}
//Si alguno ve que falta algo, Digalo por wsp o en algun momento, non se callen nada Saludos