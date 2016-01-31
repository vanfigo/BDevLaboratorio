package bucketdev.laboratorio.menu.viewmodel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.bean.AccionBean;
import bucketdev.laboratorio.bean.ModuloBean;
import bucketdev.laboratorio.bean.UsuarioBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;

public class MenuViewModel {

	public List<ModuloBean> consultarPorUsuario(UsuarioBean _usuarioBean) throws BDevException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder("SELECT m.id, m.nombre, m.accion, m.icono, m.color, ")
					.append("ra.accion_id, a.nombre AS accion_nombre FROM modulos m ")
					.append("JOIN acciones a ON a.modulo_id = m.id AND a.activo = 1 ")
					.append("JOIN roles_acciones ra ON ra.accion_id = a.id ")
					.append("JOIN roles r ON r.id = ra.rol_id AND r.activo = 1 ")
					.append("JOIN usuarios u ON u.rol_id = r.id AND u.activo = 1 WHERE m.activo = 1 AND u.id = ? ");
			con = BDev.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, _usuarioBean.getId());

			rs = pstmt.executeQuery();
			List<ModuloBean> listaModulos = new ArrayList<>();

			int id = 0;
			ModuloBean moduloBean = null;
			while (rs.next()) {
				if (id != rs.getInt("id")) {
					id = rs.getInt("id");

					moduloBean = new ModuloBean();
					moduloBean.setId(id);
					moduloBean.setNombre(rs.getString("nombre"));
					moduloBean.setAccion(rs.getString("accion"));
					moduloBean.setIcono(rs.getString("icono"));
					moduloBean.setColor(rs.getString("color"));
					moduloBean.setListaAcciones(new ArrayList<>());
					listaModulos.add(moduloBean);
				}
				AccionBean accion = new AccionBean();
				accion.setId(rs.getInt("accion_id"));
				accion.setNombre(rs.getString("accion_nombre"));

				moduloBean.getListaAcciones().add(accion);
			}
			if (listaModulos.isEmpty()) {
				BDev.getLogger().warn(BDev.getMensaje("menu.sinmodulos"));
				throw new BDevException("menu.sinmodulos", BDevTipoMensaje.ALERTA);
			}
			return listaModulos;
		} catch (BDevException bde) {
			throw bde;
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("menu.consulta.error"), e);
			throw new BDevException("menu.consulta.error", BDevTipoMensaje.ERROR);
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
