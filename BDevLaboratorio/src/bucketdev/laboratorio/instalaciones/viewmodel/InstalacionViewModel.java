package bucketdev.laboratorio.instalaciones.viewmodel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.bean.ConsumibleBean;
import bucketdev.laboratorio.bean.EquipoBean;
import bucketdev.laboratorio.bean.InstalacionBean;
import bucketdev.laboratorio.bean.InstalacionFiltroBean;
import bucketdev.laboratorio.bean.UbicacionBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoEstatus;
import bucketdev.laboratorio.type.BDevTipoMensaje;

public class InstalacionViewModel {

	public List<InstalacionBean> consulta(InstalacionFiltroBean _filtroBean) throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder("SELECT cu.id, cu.consumible_id, c.nombre AS consumible_nombre, ")
					.append("c.equipo_id, e.nombre AS equipo_nombre, cu.ubicacion_id, c.clave AS consumible_clave, ")
					.append("u.nombre AS ubicacion_nombre, cu.caducidad, cu.fecha_creacion, c.caducidad AS consumible_caducidad, ")
					.append("cu.estatus_id, es.nombre AS estatus_nombre, e.clave AS equipo_clave, cu.serie ")
					.append("FROM consumibles_ubicaciones cu ")
					.append("JOIN estatus es ON es.id = cu.estatus_id ")
					.append("JOIN consumibles c ON c.id = cu.consumible_id AND c.activo = 1 ")
					.append("JOIN equipos e ON e.id = c.equipo_id AND e.activo = 1 ")
					.append("JOIN ubicaciones u ON u.id = cu.ubicacion_id AND u.activo = 1 ")
					.append("WHERE cu.activo = 1 ")
					.append(String.format("AND cu.estatus_id = %d ", BDevTipoEstatus.DISPONIBLE.getId()));
			if(_filtroBean != null) {
				if(_filtroBean.getEquipoBean() != null) {
					sql.append(String.format("AND e.id = %d ", _filtroBean.getEquipoBean().getId()));
				}
				if(_filtroBean.getConsumibleBean() != null) {
					sql.append(String.format("AND c.id = %d ", _filtroBean.getConsumibleBean().getId()));
				}
				if (_filtroBean.isPorCaducar()) {
					sql.append(String.format(
							"AND current_timestamp() >= DATE_ADD(DATE_ADD(cu.fecha_creacion, INTERVAL cu.caducidad DAY), INTERVAL -%d DAY) ",
							_filtroBean.getDiasCaducidad()));
				}
			}
			con = BDev.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();
			List<InstalacionBean> listaUbicaciones = new ArrayList<>();

			while (rs.next()) {
				InstalacionBean instalacionBean = new InstalacionBean();

				instalacionBean.setId(rs.getInt("id"));
				instalacionBean.setSerie(rs.getString("serie"));
				instalacionBean.setCaducidad(rs.getInt("caducidad"));
				instalacionBean.setFechaCaducidad(rs.getTimestamp("fecha_creacion"));
				instalacionBean.setTipoEstatus(BDevTipoEstatus.getTipoEstatusByID(rs.getInt("estatus_id")));

				UbicacionBean ubicacionBean = new UbicacionBean();
				ubicacionBean.setId(rs.getInt("ubicacion_id"));
				ubicacionBean.setNombre(rs.getString("ubicacion_nombre"));

				ConsumibleBean consumibleBean = new ConsumibleBean();
				consumibleBean.setId(rs.getInt("consumible_id"));
				consumibleBean.setClave(rs.getString("consumible_clave"));
				consumibleBean.setNombre(rs.getString("consumible_nombre"));
				consumibleBean.setCaducidad(rs.getInt("consumible_caducidad"));

				EquipoBean equipoBean = new EquipoBean();
				equipoBean.setId(rs.getInt("equipo_id"));
				equipoBean.setClave(rs.getString("equipo_clave"));
				equipoBean.setNombre(rs.getString("equipo_nombre"));

				consumibleBean.setEquipoBean(equipoBean);

				instalacionBean.setUbicacionBean(ubicacionBean);
				instalacionBean.setConsumibleBean(consumibleBean);

				listaUbicaciones.add(instalacionBean);
			}
			return listaUbicaciones;
		} catch (BDevException bde) {
			throw bde;
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("instalaciones.consulta.error"), e);
			throw new BDevException("instalaciones.consulta.error", BDevTipoMensaje.ERROR);
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

	public void agregarOEditar(InstalacionBean _instalacionBean) throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		StringBuilder sql = null;
		try {
			con = BDev.getConnection();
			if (_instalacionBean.getId() == 0) {
				sql = new StringBuilder("INSERT INTO consumibles_ubicaciones(consumible_id, ubicacion_id, ")
						.append("caducidad, usuario_creacion) SELECT c.id, ?, c.caducidad, ? ")
						.append("FROM consumibles c ")
						.append("JOIN equipos e ON e.id = c.equipo_id ")
						.append("WHERE e.id = ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, _instalacionBean.getUbicacionBean().getId());
				pstmt.setInt(2, BDev.getSesionBean().getUsuarioBean().getId());
				pstmt.setInt(3, _instalacionBean.getConsumibleBean().getEquipoBean().getId());
			} else {
				sql = new StringBuilder("UPDATE consumibles_ubicaciones SET serie = ?, usuario_modificacion = ? WHERE id = ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, _instalacionBean.getSerie());
				pstmt.setInt(2, BDev.getSesionBean().getUsuarioBean().getId());
				pstmt.setInt(3, _instalacionBean.getId());
			}

			if (pstmt.executeUpdate() == 0) {
				throw new BDevException("instalaciones.actualizar.alerta", BDevTipoMensaje.ALERTA);
			}
		} catch (BDevException bde) {
			throw bde;
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("instalaciones.actualizar.error"), e);
			throw new BDevException("instalaciones.actualizar.error", BDevTipoMensaje.ERROR);
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

	public void renovar(InstalacionBean _instalacionBean) throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		StringBuilder sql = null;
		try {
			con = BDev.getConnection();
			sql = new StringBuilder(
					String.format("UPDATE consumibles_ubicaciones SET estatus_id = %d", BDevTipoEstatus.CADUCADO.getId()))
							.append(", usuario_modificacion = ? WHERE id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, BDev.getSesionBean().getUsuarioBean().getId());
			pstmt.setInt(2, _instalacionBean.getId());

			if (pstmt.executeUpdate() == 0) {
				throw new BDevException("instalaciones.actualizar.error", BDevTipoMensaje.ERROR);
			} else {
				pstmt.close();
				sql = new StringBuilder("INSERT INTO consumibles_ubicaciones ")
						.append("(ubicacion_id, consumible_id, caducidad, usuario_creacion) VALUES(?, ?, ?, ?)");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, _instalacionBean.getUbicacionBean().getId());
				pstmt.setInt(2, _instalacionBean.getConsumibleBean().getId());
				pstmt.setInt(3, _instalacionBean.getCaducidad());
				pstmt.setInt(4, BDev.getSesionBean().getUsuarioBean().getId());
				if (pstmt.executeUpdate() == 0) {
					throw new BDevException("instalaciones.actualizar.error", BDevTipoMensaje.ERROR);
				}
			}
		} catch (BDevException bde) {
			throw bde;
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("salidas.actualizar.error"), e);
			throw new BDevException("salidas.actualizar.error", BDevTipoMensaje.ERROR);
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

	public void eliminar(InstalacionBean _instalacionBean) throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		StringBuilder sql = null;
		try {
			con = BDev.getConnection();

			sql = new StringBuilder("UPDATE consumibles_ubicaciones SET activo = 0, usuario_modificacion = ? WHERE id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, BDev.getSesionBean().getUsuarioBean().getId());
			pstmt.setInt(2, _instalacionBean.getId());

			if (pstmt.executeUpdate() == 0) {
				throw new BDevException("instalaciones.actualizar.error", BDevTipoMensaje.ERROR);
			}
		} catch (BDevException bde) {
			throw bde;
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("instalaciones.actualizar.error"), e);
			throw new BDevException("instalaciones.actualizar.error", BDevTipoMensaje.ERROR);
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
