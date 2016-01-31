package bucketdev.laboratorio.ubicaciones.viewmodel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.bean.UbicacionBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;

public class UbicacionViewModel {

	public List<UbicacionBean> consulta() throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder("SELECT id, nombre, direccion FROM ubicaciones WHERE activo = 1");
			con = BDev.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();
			List<UbicacionBean> listaUbicaciones = new ArrayList<>();

			while (rs.next()) {

				UbicacionBean ubicacionBean = new UbicacionBean();

				ubicacionBean.setId(rs.getInt("id"));
				ubicacionBean.setNombre(rs.getString("nombre"));
				ubicacionBean.setDireccion(rs.getString("direccion"));

				listaUbicaciones.add(ubicacionBean);
			}
			return listaUbicaciones;
		} catch (BDevException bde) {
			throw bde;
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("ubicaciones.consulta.error"), e);
			throw new BDevException("ubicaciones.consulta.error", BDevTipoMensaje.ERROR);
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

	public void agregarOEditar(UbicacionBean _ubicacionBean) throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		StringBuilder sql = null;
		try {
			con = BDev.getConnection();
			if (_ubicacionBean.getId() == 0) {
				sql = new StringBuilder("INSERT INTO ubicaciones(nombre, direccion, usuario_creacion) VALUES(?, ?, ?)");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, _ubicacionBean.getNombre());
				pstmt.setString(2, _ubicacionBean.getDireccion());
				pstmt.setInt(3, BDev.getSesionBean().getUsuarioBean().getId());
			} else {
				sql = new StringBuilder("UPDATE ubicaciones SET nombre = ?, direccion = ?, usuario_modificacion = ? ")
						.append("WHERE id = ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, _ubicacionBean.getNombre());
				pstmt.setString(2, _ubicacionBean.getDireccion());
				pstmt.setInt(3, BDev.getSesionBean().getUsuarioBean().getId());
				pstmt.setInt(4, _ubicacionBean.getId());
			}

			if (pstmt.executeUpdate() == 0) {
				throw new BDevException("ubicaciones.actualizar.error", BDevTipoMensaje.ERROR);
			}
		} catch (BDevException bde) {
			throw bde;
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("ubicaciones.actualizar.error"), e);
			throw new BDevException("ubicaciones.actualizar.error", BDevTipoMensaje.ERROR);
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

	public void eliminar(UbicacionBean _ubicacionBean) throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		StringBuilder sql = null;
		try {
			con = BDev.getConnection();

			sql = new StringBuilder("UPDATE ubicaciones SET activo = 0 WHERE id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, _ubicacionBean.getId());

			if (pstmt.executeUpdate() == 0) {
				throw new BDevException("ubicaciones.actualizar.error", BDevTipoMensaje.ERROR);
			}
		} catch (BDevException bde) {
			throw bde;
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("ubicaciones.actualizar.error"), e);
			throw new BDevException("ubicaciones.actualizar.error", BDevTipoMensaje.ERROR);
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
