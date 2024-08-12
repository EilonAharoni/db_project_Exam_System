package db_proj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Check if username exists in the database
    public boolean usernameExists(Connection conn, String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Validate the user by checking the username and password in the database
    public boolean validateUser(Connection conn, String username, String password) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Register a new user in the database
    public void registerUser(Connection conn, User user) throws SQLException {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
        }
    }
}
