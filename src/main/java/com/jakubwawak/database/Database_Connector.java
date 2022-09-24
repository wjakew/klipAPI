/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.database;

import com.jakubwawak.maintanance.Configuration_Service;
import com.jakubwawak.maintanance.LoGrabber;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

/**
 * Object for connecting to database
 */

public class Database_Connector {
    Configuration_Service cs;
    LoGrabber nl;
    String logged_user_login;

    public boolean connected;
    public LocalDateTime run_time;

    public Connection con;


    /**
     * Constructor
     * @param cs
     */
    public Database_Connector(Configuration_Service cs, LoGrabber nl){
        this.cs = cs;
        this.nl = nl;
        logged_user_login = "";
    }

    /**
     * Function for connecting service to database
     */
    public void connect(){
        if ( !cs.empty() ){
            nl.add("Trying to connect to database with user: "+cs.klip_raw_database_user,"DATABASE-CONNECTOR");
            String login_data = "jdbc:mysql://"+cs.klip_raw_database+"/"+cs.klip_raw_database_name+"?"
                    + "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&" +
                    "user="+cs.klip_raw_database_user+"&password="+cs.klip_raw_database_password;
            try{
                con = DriverManager.getConnection(login_data);
                run_time = LocalDateTime.now( ZoneId.of( "Europe/Warsaw" ) );
                nl.add("Connected succesfully","CONNECTION");
                nl.add(login_data.substring(0,login_data.length()-25)+"...*END*","CONNECTION");
                connected = true;
            }catch(SQLException e){
                connected = false;
                nl.add("Failed to connect to database ("+e.toString()+")","ERROR-DB01");
            }
        }
        else{
            nl.add("Failed to connect, configuration file is empty.","DATABASE-CONFIGURATION-EMPTY");
        }
    }

    /**
     * Function for loading and getting health data
     * @return ArrayList
     */
    public ArrayList<String> get_health_data(){
        ArrayList<String> data = new ArrayList<>();
        String query = "SELECT * FROM HEALTH;";
        try{
            PreparedStatement ppst = con.prepareStatement(query);
            ResultSet rs = ppst.executeQuery();
            if ( rs.next() ){
                data.add(rs.getString("health_database_version"));
                data.add(rs.getString("health_database_status"));
                data.add(rs.getString("health_database_enable"));
                nl.add("Loaded health data from database","HEALTH-LOAD");
            }
            else{
                nl.add("Cannot load health data. Problem with database","HEALTH-ERROR");
            }
        }catch(SQLException e){
            nl.add("Failed to get health data ("+e.toString()+")","HEALTH-FAILED");
        }
        return data;
    }

    /**
     * Check if api is set to enabled
     * @return boolean
     * return codes:
     *  0 - API disabled
     *  1 - API enabled
     * -1 - health data error on database (no data in health table)
     * -2 - database error during health table select
     */
    public int check_api_enabled(){
        String query = "SELECT health_database_enable FROM HEALTH;";
        try{
            PreparedStatement ppst = con.prepareStatement(query);
            ResultSet rs = ppst.executeQuery();
            if (rs.next()){
                nl.add("API enabled flag set to: "+rs.getInt("health_database_enable"),"API-ENABLED");
                return rs.getInt("health_database_enable");
            }
            return -1;
        }catch(SQLException e){
            nl.add("Failed to check api enabled! ("+e.toString()+")","API-ENABLED-FAILED");
            return -2;
        }
    }


}
