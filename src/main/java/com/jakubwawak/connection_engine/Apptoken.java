/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.connection_engine;

import com.jakubwawak.klipAPI.KlipApiApplication;
import com.jakubwawak.klipAPI.KlipApiMenu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    String apptoken_code;
    String apptoken_mac;

    /**
     * Constructor
     * @param apptoken_code
     * @param apptoken_mac
     */
    public Apptoken(String apptoken_code,String apptoken_mac){
        this.apptoken_code = apptoken_code;
        this.apptoken_mac = apptoken_mac;
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
        }
    }
}
