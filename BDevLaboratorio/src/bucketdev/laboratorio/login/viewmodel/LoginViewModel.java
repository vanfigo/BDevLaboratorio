package bucketdev.laboratorio.login.viewmodel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.bean.RolBean;
import bucketdev.laboratorio.bean.UsuarioBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;

public class LoginViewModel {

	public UsuarioBean validaCredenciales(UsuarioBean _usuarioBean) throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder("select u.id, u.contrasena, u.rol_id, r.nombre as rol, ")
					.append("u.nombre, u.apellido_paterno, u.apellido_materno from usuarios u ")
					.append("join roles r on r.id = u.rol_id where u.usuario = ?");
			con = BDev.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, _usuarioBean.getUsuario());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				if(rs.getString("contrasena").equals(_usuarioBean.getContrasena())) {
					UsuarioBean usuarioBean = new UsuarioBean();
					RolBean rolBean = new RolBean();
					
					rolBean.setId(rs.getInt("rol_id"));
					rolBean.setNombre(rs.getString("rol"));
					
					usuarioBean.setId(rs.getInt("id"));
					usuarioBean.setNombre(rs.getString("nombre"));
					usuarioBean.setApellidoPaterno(rs.getString("apellido_paterno"));
					usuarioBean.setApellidoMaterno(rs.getString("apellido_materno"));
					usuarioBean.setRolBean(rolBean);
					
					return usuarioBean;
				} else {
					throw new BDevException("login.error.contrasena", BDevTipoMensaje.ALERTA);
				}
			} else {
				throw new BDevException("login.error.usuario", BDevTipoMensaje.ALERTA);
			}
		} catch (BDevException bde) {
			throw bde;
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("login.error"), e);
			throw new BDevException("login.error", BDevTipoMensaje.ERROR);
		} finally {
			try {
				if (con != null)
					con.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				BDev.getLogger().fatal("mensaje.administrador", e);
			}
		}
	}
	
	public void registrarSesion(UsuarioBean _usuarioBean) throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = BDev.getConnection();
			con.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder("UPDATE sesiones_usuarios ")
					.append("SET fecha_modificacion = CURRENT_TIMESTAMP(), activo = 0 WHERE usuario_id = ? and activo = 1");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, _usuarioBean.getId());
			
			pstmt.executeUpdate();
			pstmt.close();
			
			sql = new StringBuilder("INSERT INTO sesiones_usuarios(usuario_id) VALUES(?)");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, _usuarioBean.getId());
			
			pstmt.executeUpdate();
			
			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException sqle) {
				BDev.getLogger().fatal("mensaje.administrador", sqle);
			}
			BDev.getLogger().error(BDev.getMensaje("login.error.registro"), e);
			throw new BDevException("login.error.registro", BDevTipoMensaje.ERROR);
		} finally {
			try {
				if (con != null)
					con.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				BDev.getLogger().fatal("mensaje.administrador", e);
			}
		}
	}
	
	public void cerrarSesion(UsuarioBean _usuarioBean) throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = BDev.getConnection();
			
			StringBuilder sql = new StringBuilder("UPDATE sesiones_usuarios ")
					.append("SET fecha_modificacion = CURRENT_TIMESTAMP(), activo = 0 WHERE usuario_id = ? and activo = 1");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, _usuarioBean.getId());
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("login.error.registro"), e);
			throw new BDevException("login.error.registro", BDevTipoMensaje.ERROR);
		} finally {
			try {
				if (con != null)
					con.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				BDev.getLogger().fatal("mensaje.administrador", e);
			}
		}
	}

}
