package mx.bg.logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.bg.basededatos.AdministradorBaseDeDatos;

public class MembresiaDAO implements IMembresiaDAO {

    AdministradorBaseDeDatos administradorAccesoBase = new AdministradorBaseDeDatos();

    @Override
    public int registrarMembresia(Membresia membresia) throws ExcepcionDAO {
        try{
        existeMembresiaConCaracteristicas(membresia);
        existeMembresiaNombreRegistro(membresia);
        return ejecutarRegistrarMembresia(membresia);
        }catch (IllegalArgumentException ex) {
            throw new ExcepcionDAO(ex.getMessage(), EstadosDAO.ADVERTENCIA);
        }
    }
    
     public int ejecutarRegistrarMembresia(Membresia membresia) throws ExcepcionDAO {
        Connection conexion = null;
        int filasAfectadas = 0;
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement objetoMembresia = conexion.prepareStatement("INSERT INTO membresia ("
                    + "nombre, precio, caracteristicaAccesoIlimitado, caracteristicaClaseGrupal"
                    + ", caracteristicaEstacionamiento, caracteristicaInternet, caracteristicaS"
                    + "pa) VALUES (?,?,?,?,?,?,?)");
            objetoMembresia.setString(1, membresia.getNombre());
            objetoMembresia.setString(2, membresia.getPrecio());
            objetoMembresia.setString(3, membresia.getCaracteristicaAccesoIlimitado());
            objetoMembresia.setString(4, membresia.getCaracteristicaClaseGrupal());
            objetoMembresia.setString(5, membresia.getCaracteristicaEstacionamiento());
            objetoMembresia.setString(6, membresia.getCaracteristicaInternet());
            objetoMembresia.setString(7, membresia.getCaracteristicaSpa());
            filasAfectadas = objetoMembresia.executeUpdate();

        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("Se ha perdido la conexión con la base de dato", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return filasAfectadas;
    }

    @Override
    public int eliminarMembresia(int idMembresia) throws ExcepcionDAO {
        try{
        existeMiembroAsociadoMembresia(idMembresia);
        return ejecutarEliminarMembresia(idMembresia);
        }catch (IllegalArgumentException ex) {
            throw new ExcepcionDAO(ex.getMessage(), EstadosDAO.ADVERTENCIA);
        }
    }
    
    private int ejecutarEliminarMembresia(int idMembresia) throws ExcepcionDAO {
        Connection conexion = null;
        int filasAfectadas = 0;
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement objetoMembresia = conexion.prepareStatement("DELETE FROM membresia WHERE idMembresia = ?");
            objetoMembresia.setInt(1, idMembresia);
            filasAfectadas = objetoMembresia.executeUpdate();

        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("Se ha perdido la conexión con la base de datos", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return filasAfectadas;
    }

    @Override
    public int actualizarMembresia(Membresia membresia) throws ExcepcionDAO {
        try{
        existeMembresiaConCaracteristicasActualizacion(membresia);
        existeMembresiaNombreActualizacion(membresia);  
        return ejecutarActualizarMembresia(membresia);
        }catch (IllegalArgumentException ex) {
            throw new ExcepcionDAO(ex.getMessage(), EstadosDAO.ADVERTENCIA);
        }
    }
    
     public int ejecutarActualizarMembresia(Membresia membresia) throws ExcepcionDAO {
        Connection conexion = null;
        int filasAfectadas = 0;
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement objetoMembresia = conexion.prepareStatement(
                    "UPDATE membresia SET nombre = ?, precio = ?, caracteristicaaccesoilimitado = ?, "
                    + "caracteristicaclasegrupal = ?, caracteristicaestacionamiento = ?, caracteristicainternet = ?, "
                    + "caracteristicaspa = ? WHERE idMembresia = ?"
            );
            objetoMembresia.setString(1, membresia.getNombre());
            objetoMembresia.setString(2, membresia.getPrecio());
            objetoMembresia.setString(3, membresia.getCaracteristicaAccesoIlimitado());
            objetoMembresia.setString(4, membresia.getCaracteristicaClaseGrupal());
            objetoMembresia.setString(5, membresia.getCaracteristicaEstacionamiento());
            objetoMembresia.setString(6, membresia.getCaracteristicaInternet());
            objetoMembresia.setString(7, membresia.getCaracteristicaSpa());
            objetoMembresia.setInt(8, membresia.getIdMembresia());

            filasAfectadas = objetoMembresia.executeUpdate();

        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("Se ha perdido la conexión con la base de datos", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return filasAfectadas;
    }

    @Override
    public ArrayList<Membresia> consultarTotalMembresia() throws ExcepcionDAO {
        ArrayList<Membresia> listaMembresias = new ArrayList<>();
        Connection conexion = null;
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement objetoConsulta = conexion.prepareStatement("SELECT * FROM membresia");
            ResultSet resultado = objetoConsulta.executeQuery();

            while (resultado.next()) {
                Membresia membresia = new Membresia();
                membresia.setIdMembresia(resultado.getInt("idMembresia"));
                membresia.setNombre(resultado.getString("nombre"));
                membresia.setPrecio(resultado.getString("precio"));
                membresia.setCaracteristicaAccesoIlimitado(resultado.getString("caracteristicaaccesoilimitado"));
                membresia.setCaracteristicaClaseGrupal(resultado.getString("caracteristicaclasegrupal"));
                membresia.setCaracteristicaEstacionamiento(resultado.getString("caracteristicaestacionamiento"));
                membresia.setCaracteristicaInternet(resultado.getString("caracteristicainternet"));
                membresia.setCaracteristicaSpa(resultado.getString("caracteristicaspa"));
                listaMembresias.add(membresia);
            }

        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("Se ha perdido la conexión con la base de datos", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return listaMembresias;
    }

    @Override
    public Membresia consultarTotalMembresiaporId(int idMembresia) throws ExcepcionDAO {
        Connection conexion = null;
        Membresia membresia = null;
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement objetoConsulta = conexion.prepareStatement("SELECT * FROM membresia WHERE idMembresia = ?");
            objetoConsulta.setInt(1, idMembresia);
            ResultSet resultado = objetoConsulta.executeQuery();

            if (resultado.next()) {
                membresia = new Membresia();
                membresia.setIdMembresia(resultado.getInt("idMembresia"));
                membresia.setNombre(resultado.getString("nombre"));
                membresia.setPrecio(resultado.getString("precio"));
                membresia.setCaracteristicaAccesoIlimitado(resultado.getString("caracteristicaaccesoilimitado"));
                membresia.setCaracteristicaClaseGrupal(resultado.getString("caracteristicaclasegrupal"));
                membresia.setCaracteristicaEstacionamiento(resultado.getString("caracteristicaestacionamiento"));
                membresia.setCaracteristicaInternet(resultado.getString("caracteristicainternet"));
                membresia.setCaracteristicaSpa(resultado.getString("caracteristicaspa"));
            }

        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("Se ha perdido la conexión con la base de datos", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return membresia;
    }

    private void existeMembresiaConCaracteristicas(Membresia membresia) throws ExcepcionDAO {
        Connection conexion = null;
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM membresia WHERE "
                    + " nombre = ? AND caracteristicaAccesoIlimitado = ? AND "
                    + "caracteristicaClaseGrupal = ? AND caracteristicaEstacionamiento = ? "
                    + "AND caracteristicaInternet = ? AND caracteristicaSpa = ?");
            consulta.setString(1, membresia.getNombre());
            consulta.setString(2, membresia.getCaracteristicaAccesoIlimitado());
            consulta.setString(3, membresia.getCaracteristicaClaseGrupal());
            consulta.setString(4, membresia.getCaracteristicaEstacionamiento());
            consulta.setString(5, membresia.getCaracteristicaInternet());
            consulta.setString(6, membresia.getCaracteristicaSpa());

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                throw new IllegalArgumentException("Ya se ha registrado una membresia con estas características.");
            }

        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("Error al comprobar la existencia de la membresía.", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
    }
    
    private void existeMembresiaConCaracteristicasActualizacion(Membresia membresia) throws ExcepcionDAO {
        Connection conexion = null;
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM membresia WHERE "
                    + "caracteristicaAccesoIlimitado = ? AND caracteristicaClaseGrupal = ? AND caracteristicaEstacionamiento = ? "
                    + "AND caracteristicaInternet = ? AND caracteristicaSpa = ? AND idMembresia != ?");
            consulta.setString(1, membresia.getCaracteristicaAccesoIlimitado());
            consulta.setString(2, membresia.getCaracteristicaClaseGrupal());
            consulta.setString(3, membresia.getCaracteristicaEstacionamiento());
            consulta.setString(4, membresia.getCaracteristicaInternet());
            consulta.setString(5, membresia.getCaracteristicaSpa());
            consulta.setInt(6, membresia.getIdMembresia());

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                throw new IllegalArgumentException("Ya se ha registrado una membresia con estas características.");
            }

        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("Error al comprobar la existencia de la membresía.", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
    }
    
    private void existeMembresiaNombreActualizacion(Membresia membresia) throws ExcepcionDAO {
        Connection conexion = null;
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM membresia WHERE "
                    + "nombre = ? AND idMembresia != ?");
            consulta.setString(1,membresia.getNombre());
            consulta.setInt(2, membresia.getIdMembresia());

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                throw new IllegalArgumentException("Ya hay una membresia registrada con ese nombre.");
            }

        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("Error al comprobar la existencia de la membresía.", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
    }
    
    private void existeMembresiaNombreRegistro(Membresia membresia) throws ExcepcionDAO {
        Connection conexion = null;
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM membresia WHERE "
                    + "nombre = ?");
            consulta.setString(1,membresia.getNombre());
            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                throw new IllegalArgumentException("Ya hay una membresia registrada con ese nombre");
            }

        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("Error al comprobar la existencia de la membresía.", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
    }
    
    private void existeMiembroAsociadoMembresia(int idMembresia) throws ExcepcionDAO {
        Connection conexion = null;
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM miembro WHERE idMembresia = ?");
            consulta.setInt(1, idMembresia);

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                throw new IllegalArgumentException("No puede eliminar esta membresia, un miembro está asociada a ella.");
            }

        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("Error al comprobar la existencia de la membresía.", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
    }

}
