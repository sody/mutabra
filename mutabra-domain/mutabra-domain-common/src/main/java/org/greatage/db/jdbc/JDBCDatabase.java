//package org.greatage.db;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
///**
// * @author Ivan Khalopik
// * @since 1.0
// */
//public class JDBCDatabase implements Database {
//	private final DataSource dataSource;
//
//	public JDBCDatabase(final DataSource dataSource) {
//		this.dataSource = dataSource;
//	}
//
//	public InsertBuilder insert(final String entityName) {
//		return new JDBCInsert(entityName);
//	}
//
//	private <T> T execute(final String sql, final PreparedStatementCallback<T> callback) {
//		Connection connection = null;
//		PreparedStatement statement = null;
//		try {
//			connection = dataSource.getConnection();
//			statement = connection.prepareStatement(sql);
//			return callback.doInStatement(statement);
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		} finally {
//			if (statement != null) {
//				try {
//					statement.close();
//				} catch (SQLException e) {
//					// do nothing
//				}
//			}
//			if (connection != null) {
//				try {
//					connection.close();
//				} catch (SQLException e) {
//					// do nothing
//				}
//			}
//		}
//	}
//
//	class JDBCInsert implements InsertBuilder {
//		private static final String SQL = "insert into %s (%s) values (%s);";
//
//		private final String table;
//		private final StringBuilder propertyNames = new StringBuilder();
//		private final StringBuilder values = new StringBuilder();
//
//		JDBCInsert(final String table) {
//			this.table = table;
//		}
//
//		public InsertBuilder set(final String propertyName, final Object value) {
//			if (propertyNames.length() > 0) {
//				propertyNames.append(',');
//				values.append(',');
//			}
//			propertyNames.append(propertyName);
//			values.append("?");
//			return this;
//		}
//
//		public Database end() {
//			final String sql = String.format(SQL, table, propertyNames, values);
//			execute(sql, new PreparedStatementCallback<Object>() {
//				public Object doInStatement(final PreparedStatement statement) throws SQLException {
//					return null;
//				}
//			});
//			return JDBCDatabase.this;
//		}
//	}
//
//	interface PreparedStatementCallback<T> {
//		T doInStatement(PreparedStatement statement) throws SQLException;
//	}
//}
