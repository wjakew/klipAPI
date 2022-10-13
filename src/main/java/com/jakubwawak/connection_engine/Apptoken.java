/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.connection_engine;

import com.jakubwawak.klipAPI.KlipApiApplication;
import com.jakubwawak.maintanance.RandomString;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

/**
 * Object for checking/maintaining apptoken data
 */
public class Apptoken {

    /**
     * CREATE TABLE APPTOKEN
     * (
     *     apptoken_id INT AUTO_INCREMENT PRIMARY KEY,
     *     apptoken_code VARCHAR(20),
     *     apptoken_mac VARCHAR(20),
     *     apptoken_time TIMESTAMP
     * );
     */

    public String apptoken_code;
    public String apptoken_mac;

    public int apptoken_id;
    /**
     * Constructor
     * @param apptoken_code
     * @param apptoken_mac
     */
    public Apptoken(String apptoken_code,String apptoken_mac){
        this.apptoken_code = apptoken_code;
        this.apptoken_mac = apptoken_mac;
        this.apptoken_id = -1;
    }

    /**
     * Constructor
     * @param apptoken_mac
     */
    public Apptoken(String apptoken_mac){
        this.apptoken_code = "";
        this.apptoken_mac = apptoken_mac;
        this.apptoken_id = -1;
    }

    public Apptoken (int apptoken_id){
        this.apptoken_code = "";
        this.apptoken_mac = "";
        this.apptoken_id = apptoken_id;
    }

    /**
     * Constructor
     */
    public Apptoken(){
        this.apptoken_code = "";
        this.apptoken_mac = "";
    }

    /**
     * Function for creating apptoken for given MAC Address
     * @return Integer
     * return codes:
     *  1 - apptoken created
     * -1 - database error
     */
    public int create_apptoken(){
        String query = "INSERT INTO APPTOKEN (apptoken_code,apptoken_mac,apptoken_time) VALUES (?,?,?);";
        try{
            PreparedStatement ppst = KlipApiApplication.database.con.prepareStatement(query);
            RandomString randomString = new RandomString(20);
            ppst.setString(1,randomString.buf);
            ppst.setString(2,apptoken_mac);
            ppst.setObject(3, LocalDateTime.now(ZoneId.of("Europe/Warsaw")));
            KlipApiApplication.database.nl.add("APPTOKEN-CREATE","Created apptoken for "+apptoken_mac+" CODE: "+randomString.buf);
            ppst.execute();
            return 1;
        }catch(SQLException e){
            KlipApiApplication.database.nl.add("APPTOKEN-CREATE-FAILED","Failed to create apptoken entry on database ("+e.toString()+")");
            return -1;
        }
    }

    /**
     * Function for removing apptoken data from database
     * @return Integer
     * return codes:
     *  1 - apptoken removed
     * -1 - database error
     */
    public int remove_apptoken(){
        String query = "DELETE FROM APPTOKEN WHERE apptoken_id = ?;";
        try{
            PreparedStatement ppst = KlipApiApplication.database.con.prepareStatement(query);
            ppst.setInt(1,apptoken_id);
            ppst.execute();
            KlipApiApplication.database.nl.add("APPTOKEN-REMOVED","Removed apptoken data for apptoken_id:"+apptoken_id);
            return 1;
        }catch(SQLException e){
            KlipApiApplication.database.nl.add("APPTOKEN-REMOVE-FAILED","Failed to remove apptoken ("+e.toString()+")");
            return -1;
        }
    }

    /**
     * Function for checking given apptoken
     * @return Integer
     * flag:
     *  2 - apptoken correct
     *  0 - apptoken incorrect
     * -1 - wrong apptoken and mac
     */
    public int check_apptoken(){
        String query = "SELECT apptoken_id FROM APPTOKEN WHERE apptoken_code = ? and apptoken_mac = ?;";
        try{
            PreparedStatement ppst = KlipApiApplication.database.con.prepareStatement(query);
            ppst.setString(1,apptoken_code);
            ppst.setString(2,apptoken_mac);
            ResultSet rs = ppst.executeQuery();
            if ( rs.next() ){
                return rs.getInt("apptoken_id");
            }
            return 0;
        }catch(SQLException e){
            KlipApiApplication.database.nl.add("APPTOKEN-CHECK-FAILED","Failed to check API ("+e.toString()+")");
            return -1;
        }
    }

    /**
     * Function for listing apptoken data
     * @return ArrayList
     */
    public ArrayList<String> show_apptoken_list(){
        String query = "SELECT * FROM APPTOKEN;";
        ArrayList<String> data = new ArrayList<>();
        try{
            PreparedStatement ppst = KlipApiApplication.database.con.prepareStatement(query);
            ResultSet rs = ppst.executeQuery();
            while(rs.next()){
                data.add("id: "+rs.getInt("apptoken_id")+": "+rs.getString("apptoken_mac")+" code: "+rs.getString("apptoken_code"));
            }
        }catch(SQLException e){
            KlipApiApplication.database.nl.add("APPTOKEN-LIST-FAILED","Failed to list apptokens ("+e.toString()+")");
        }
        return data;
    }
}
