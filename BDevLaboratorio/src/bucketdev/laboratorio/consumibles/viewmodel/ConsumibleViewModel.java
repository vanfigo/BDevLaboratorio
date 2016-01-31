package bucketdev.laboratorio.consumibles.viewmodel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.bean.ConsumibleBean;
import bucketdev.laboratorio.bean.EquipoBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;

public class ConsumibleViewModel {

	public List<ConsumibleBean> consulta(EquipoBean _equipoBean) throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder("SELECT c.id, c.clave, c.nombre, c.caducidad, c.equipo_id, ")
					.append("e.nombre AS equipo_nombre FROM consumibles c ")
					.append("JOIN equipos e ON e.id = c.equipo_id AND e.activo = 1 ");
			if(_equipoBean != null) {
				sql.append(String.format("AND e.id = %d ", _equipoBean.getId()));
			}
			sql.append(" WHERE c.activo = 1");
			con = BDev.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();
			List<ConsumibleBean> listaUsuarios = new ArrayList<>();

			while (rs.next()) {

				ConsumibleBean consumibleBean = new ConsumibleBean();

				consumibleBean.setId(rs.getInt("id"));
				consumibleBean.setClave(rs.getString("clave"));
				consumibleBean.setNombre(rs.getString("nombre"));
				consumibleBean.setCaducidad(rs.getInt("caducidad"));

				EquipoBean equipoBean = new EquipoBean();
				equipoBean.setId(rs.getInt("equipo_id"));
				equipoBean.setNombre(rs.getString("equipo_nombre"));

				consumibleBean.setEquipoBean(equipoBean);

				listaUsuarios.add(consumibleBean);
			}
			return listaUsuarios;
		} catch (BDevException bde) {
			throw bde;
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("consumibles.consulta.error"), e);
			throw new BDevException("consumibles.consulta.error", BDevTipoMensaje.ERROR);
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

	public void agregarOEditar(ConsumibleBean _consumibleBean) throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		StringBuilder sql = null;
		ResultSet rs = null;
		try {
			con = BDev.getConnection();
			if (_consumibleBean.getId() == 0) {
				sql = new StringBuilder("INSERT INTO consumibles(clave, nombre, caducidad, equipo_id, usuario_creacion) ")
						.append("VALUES(?, ?, ?, ?, ?)");
				pstmt = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, _consumibleBean.getClave());
				pstmt.setString(2, _consumibleBean.getNombre());
				pstmt.setInt(3, _consumibleBean.getCaducidad());
				pstmt.setInt(4, _consumibleBean.getEquipoBean().getId());
				pstmt.setInt(5, BDev.getSesionBean().getUsuarioBean().getId());
			} else {
				sql = new StringBuilder("UPDATE consumibles SET clave = ?, nombre = ?, caducidad = ?, equipo_id = ?, ")
						.append("usuario_modificacion = ? WHERE id = ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, _consumibleBean.getClave());
				pstmt.setString(2, _consumibleBean.getNombre());
				pstmt.setInt(3, _consumibleBean.getCaducidad());
				pstmt.setInt(4, _consumibleBean.getEquipoBean().getId());
				pstmt.setInt(5, BDev.getSesionBean().getUsuarioBean().getId());
				pstmt.setInt(6, _consumibleBean.getId());
			}

			if (pstmt.executeUpdate() == 0) {
				throw new BDevException("consumibles.actualizar.error", BDevTipoMensaje.ERROR);
			} else {
				int id = 0;
				if(_consumibleBean.getClave().isEmpty()) {
					if(_consumibleBean.getId() == 0) {
						rs = pstmt.getGeneratedKeys();
						rs.next();
						id = rs.getInt(1);
					} else {
						id = _consumibleBean.getId();
					}
					pstmt.close();
					
					sql = new StringBuilder("UPDATE consumibles SET clave = ? WHERE id = ?");
					pstmt = con.prepareStatement(sql.toString());
					pstmt.setString(1, String.valueOf(id));
					pstmt.setInt(2, id);
					
					pstmt.executeUpdate();
				}
				
			}
		} catch (BDevException bde) {
			throw bde;
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("consumibles.actualizar.error"), e);
			throw new BDevException("consumibles.actualizar.error", BDevTipoMensaje.ERROR);
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

	public void eliminar(ConsumibleBean _consumibleBean) throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		StringBuilder sql = null;
		try {
			con = BDev.getConnection();

			sql = new StringBuilder("UPDATE consumibles SET activo = 0, usuario_modificacion = ? WHERE id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, BDev.getSesionBean().getUsuarioBean().getId());
			pstmt.setInt(2, _consumibleBean.getId());

			if (pstmt.executeUpdate() == 0) {
				throw new BDevException("consumibles.actualizar.error", BDevTipoMensaje.ERROR);
			}
		} catch (BDevException bde) {
			throw bde;
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("consumibles.actualizar.error"), e);
			throw new BDevException("consumibles.actualizar.error", BDevTipoMensaje.ERROR);
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
