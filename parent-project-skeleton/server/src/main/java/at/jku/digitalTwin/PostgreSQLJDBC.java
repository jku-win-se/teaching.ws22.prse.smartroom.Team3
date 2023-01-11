package at.jku.digitalTwin;

import java.sql.*;

public class PostgreSQLJDBC {
    public Connection connect_to_db(String dbname, String user, String password){
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, password);
            if(c != null){
                System.out.println("Connection Established");
            }
            else{
                System.out.println("Connection Failed");
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return c;
    }

    public void read_room(Connection c, String table_name){
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from %s", table_name);
            statement = c.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString("roomid") + " ");
                System.out.println(rs.getString("size") + " ");
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void add_room(Connection c, String table_name, String roomid, int size){
        Statement statement;
        try {
            String query=String.format("insert into %s(roomid, size) values ('%s','%s');", table_name,roomid,size);
            statement=c.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row Inserted");
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void delete_room(Connection c, String table_name, String roomid){
        Statement statement;
        try {
            String query=String.format("delete from %s where roomid='%s'", table_name,roomid);
            statement=c.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row Deleted");
        } catch (Exception e){
            System.out.println(e);
        }
    }
}