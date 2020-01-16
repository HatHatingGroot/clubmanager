package com.clubmanager.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = {"com.clubmanager.service"})
//@EnableAspectJAutoProxy

@EnableTransactionManagement

@MapperScan(basePackages = {"com.clubmanager.mapper"})
public class RootConfig {
	
	//DBCP ���� - HikariCP�� �̿��Ͽ� DataSource �������̽��� ���� CP ��� 
	@Bean
	public HikariConfig hikariConfig() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
//		hikariConfig.setJdbcUrl("jdbc:log4jdbc:oracle:thin:@localhost:1521:XE");
//		hikariConfig.setJdbcUrl("jdbc:log4jdbc:mysql://localhost:3306/clubmanagerdb?useSSL=false&amp;serverTimeZone=Asia/Seoul&characterEncoding=utf8");
		hikariConfig.setJdbcUrl("jdbc:log4jdbc:mysql://database-1.caubwqy6j7zx.ap-northeast-2.rds.amazonaws.com:3306/clubmanagerDB?useSSL=false&amp;serverTimeZone=Asia/Seoul&characterEncoding=utf8");
		hikariConfig.setUsername("clubmanager");
		hikariConfig.setPassword("dbqmfkejtm1!");
		
		return hikariConfig;
	}
	@Bean
	public HikariDataSource dataSource() {
		
		return new HikariDataSource(hikariConfig());
	}
	//DBCP end
	
	
	//SQLSession : Connection�� �����ϰų� ���ϴ� SQL �� ����� ���Ϲ޴� ����
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource());
		return (SqlSessionFactory) sqlSessionFactory.getObject();
	}
	
	@Bean
	public DataSourceTransactionManager txManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	
}
