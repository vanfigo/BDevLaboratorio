package bucketdev.laboratorio.usuarios.viewmodel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.bean.UsuarioBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;

public class UsuarioViewModel {

	public List<UsuarioBean> consulta() throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder("SELECT id, nombre, apellido_paterno, apellido_materno, usuario ")
					.append("FROM usuarios WHERE activo = 1");
			con = BDev.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();
			List<UsuarioBean> listaUsuarios = new ArrayList<>();

			while (rs.next()) {

				UsuarioBean usuarioBean = new UsuarioBean();

				usuarioBean.setId(rs.getInt("id"));
				usuarioBean.setNombre(rs.getString("nombre"));
				usuarioBean.setApellidoPaterno(rs.getString("apellido_paterno"));
				usuarioBean.setApellidoMaterno(rs.getString("apellido_materno"));
				usuarioBean.setUsuario(rs.getString("usuario"));

				listaUsuarios.add(usuarioBean);
			}
			return listaUsuarios;
		} catch (BDevException bde) {
			throw bde;
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("usuarios.consulta.error"), e);
			throw new BDevException("usuarios.consulta.error", BDevTipoMensaje.ERROR);
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

	public void agregarOEditar(UsuarioBean _usuarioBean) throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		StringBuilder sql = null;
		try {
			con = BDev.getConnection();
			if (_usuarioBean.getId() == 0) {
				sql = new StringBuilder("INSERT INTO usuarios")
						.append("(rol_id, nombre, apellido_paterno, apellido_materno, usuario, contrasena, usuario_creacion) ")
						.append("VALUES(1, ?, ?, ?, ?, ?, ?)");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, _usuarioBean.getNombre());
				pstmt.setString(2, _usuarioBean.getApellidoPaterno());
				pstmt.setString(3, _usuarioBean.getApellidoMaterno());
				pstmt.setString(4, _usuarioBean.getUsuario());
				pstmt.setString(5, _usuarioBean.getContrasena());
				pstmt.setInt(6, BDev.getSesionBean().getUsuarioBean().getId());
			} else {
				sql = new StringBuilder(
						"UPDATE usuarios SET nombre = ?, apellido_paterno = ?, apellido_materno = ?, usuario = ? ");
				if (_usuarioBean.getContrasena() != null) {
					sql.append(String.format(", contrasena = '%s' ", _usuarioBean.getContrasena()));
				}
				sql.append(", usuario_modificacion = ? WHERE id = ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, _usuarioBean.getNombre());
				pstmt.setString(2, _usuarioBean.getApellidoPaterno());
				pstmt.setString(3, _usuarioBean.getApellidoMaterno());
				pstmt.setString(4, _usuarioBean.getUsuario());
				pstmt.setInt(5, BDev.getSesionBean().getUsuarioBean().getId());
				pstmt.setInt(6, _usuarioBean.getId());
			}

			if (pstmt.executeUpdate() == 0) {
				throw new BDevException("usuarios.actualizar.error", BDevTipoMensaje.ERROR);
			}
		} catch (BDevException bde) {
			throw bde;
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("usuarios.actualizar.error"), e);
			throw new BDevException("usuarios.actualizar.error", BDevTipoMensaje.ERROR);
		} finally {
			try {
				if (con != null)
					con.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				BDev.getLogger().fatal("mensaje.administrador", e);
			}
		}
	}

	public void eliminar(UsuarioBean _usuarioBean) throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		StringBuilder sql = null;
		try {
			con = BDev.getConnection();

			sql = new StringBuilder("DELETE FROM usuarios WHERE id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, _usuarioBean.getId());

			if (pstmt.executeUpdate() == 0) {
				throw new BDevException("usuarios.actualizar.error", BDevTipoMensaje.ERROR);
			}
		} catch (BDevException bde) {
			throw bde;
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("usuarios.actualizar.error"), e);
			throw new BDevException("usuarios.actualizar.error", BDevTipoMensaje.ERROR);
		} finally {
			try {
				if (con != null)
					con.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				BDev.getLogger().fatal("mensaje.administrador", e);
			}
		}
	}

}
