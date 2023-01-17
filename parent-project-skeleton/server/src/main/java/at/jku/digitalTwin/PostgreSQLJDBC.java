package at.jku.digitalTwin;

import at.jku.digitalTwin.objects.*;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostgreSQLJDBC {

    //DATABASE CONNECTION
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

    //ROOMS
    public List<Room_Object> getAllRooms(Connection c, String table_name) {
        List<Room_Object> room = new ArrayList<>();
        Statement statement;
        ResultSet rs = null;
        try{
             String query = String.format("select * from room");
             statement = c.createStatement();
             rs = statement.executeQuery(query);
             while (rs.next()) {
                String roomId = rs.getString("roomid");
                double roomSize = rs.getDouble("size");
                String measurementUnit = rs.getString("unit");
                room.add(new Room_Object(roomId, roomSize, measurementUnit));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }

    public void add_room(Connection c, String table_name, String roomid, double size, String unit){
        Statement statement;
        try {
            String query = String.format("insert into %s(roomid, size, unit) values ('%s','%s','%s');", table_name, roomid, size, unit);
            statement = c.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row Inserted");
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public Room_Object getRoomById(Connection c, String room_id) {
        try (PreparedStatement statement = c.prepareStatement("SELECT roomid, size, unit FROM room WHERE roomid = ?")) {
             statement.setString(1, room_id);
             ResultSet rs = statement.executeQuery();
             if(rs.next()){
                String id = rs.getString("roomid");
                double size = rs.getDouble("size");
                String unit = rs.getString("unit");
                return new Room_Object(id, size, unit);
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Room_Object deleteRoomById(Connection c, String room_id) {
        Room_Object deletedRoom = getRoomById(c, room_id);
        if(deletedRoom == null) {
            return null;
        }
        try (PreparedStatement statement = c.prepareStatement("DELETE FROM room WHERE roomid = ?")) {
            statement.setString(1, room_id);
            statement.executeUpdate();
            return deletedRoom;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Room_Object updateRoomById(Connection c, String room_id, Update_RoomObject update_roomObject) {
        Room_Object updatedRoom = null;
        try {
            String sql = "UPDATE room SET size = ?, unit = ? WHERE roomid = ?";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setDouble(1, update_roomObject.getRoom_size());
            stmt.setString(2, update_roomObject.getMeasurement_unit());
            stmt.setString(3, room_id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 1) {
                // retrieve the updated room from the database
                sql = "SELECT * FROM room WHERE roomid = ?";
                stmt = c.prepareStatement(sql);
                stmt.setString(1, room_id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    updatedRoom = new Room_Object();
                    updatedRoom.setRoom_id(rs.getString("roomid"));
                    updatedRoom.setRoom_size(rs.getDouble("size"));
                    updatedRoom.setMeasurement_unit(rs.getString("unit"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedRoom;
    }

    public PeopleInRoomObject getPeopleInRoomById(Connection c, String room_id) {
        PeopleInRoomObject peopleInRoom = null;
        try {
            String sql = "SELECT * FROM peopleinroom WHERE peopleroomid = ?";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, room_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                peopleInRoom = new PeopleInRoomObject();
                peopleInRoom.setRoom_id(rs.getString("peopleroomid"));
                peopleInRoom.setPeople_count(rs.getInt("nopeopleinroom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peopleInRoom;
    }

    public PeopleInRoomObject addPeopleInRoomById(Connection c, String room_id, PeopleInRoomObject peopleInRoomObject) {
        PeopleInRoomObject addedPeopleInRoom = null;
        try {
            String sql = "INSERT INTO peopleinroom (peopleroomid, nopeopleinroom) VALUES (?, ?)";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, room_id);
            stmt.setInt(2, peopleInRoomObject.getPeople_count());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 1) {
                addedPeopleInRoom = peopleInRoomObject;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addedPeopleInRoom;
    }

    //LIGHTS
    public List<Lights_Object> getLightsByRoomId(Connection c, String room_id) {
        List<Lights_Object> lights = new ArrayList<>();
        try {
            String sql = "SELECT * FROM light WHERE roomid = ?";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, room_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Lights_Object light = new Lights_Object();
                light.setLight_id(rs.getString("lightid"));
                light.setName(rs.getString("lightname"));
                //light.setStatus(rs.getString("status"));
                light.setRoom_id(rs.getString("roomid"));
                lights.add(light);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lights;
    }

    public Lights_Object addLight(Connection c, Lights_Object lights_object) {
        Lights_Object addedLight = null;
        try {
            String sql = "INSERT INTO light (lightname, lightid, roomid) VALUES (?,?,?)";
            PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, lights_object.getName());
            stmt.setString(2, lights_object.getLight_id());
            stmt.setString(3, lights_object.getRoom_id());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    addedLight = lights_object;
                    addedLight.setLight_id(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addedLight;
    }

    public Lights_Object getLightById(Connection c, String light_id) {
        Lights_Object light = null;
        try {
            String sql = "SELECT * FROM light WHERE lightid = ?";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, light_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                light = new Lights_Object();
                light.setLight_id(rs.getString("lightid"));
                light.setName(rs.getString("lightname"));
                //light.setStatus(rs.getString("status"));
                light.setRoom_id(rs.getString("roomid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return light;
    }

    public boolean deleteLightById(Connection c, String light_id) {
        boolean isDeleted = false;
        try {
            String sql = "DELETE FROM light WHERE lightid = ?";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, light_id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 1) {
                isDeleted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    public Lights_Object updateLightById(Connection c, String light_id, Update_LightObject update_LightObject) {
        Lights_Object updatedLight = null;
        try {
            String sql = "UPDATE light SET lightname = ? WHERE lightid = ?";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, update_LightObject.getName());
            //stmt.setString(2, update_LightObject.getStatus());
            stmt.setString(2, light_id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 1) {
                // retrieve the updated light from the database
                sql = "SELECT * FROM light WHERE lightid = ?";
                stmt = c.prepareStatement(sql);
                stmt.setString(1, light_id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    updatedLight = new Lights_Object();
                    updatedLight.setLight_id(rs.getString("lightid"));
                    updatedLight.setName(rs.getString("lightname"));
                    //updatedLight.setStatus(rs.getString("status"));
                    updatedLight.setRoom_id(rs.getString("roomid"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedLight;
    }

    public List<Light_Operation_Return_Object> getLightOperations(Connection c, String room_id, String light_id) {
        List<Light_Operation_Return_Object> lightOperations = new ArrayList<>();
        try {
            String sql = "SELECT * FROM lightstatus WHERE lightid = ? and roomid = ?";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, light_id);
            stmt.setString(2, room_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Light_Operation_Return_Object operation = new Light_Operation_Return_Object();
                operation.setId(rs.getInt("lightid"));
                operation.setTurnon(rs.getBoolean("lightison"));
                operation.setBrightness(rs.getInt("brightness"));
                operation.setHex(rs.getString("hex"));
                operation.setTime(rs.getTimestamp("lighttimestamp").toLocalDateTime());
                lightOperations.add(operation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lightOperations;
    }

    public boolean activateLight(Connection c, String room_id, String light_id, Light_Activation_Object light_activation_object) {
        try {
            String sql = "UPDATE lightstatus SET lightison = ? FROM lightstatus as ls JOIN light ON ls.lightid = light.lightid WHERE ls.lightid = ? and light.roomid = ?";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setBoolean(1, light_activation_object.isTurnon());
            stmt.setString(2, light_id);
            stmt.setString(3, room_id);
            int updatedRows = stmt.executeUpdate();
            if (updatedRows > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Light_Operation_Object setColor(Connection c, String room_id, String light_id, Light_Operation_Object light_operation_object) {
        try {
            String sql = "UPDATE lightstatus SET hex = ? WHERE lightid = ? and roomid = ?";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, light_operation_object.getHex());
            stmt.setString(2, light_id);
            stmt.setString(3, room_id);
            int updatedRows = stmt.executeUpdate();
            if (updatedRows == 0) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return light_operation_object;
    }

    //VENTILATORS
    public List<Power_Plug_Object> getVentilators(Connection c, String room_id) {
        try {
            String sql = "SELECT * FROM fan WHERE roomid = ?";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, room_id);
            ResultSet rs = stmt.executeQuery();
            List<Power_Plug_Object> ventilators = new ArrayList<>();
            while (rs.next()) {
                String plug_id = rs.getString("fanid");
                String name = rs.getString("fanname");
                Power_Plug_Object ventilator = new Power_Plug_Object(plug_id, name);
                ventilators.add(ventilator);
            }
            return ventilators;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Power_Plug_Object> addVentilator(Connection c, String room_id, Power_Plug_Object powerPlugObject) {
        PreparedStatement ps = null;
        try {
            ps = c.prepareStatement("INSERT INTO fan (roomid, fanid, fanname) VALUES (?, ?, ?)");
            ps.setString(1, room_id);
            ps.setString(2, powerPlugObject.getPlug_id());
            ps.setString(3, powerPlugObject.getName());
            ps.executeUpdate();
            return getVentilators(c, room_id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Power_Plug_Object getVentilator(Connection c, String plug_id) {
        Power_Plug_Object power_plug_object = null;
        try {
            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM fan WHERE fanid = ?");
            pstmt.setString(1, plug_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                power_plug_object = new Power_Plug_Object(rs.getString("fanid"), rs.getString("fanname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return power_plug_object;
    }

    public boolean deleteVentilator(Connection c, String plug_id) {
        try {
            PreparedStatement st = c.prepareStatement("DELETE FROM fan WHERE fanid = ?");
            st.setString(1, plug_id);
            int rowsAffected = st.executeUpdate();
            if(rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateVentilator(Connection c, String plug_id, Update_PlugObject update_plugObject) {
        try {
            PreparedStatement statement = c.prepareStatement("UPDATE fan SET fanname = ? WHERE fanid = ?");
            statement.setString(1, update_plugObject.getName());
            statement.setString(2, plug_id);

            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean activateVentilator(Connection c, String room_id, String plug_id) {
        try {
            PreparedStatement statement = c.prepareStatement("UPDATE fanstatus SET fanison = true WHERE fanid = ? AND roomid = ?");
            statement.setString(1, plug_id);
            statement.setString(2, room_id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Power_Plug_Operation_Object> getVentilatorOperations(Connection c, String room_id, String plug_id) {
        List<Power_Plug_Operation_Object> operations = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT fanison, fantimestamp FROM fanstatus WHERE roomid = ? AND fanid = ?");
            st.setString(1, room_id);
            st.setString(2, plug_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                boolean turnon = rs.getBoolean("fanison");
                LocalDateTime time = rs.getTimestamp("fantimestamp").toLocalDateTime();
                operations.add(new Power_Plug_Operation_Object(turnon, time));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(operations.isEmpty()){
            return null;
        }
        return operations;
    }


}