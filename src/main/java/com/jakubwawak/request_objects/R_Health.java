/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.request_objects;

import com.jakubwawak.klipAPI.KlipApiApplication;

import java.util.ArrayList;

/**
 * Object for storing request answer for /health endpoint
 */
public class R_Health extends Answer{

    /**
     * CREATE TABLE HEALTH
     * (
     *     health_database_version VARCHAR(10),
     *     health_database_status INT,
     *     health_database_enable INT
     * );
     */
    String health_database_version;
    int health_database_status;
    int health_database_enable;

    /**
     * Constructor
     */
    public R_Health(){
        // main answer data set
        super.answer_name = "/health";
        super.apptoken_flag = 0;
        super.sessiontoken_flag = 0;

        // main object logic
        ArrayList<String> data = KlipApiApplication.database.get_health_data();
        if ( data.size() == 3 ){
            health_database_version = data.get(0);
            health_database_status = Integer.parseInt(data.get(1));
            health_database_enable = Integer.parseInt(data.get(2));

            // setting no error flag on answer object
            super.answer_error = "no_error";
            super.answer_error_flag = 0;
        }
        else{
            KlipApiApplication.log.add("Failed to get /health data! Object empty!","HEALTH-ERROR");

            // setting error flag on answer object
            super.answer_error = "Failed to get /health data. ArrayList object empty from database";
            super.answer_error_flag = 1;
        }
    }
}
