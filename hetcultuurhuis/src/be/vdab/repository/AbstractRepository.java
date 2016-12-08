package be.vdab.repository;

import javax.sql.DataSource;

public class AbstractRepository {
	public final static String JNDI_NAME = "jdbc/cultuurhuis";
	protected DataSource dataSource;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}

}
