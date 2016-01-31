package bucketdev.laboratorio.equipos.viewmodel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.bean.EquipoBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;

public class EquipoViewModel {

	public List<EquipoBean> consulta() throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder("SELECT id, nombre, clave, descripcion ")
					.append("FROM equipos WHERE activo = 1");
			con = BDev.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();
			List<EquipoBean> listaEquipos = new ArrayList<>();

			while (rs.next()) {
				EquipoBean equipoBean = new EquipoBean();

				equipoBean.setId(rs.getInt("id"));
				equipoBean.setNombre(rs.getString("nombre"));
				equipoBean.setClave(rs.getString("clave"));
				equipoBean.setDescripcion(rs.getString("descripcion"));

				listaEquipos.add(equipoBean);
			}
			return listaEquipos;
		} catch (BDevException bde) {
			throw bde;
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("equipos.consulta.error"), e);
			throw new BDevException("equipos.consulta.error", BDevTipoMensaje.ERROR);
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

	public void agregarOEditar(EquipoBean _equipoBean) throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		StringBuilder sql = null;
		ResultSet rs = null;
		try {
			con = BDev.getConnection();
			if (_equipoBean.getId() == 0) {
				sql = new StringBuilder("INSERT INTO equipos").append("(nombre, clave, descripcion, usuario_creacion) ")
						.append("VALUES(?, ?, ?, ?)");
				pstmt = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, _equipoBean.getNombre());
				pstmt.setString(2, _equipoBean.getClave());
				pstmt.setString(3, _equipoBean.getDescripcion());
				pstmt.setInt(4, BDev.getSesionBean().getUsuarioBean().getId());
			} else {
				sql = new StringBuilder("UPDATE equipos SET nombre = ?, clave = ?, descripcion = ?, usuario_modificacion = ? WHERE id = ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, _equipoBean.getNombre());
				pstmt.setString(2, _equipoBean.getClave());
				pstmt.setString(3, _equipoBean.getDescripcion());
				pstmt.setInt(4, BDev.getSesionBean().getUsuarioBean().getId());
				pstmt.setInt(5, _equipoBean.getId());
			}

			if (pstmt.executeUpdate() == 0) {
				throw new BDevException("equipos.actualizar.error", BDevTipoMensaje.ERROR);
			} else {
				int id = 0;
				if(_equipoBean.getClave().isEmpty()) {
					if(_equipoBean.getId() == 0) {
						rs = pstmt.getGeneratedKeys();
						rs.next();
						id = rs.getInt(1);
					} else {
						id = _equipoBean.getId();
					}
					pstmt.close();
					
					sql = new StringBuilder("UPDATE equipos SET clave = ? WHERE id = ?");
					pstmt = con.prepareStatement(sql.toString());
					pstmt.setString(1, String.valueOf(id));
					pstmt.setInt(2, id);
					
					pstmt.executeUpdate();
				}
				
			}
		} catch (BDevException bde) {
			throw bde;
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("equipos.actualizar.error"), e);
			throw new BDevException("equipos.actualizar.error", BDevTipoMensaje.ERROR);
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

	public void eliminar(EquipoBean _equipoBean) throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		StringBuilder sql = null;
		try {
			con = BDev.getConnection();

			sql = new StringBuilder("UPDATE equipos SET activo = 0, usuario_modificacion = ? WHERE id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, BDev.getSesionBean().getUsuarioBean().getId());
			pstmt.setInt(2, _equipoBean.getId());

			if (pstmt.executeUpdate() == 0) {
				throw new BDevException("equipos.actualizar.error", BDevTipoMensaje.ERROR);
			}
		} catch (BDevException bde) {
			throw bde;
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("equipos.actualizar.error"), e);
			throw new BDevException("equipos.actualizar.error", BDevTipoMensaje.ERROR);
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
