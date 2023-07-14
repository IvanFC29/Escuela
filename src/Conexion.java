import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;

public class Conexion 
{
    private static final String CONTROLADOR = "com.mysql.jdbc.Driver";

    private final String URL = "jdbc:mysql://localhost:3306/escuela";
    private final String USUARIO = "root" ;
    private final String PASSWORD = "";   

    static{
		try{
			Class.forName(CONTROLADOR);	
		}catch(ClassNotFoundException e){
			System.out.println("Error al cargar el controlador");
			e.printStackTrace();	
		}
	}
    
    public Connection conectar(){
		Connection conector = null;
		try{			
			conector = (Connection) DriverManager.getConnection(URL, USUARIO, PASSWORD);
			System.out.println("Conexion exitosa");
		}catch(SQLException e){
			System.out.println("Error en la coneccion");
			e.printStackTrace();
		}
		return conector;
	}
}
