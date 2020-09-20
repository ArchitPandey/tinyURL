package tinyurl.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
public class AppConfig {
	
	@Bean
	public DataSource tinyURLDataSource(@Value("${db.driverclass}") String driverclass, @Value("${db.url}") String url, @Value("${db.user") String user, @Value("${db.password}") String password) {
		DriverManagerDataSource dsbean = new DriverManagerDataSource();
		dsbean.setDriverClassName(driverclass);
		dsbean.setUrl(url);
		dsbean.setUsername(user);
		dsbean.setPassword(password);
		return dsbean;
	}
	
	@Bean
	PlatformTransactionManager platformTrasactionManager(@Qualifier("tinyURLDataSource") DataSource datasource) {
		return new DataSourceTransactionManager(datasource);
	}
	
	@Bean 
	public JdbcTemplate jdbcTemplate(@Qualifier("tinyURLDataSource") DataSource datasource) {
		return new JdbcTemplate(datasource);
	}
	
}
