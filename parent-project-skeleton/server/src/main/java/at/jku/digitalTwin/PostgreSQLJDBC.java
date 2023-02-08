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
                //light.setRoom_id(rs.getString("roomid"));
                lights.add(light);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lights;
    }

    public Lights_Object addLight(Connection c, String room_id, Lights_Object lights_object) {
        Lights_Object addedLight = null;
        try {
            String sql = "INSERT INTO light (lightname, lightid, roomid) VALUES (?,?,?)";
            PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, lights_object.getName());
            stmt.setString(2, lights_object.getLight_id());
            stmt.setString(3, room_id);
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

    public Lights_Object getLightById(Connection c, String room_id, String light_id) {
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
                //light.setRoom_id(rs.getString("roomid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return light;
    }

    public boolean deleteLightById(Connection c, String room_id, String light_id) {
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

    public Lights_Object updateLightById(Connection c, String room_id, String light_id, Update_LightObject update_LightObject) {
        Lights_Object updatedLight = null;
        //Update_LightObject updatedLight = null;
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
                    //updatedLight = new Update_LightObject();
                    updatedLight.setLight_id(rs.getString("lightid"));
                    updatedLight.setName(rs.getString("lightname"));
                    //updatedLight.setStatus(rs.getString("status"));
                    //updatedLight.setRoom_id(rs.getString("roomid"));
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
            String sql = "SELECT * FROM lightstatus WHERE lightid = ?";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, light_id);
            //stmt.setString(2, room_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Light_Operation_Return_Object operation = new Light_Operation_Return_Object();
                operation.setId(rs.getInt("lightid"));
                operation.setTurnon(rs.getBoolean("lightison"));
                operation.setBrightness(rs.getInt("brightness"));
                operation.setHex(rs.getString("hex"));
                operation.setTime(rs.getTimestamp("lighttimestamp"));
                lightOperations.add(operation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lightOperations;
    }

    public boolean activateLight(Connection c, String room_id, String light_id, Light_Activation_Object light_activation_object) {
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        try {
            String sql = "INSERT INTO lightstatus (lightid, lightison, lighttimestamp, hex, brightness) VALUES (?,?,?,?,?)";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, light_id);
            stmt.setBoolean(2, light_activation_object.isTurnon());
            stmt.setTimestamp(3, timestamp);
            stmt.setString(4, "ffffe0");
            stmt.setInt(5, 50);
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
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());

        try {
            String sql = "INSERT INTO lightstatus (lightid, lightison, lighttimestamp, hex, brightness) VALUES (?,?,?,?,?)";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, light_id);
            stmt.setBoolean(2, light_operation_object.isTurnon());
            stmt.setTimestamp(3, timestamp);
            stmt.setString(4, light_operation_object.getHex());
            stmt.setInt(5, light_operation_object.getBrightness());
            int updatedRows = stmt.executeUpdate();
            if (updatedRows < 0) {
                return light_operation_object;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public Power_Plug_Object addVentilator(Connection c, String room_id, Power_Plug_Object powerPlugObject) {
        Power_Plug_Object addedVentilator = null;
        try {
            String sql = "INSERT INTO fan (roomid, fanid, fanname) VALUES (?, ?, ?)";
            PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, room_id);
            stmt.setString(2, powerPlugObject.getPlug_id());
            stmt.setString(3, powerPlugObject.getName());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    addedVentilator = powerPlugObject;
                    addedVentilator.setPlug_id(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addedVentilator;
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

    public boolean activateVentilator(Connection c, String plug_id, Power_Plug_Storing_Object plug) {
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        try {
            String sql = "INSERT INTO fanstatus (fanid, fanison, fantimestamp) VALUES (?,?,?)";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, plug_id);
            stmt.setBoolean(2, plug.isTurnon());
            stmt.setTimestamp(3, timestamp);

            int rowsAffected = stmt.executeUpdate();
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
            PreparedStatement st = c.prepareStatement("SELECT fanison, fantimestamp FROM fanstatus WHERE fanid = ?");
            //st.setString(1, room_id);
            st.setString(1, plug_id);
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




    public List<Door_Object> getDoors(Connection c, String room_id) {
        try {
            String sql = "SELECT * FROM door WHERE roomid = ?";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, room_id);
            ResultSet rs = stmt.executeQuery();
            List<Door_Object> doors = new ArrayList<>();
            while (rs.next()) {
                String door_id = rs.getString("doorid");
                String name = rs.getString("doorname");
                Door_Object door = new Door_Object(door_id, name);
                doors.add(door);
            }
            return doors;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Door_Object addDoor(Connection c, String room_id, Door_Object doorObject) {
        Door_Object addedDoor = null;
        try {
            String sql = "INSERT INTO door (roomid, doorid, doorname) VALUES (?, ?, ?)";
            PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, room_id);
            stmt.setString(2, doorObject.getDoor_id());
            stmt.setString(3, doorObject.getName());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    addedDoor = doorObject;
                    addedDoor.setDoor_id(rs.getString(1));
                }
            }
        } catch(SQLException e){
                e.printStackTrace();
        }

        return addedDoor;
    }

    public Door_Object getDoor(Connection c, String door_id) {
        Door_Object door_object = null;
        try {
            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM door WHERE doorid = ?");
            pstmt.setString(1, door_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                door_object = new Door_Object(rs.getString("doorid"), rs.getString("doorname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return door_object;
    }

    public boolean deleteDoor(Connection c, String door_id) {
        try {
            PreparedStatement st = c.prepareStatement("DELETE FROM door WHERE doorid = ?");
            st.setString(1, door_id);
            int rowsAffected = st.executeUpdate();
            if(rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean openDoor (Connection c, String door_id, Open_Door_Object doorObject) {
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        try {
            String sql = "INSERT INTO doorstatus (doorid, doorisopen, doortimestamp) VALUES (?,?,?)";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, door_id);
            stmt.setBoolean(2, doorObject.isOpen());
            stmt.setTimestamp(3, timestamp);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Open_Door_Operation_Object> getDoorOperations(Connection c, String room_id, String door_id) {
        List<Open_Door_Operation_Object> doorOperations = new ArrayList<>();
        try {
            String sql = "SELECT * FROM doorstatus WHERE doorid = ?";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, door_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Open_Door_Operation_Object operation = new Open_Door_Operation_Object();
                operation.setOpen(rs.getBoolean("doorisopen"));
                operation.setTime(rs.getString("doortimestamp"));
                doorOperations.add(operation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doorOperations;
    }









    public List<Window_Object> getWindows(Connection c, String room_id) {
        try {
            String sql = "SELECT * FROM windows WHERE roomid = ?";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, room_id);
            ResultSet rs = stmt.executeQuery();
            List<Window_Object> windows = new ArrayList<>();
            while (rs.next()) {
                String window_id = rs.getString("windowid");
                String name = rs.getString("windowname");
                Window_Object window = new Window_Object(window_id, name);
                windows.add(window);
            }
            return windows;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Window_Object> addWindow(Connection c, String room_id, Window_Object windowObject) {
        PreparedStatement ps = null;
        try {
            ps = c.prepareStatement("INSERT INTO windows (roomid, windowid, windowname) VALUES (?, ?, ?)");
            ps.setString(1, room_id);
            ps.setString(2, windowObject.getWindow_id());
            ps.setString(3, windowObject.getName());
            ps.executeUpdate();
            return getWindows(c, room_id);
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

    public Window_Object getWindow(Connection c, String window_id) {
        Window_Object window_object = null;
        try {
            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM windows WHERE windowid = ?");
            pstmt.setString(1, window_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                window_object = new Window_Object(rs.getString("windowid"), rs.getString("windowname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return window_object;
    }

    public boolean deleteWindow(Connection c, String window_id) {
        try {
            PreparedStatement st = c.prepareStatement("DELETE FROM windows WHERE windowid = ?");
            st.setString(1, window_id);
            int rowsAffected = st.executeUpdate();
            if(rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean openWindow (Connection c, String window_id, Open_Window_Object windowObject) {
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        try {
            String sql = "INSERT INTO windowstatus (windowid, windowisopen, windowtimestamp) VALUES (?,?,?)";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, window_id);
            stmt.setBoolean(2, windowObject.isOpen());
            stmt.setTimestamp(3, timestamp);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Open_Window_Operation_Object> getWindowOperations(Connection c, String room_id, String window_id) {
        List<Open_Window_Operation_Object> windowOperations = new ArrayList<>();
        try {
            String sql = "SELECT * FROM doorstatus WHERE doorid = ?";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, window_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Open_Window_Operation_Object operation = new Open_Window_Operation_Object();
                operation.setOpen(rs.getBoolean("windowisopen"));
                operation.setTime(rs.getString("windowtimestamp"));
                windowOperations.add(operation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return windowOperations;
    }




}