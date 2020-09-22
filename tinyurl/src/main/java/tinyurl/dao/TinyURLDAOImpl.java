package tinyurl.dao;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tinyurl.commons.AppConstants;
import tinyurl.commons.AppProperties;

@Repository
public class TinyURLDAOImpl implements TinyURLDAO {

	private final String LOAD_UNUSED_KEYS = "call fetchKeys(?)";
	private final String FETCH_UNUSED_KEYS = "select seq from tmp_unused_keys";
	private final String DELETE_KEY = "delete from tu_url_map where key_value=? LIMIT 1";
	private final String FETCH_LONG_URL = "call fetchLongURL(?,?)";
	private final String UPDATE_KEY_USED = "update tu_keys set used_flag = 'Y' where key_value = ?";
	private final String UPDATE_KEY_UNUSED = "update tu_keys set used_flag = 'N' where key_value = ?";
	private final String CREATE_LONG_URL = "insert into tu_url_map values (?, ?, ?, ?)";
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	@Override
	public String getLongURL(int key) {
		List<SqlParameter> params = Arrays.asList( new SqlParameter(Types.BIGINT), new SqlOutParameter("longURL", Types.VARCHAR) );
		return (String)jdbcTemplate.call( (conn) -> {
			CallableStatement cs = conn.prepareCall(FETCH_LONG_URL);
			cs.setInt(1, key);
			cs.registerOutParameter(2, Types.VARCHAR);
			return cs;
		}, params).get("longURL");
	}

	@Override
	public int deleteTinyURL(int key) {
		jdbcTemplate.update( (conn) -> {
			PreparedStatement ps = conn.prepareStatement(UPDATE_KEY_UNUSED);
			ps.setInt(1,key);
			return ps;
		});
		return jdbcTemplate.update( (conn) -> {
			PreparedStatement ps = conn.prepareStatement(DELETE_KEY);
			ps.setInt(1,key);
			return ps;
		});
	}

	@Override
	@Transactional
	public List<Integer> fetchUnusedKeys() throws NumberFormatException, IOException {
		int fetchSize = Integer.parseInt(AppProperties.getInstance().getProperty(AppConstants.FETCH_SIZE));
		List<SqlParameter> params = Arrays.asList(new SqlParameter(Types.INTEGER));
		jdbcTemplate.call( (conn) -> {
			CallableStatement cs = conn.prepareCall(LOAD_UNUSED_KEYS);
			cs.setInt(1, fetchSize);
			return cs;
		}, params);
		return jdbcTemplate.query(FETCH_UNUSED_KEYS, (prepStmt)->{}, (rs,rnum)->rs.getInt(1));
	}

	@Override
	public int createTinyURL(String longURL, int key) throws NumberFormatException, IOException {
		jdbcTemplate.update( (conn) -> {
			PreparedStatement ps = conn.prepareStatement(UPDATE_KEY_USED);
			ps.setInt(1,key);
			return ps;
		});
		
		long offset = (Integer.parseInt(AppProperties.getInstance().getProperty(AppConstants.EXPIRATION_TIME))*1000);
		
		jdbcTemplate.update( (conn) -> {
			PreparedStatement ps = conn.prepareStatement(CREATE_LONG_URL);
			ps.setInt(1,key);
			ps.setString(2, longURL);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()+ offset ));
			return ps;
		} );
		
		return key;
	}

}
