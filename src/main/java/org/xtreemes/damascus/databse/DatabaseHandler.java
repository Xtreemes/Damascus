package org.xtreemes.damascus.databse;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xtreemes.damascus.Damascus;
import org.xtreemes.damascus.Rank;

import java.io.File;
import java.sql.*;
import java.util.logging.Level;

public class DatabaseHandler {

    private static final String DATABASE_DIRECTORY = "damascus/";
    private static Connection connection;
    public static boolean connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            File data_dir = new File(DATABASE_DIRECTORY);
            boolean exists = true;
            if(!data_dir.exists()){
                data_dir.mkdirs();
                exists = false;
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_DIRECTORY + "database.db");
            if(!exists){
                return initializeDatabase();
            }
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            return false;
        }
    }
    public static void disconnect() {
        try {
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static boolean initializeDatabase(){
        try {
            connection.createStatement().execute("""
                    CREATE TABLE
                        players (
                            uuid TEXT PRIMARY KEY,
                            rank TEXT
                        )
                    ;""");
            connection.createStatement().execute("""
                    CREATE TABLE
                        plots (
                            id TEXT PRIMARY KEY,
                            owner TEXT,
                            code TEXT
                        )
                    ;""");
            connection.commit();
            Damascus.PLUGIN.getLogger().log(Level.FINE, "Database initialized!");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    // PLAYER RELATED METHODS

    /**
     * Checks if a player is in the database.
     * If not, adds them to it.
     * If they are, does nothing.
     *
     * @param uuid UUID as string of player to check
     */
    public static void addPlayer(String uuid){
        try {
            String query = "SELECT COUNT(*) AS count FROM players WHERE uuid = ?";
            PreparedStatement prep_state = connection.prepareStatement(query);
            prep_state.setString(1, uuid);
            ResultSet result = prep_state.executeQuery();
            if(result.next()){
                int count = result.getInt("count");
                if(!(count>0)){
                    String add = "INSERT INTO players (uuid, rank) VALUES (?, ?)";
                    PreparedStatement statement = connection.prepareStatement(add);
                    statement.setString(1,uuid);
                    statement.setString(2,"DEFAULT");
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Rank getPlayerRank(String uuid){
        try {
            String query = "SELECT rank FROM players WHERE uuid = ?;";
            PreparedStatement prep = connection.prepareStatement(query);
            prep.setString(1, uuid);
            ResultSet result = prep.executeQuery();
            if(result.next()){
                String rank = result.getString("rank");
                if(rank != null){
                    return Rank.valueOf(rank);
                } else {
                    return Rank.DEFAULT;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Rank.DEFAULT;
    }
    public static boolean setPlayerRank(String uuid, Rank rank){
        try {
            String update = "UPDATE players SET rank = ? WHERE uuid = ?;";
            PreparedStatement prep = connection.prepareStatement(update);
            prep.setString(1, rank.name());
            prep.setString(2, uuid);
            prep.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    // PLOT RELATED METHODS
    public static JSONArray getPlotJSON(String id){
        try {
            String query = "SELECT code FROM plots WHERE id = ?;";
            PreparedStatement prep = connection.prepareStatement(query);
            prep.setString(1, id);
            ResultSet result = prep.executeQuery();
            if(result.next()){
                String code_json = result.getString("code");
                JSONParser parser = new JSONParser();
                JSONArray array = (JSONArray) parser.parse(code_json);
                return array;
            }
            return null;
        } catch (SQLException | ParseException e) {
            return null;
        }
    }
    public static void setPlotJSON(JSONArray array, String id){
        try {
            String check_query = "SELECT COUNT(*) AS count FROM plots WHERE id = ?";
            PreparedStatement prep_check = connection.prepareStatement(check_query);
            prep_check.setString(1,id);
            ResultSet result = prep_check.executeQuery();
            if(result.next()){
                if(result.getInt("count")==0){
                    String add = "INSERT INTO plots (id) VALUES (?)";
                    PreparedStatement add_state = connection.prepareStatement(add);
                    add_state.setString(1,id);
                    add_state.executeUpdate();
                }
            }
            String query = "UPDATE plots SET code = ? WHERE id = ?;";
            PreparedStatement prep = connection.prepareStatement(query);
            prep.setString(1, array.toJSONString());
            prep.setString(2, id);
            prep.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
