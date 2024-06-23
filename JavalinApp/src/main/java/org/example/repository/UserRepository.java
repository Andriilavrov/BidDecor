package org.example.repository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.example.model.User;

public class UserRepository {
    private String url;
    private String serverUser;
    private String password;

    public UserRepository() {
        try {
            InputStream input = this.getClass().getClassLoader().getResourceAsStream("application.properties");

            label58: {
                try {
                    Properties prop = new Properties();
                    if (input == null) {
                        System.out.println("Sorry, unable to find application.properties");
                        break label58;
                    }

                    prop.load(input);
                    this.url = prop.getProperty("db.url");
                    this.serverUser = prop.getProperty("db.user");
                    this.password = prop.getProperty("db.password");
                } catch (Throwable var5) {
                    if (input != null) {
                        try {
                            input.close();
                        } catch (Throwable var4) {
                            var5.addSuppressed(var4);
                        }
                    }

                    throw var5;
                }

                if (input != null) {
                    input.close();
                }

                return;
            }

            if (input != null) {
                input.close();
            }

        } catch (IOException var6) {
            var6.printStackTrace();
        }
    }

    public List<User> findAll() {
        List<User> users = new ArrayList();

        try {
            Connection connection = DriverManager.getConnection(this.url, this.serverUser, this.password);

            try {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Users");

                try {
                    ResultSet rs = stmt.executeQuery();

                    while(rs.next()) {
                        users.add(new User(rs.getInt("userId"), rs.getString("userName"), rs.getString("email"), rs.getString("password")));
                    }
                } catch (Throwable var8) {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (Throwable var7) {
                            var8.addSuppressed(var7);
                        }
                    }

                    throw var8;
                }

                if (stmt != null) {
                    stmt.close();
                }
            } catch (Throwable var9) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Throwable var6) {
                        var9.addSuppressed(var6);
                    }
                }

                throw var9;
            }

            if (connection != null) {
                connection.close();
            }
        } catch (Exception var10) {
            var10.printStackTrace();
        }

        return users;
    }

    public void save(User user) {
        try {
            Connection connection = DriverManager.getConnection(this.url, this.serverUser, this.password);

            try {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO Users (userName, email, password) VALUES (?, ?, ?)");

                try {
                    stmt.setString(1, user.getUserName());
                    stmt.setString(2, user.getEmail());
                    stmt.setString(3, user.getPassword());
                    stmt.executeUpdate();
                } catch (Throwable var8) {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (Throwable var7) {
                            var8.addSuppressed(var7);
                        }
                    }

                    throw var8;
                }

                if (stmt != null) {
                    stmt.close();
                }
            } catch (Throwable var9) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Throwable var6) {
                        var9.addSuppressed(var6);
                    }
                }

                throw var9;
            }

            if (connection != null) {
                connection.close();
            }
        } catch (Exception var10) {
            var10.printStackTrace();
        }

    }
}
