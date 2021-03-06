package io.insight.jgit.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.insight.jgit.services.KVAdapter;
import org.jooq.SQLDialect;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MysqlRepoManager extends JdbcRepoManager {

  private final DataSource ds;
  public MysqlJdbcAdapter jdbcAdapter;

  public MysqlRepoManager(String jdbcUrl, String user, String password) {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(jdbcUrl);
    config.setUsername(user);
    config.setPassword(password);
    ds = new HikariDataSource(config);
  }

  public MysqlRepoManager(DataSource ds) {
    this.ds = ds;
  }

  @Override
  public JdbcAdapter adapter() {
    if (jdbcAdapter == null) {
      jdbcAdapter = new MysqlJdbcAdapter();
    }
    return jdbcAdapter;
  }

  private class MysqlJdbcAdapter extends JdbcAdapter {
    @Override
    SQLDialect dialect() {
      return SQLDialect.MYSQL;
    }

    @Override
    protected Connection getConnection() throws SQLException {
      return ds.getConnection();
    }
  }
}
