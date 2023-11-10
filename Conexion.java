package DEPORTES_EXTREMOS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Conexion {
    Connection conni;
////
    private static final String host = "localhost";
    private static final String port = "3306";
    private static final String dbName = "deportes";
    private static final String userName = "root";
    private static final String password = "12345678";
    private static Conexion instance;

    public Conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver"); // Cambié el nombre del controlador
            String url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dbName +
                    "?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            conni = DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Error, no se pudo conectar a la base de datos: " + e.getMessage());
        }
    }

    public void cerrarConexion() {
        try {
            if (conni != null) {
                conni.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión:" + e.getMessage());
        }
    }

   public deportesextremos getProductoPorNombre(String nombreProducto) {
    String sql = "SELECT * FROM deportes_extremos WHERE NOMBRE_PRODUCTO = ?";
    try {
        PreparedStatement statement = conni.prepareStatement(sql);
        statement.setString(1, nombreProducto);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("ID");
            String nombre = resultSet.getString("NOMBRE_PRODUCTO");
            double precio = resultSet.getDouble("PRECIO");
            int cantidad = resultSet.getInt("CANTIDAD"); // Agregar la obtención de la cantidad

            return new deportesextremos(nombre, precio, cantidad); // Pasar la cantidad al constructor de deportesextremos
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null; // Retorna null si el producto no se encuentra en la base de datos
}


    public double obtenerPrecioProducto(String nombreProducto) {
        String sql = "SELECT PRECIO FROM deportes_extremos WHERE NOMBRE_PRODUCTO = ?";
        try {
            PreparedStatement statement = conni.prepareStatement(sql);
            statement.setString(1, nombreProducto);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("PRECIO");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1.0;
    }

  public List<deportesextremos> getProductosFromDatabase() {
    List<deportesextremos> productos = new ArrayList<>();
    String sql = "SELECT ID, NOMBRE_PRODUCTO, PRECIO, CANTIDAD FROM deportes_extremos";
    try {
        PreparedStatement statement = conni.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("ID");
            String nombre = resultSet.getString("NOMBRE_PRODUCTO");
            double precio = resultSet.getDouble("PRECIO");
            int cantidad = resultSet.getInt("CANTIDAD"); // Agregar la obtención de la cantidad

            productos.add(new deportesextremos(nombre, precio, cantidad)); // Pasar la cantidad al constructor de deportesextremos
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return productos;
}

   public void agregarProductoALaBaseDeDatos(String productoNombre, int cantidadInicial) {
        try {
            String sql = "INSERT INTO deportes_extremos (NOMBRE_PRODUCTO, CANTIDAD, PRECIO) VALUES (?, ?, 0)";
            PreparedStatement preparedStatement = conni.prepareStatement(sql);
            preparedStatement.setString(1, productoNombre);
            preparedStatement.setInt(2, cantidadInicial);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  public void aumentarCantidadEnBaseDeDatos(String productoNombre, int cantidadAumentar) {
    try {
        String sql = "UPDATE deportes_extremos SET CANTIDAD = CANTIDAD + ? WHERE NOMBRE_PRODUCTO = ?";
        PreparedStatement preparedStatement = conni.prepareStatement(sql);
        preparedStatement.setInt(1, cantidadAumentar); // Usar la cantidad pasada como parámetro
        preparedStatement.setString(2, productoNombre);
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
  
