package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.beans.factory.InitializingBean;

import util.DBUtils;

public class PageTemplate implements InitializingBean {
	
	private int id;
	
	private String name;
	
	private Region mainRegion;
	
	private ArrayList<Region> otherRegions;
	
//	private SessionFactory sessionFactory;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Region getMainRegion() {
		return mainRegion;
	}

	public void setMainRegion(Region mainRegion) {
		this.mainRegion = mainRegion;
	}

	public ArrayList<Region> getOtherRegions() {
		return otherRegions;
	}

	public void setOtherRegions(ArrayList<Region> otherRegions) {
		this.otherRegions = otherRegions;
	}
	
//	public void setSessionFactory(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}
	
	private void init() {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "select id, name from page_template where enabled=1";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				this.setId(rs.getInt(1));
				this.setName(rs.getString(2));
			}
			DBUtils.close(null, stmt, rs);
			sql = "select id, name from region where template_id=? and isMainRegion=1";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, this.id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				this.mainRegion = new Region();
				this.mainRegion.setId(rs.getInt(1));
				this.mainRegion.setName(rs.getString(2));
			}
			DBUtils.close(null, stmt, rs);
			sql = "select id, name from region where template_id=? and isMainRegion=0";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, this.id);
			rs = stmt.executeQuery();
			otherRegions = new ArrayList<Region>();
			Region region;
			while (rs.next()) {
				region = new Region();
				otherRegions.add(region);
				region.setId(rs.getInt(1));
				region.setName(rs.getString(2));
			}
			DBUtils.close(null, stmt, rs);
			setRegionModules(conn, mainRegion);
			for (Region r : otherRegions) {
				setRegionModules(conn, r);
			}
			DBUtils.close(conn, null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void setRegionModules(Connection conn, Region region) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "select id, name, controllerName from region_modules, module where region_id=? and module_id=id";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, region.getId());
			rs = stmt.executeQuery();
			ArrayList<Module> modules = new ArrayList<Module>();
			region.setModules(modules);
			Module module;
			while (rs.next()) {
				module = new Module();
				modules.add(module);
				module.setId(rs.getInt(1));
				module.setName(rs.getString(2));
				module.setControllerName(rs.getString(3));
			}
			DBUtils.close(null, stmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
