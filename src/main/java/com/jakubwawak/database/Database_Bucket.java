/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.database;

import com.jakubwawak.maintanance.Password_Validator;
import com.jakubwawak.maintanance.RandomString;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Database Connector to maintain bucket data on database
 */
public class Database_Bucket {

    public Database_Connector database;

    /**
     * Constructor
     * @param database
     */
    public Database_Bucket(Database_Connector database){
        this.database = database;
    }

    /**
     * Function for creating bucket on database
     * @param bucket_note
     * @param bucket_password
     * @return Integer
     */
    public int create_bucket(String bucket_note,String bucket_password){
        /**
         * CREATE TABLE BUCKET
         * (
         *     bucket_id INT AUTO_INCREMENT PRIMARY KEY,
         *     bucket_sid VARCHAR(20),
         *     bucket_archived INT,
         *     bucket_time TIMESTAMP,
         *     bucket_note TEXT,
         *     bucket_hash_password VARCHAR(100)
         * ) AUTO_INCREMENT = 10000;
         */

        String query = "INSERT INTO BUCKET (bucket_sid,bucket_archived,bucket_time,bucket_note,bucket_hash_password) VALUES(?,?,?,?,?);";
        try{
            PreparedStatement ppst = database.con.prepareStatement(query);
            RandomString rs = new RandomString(10);
            ppst.setString(1,rs.buf);
            ppst.setInt(2,0);
            ppst.setObject(3, LocalDateTime.now(ZoneId.of("Europe/Warsaw")));
            ppst.setString(4,bucket_note);
            Password_Validator password = new Password_Validator(bucket_password);
            ppst.setString(5,password.hash());
            ppst.execute();
            database.nl.add("BUCKET-CREATE","New bucket was created! Bucket SID: "+rs.buf);
            return 1;
        }catch(Exception e){
            database.nl.add("BUCKET-CREATE-FAILED","Failed to create bucket ("+e.toString()+")");
            return -1;
        }
    }

    /**
     * Function for archiving bucket on database
     * @param bucket_id
     * @param bucket_hash_password
     * @return Integer
     */
    public int archive_bucket(int bucket_id,String bucket_hash_password){
        String query = "UPDATE BUCKET SET bucket_archived = 1 WHERE bucket_id = ? and bucket_hash_password = ?;";
        try{
            PreparedStatement ppst = database.con.prepareStatement(query);
            ppst.setInt(1,bucket_id);
            ppst.setString(2,bucket_hash_password);
            ppst.execute();
            database.nl.add("BUCKET-ARCHIVED","Bucket was archived! (bucket_id: "+bucket_id+")");
            return 1;
        }catch(SQLException e){
            database.nl.add("BUCKET-ARCHIVED-FAILED","Failed to archive bucket data ("+e.toString()+")");
            return -1;
        }
    }
}
