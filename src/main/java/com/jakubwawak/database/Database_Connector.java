/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.database;

import com.jakubwawak.maintanance.Configuration_Service;

/**
 * Object for connecting to database
 */

public class Database_Connector {
    Configuration_Service cs;

    /**
     * Constructor
     * @param cs
     */
    public Database_Connector(Configuration_Service cs){
        this.cs = cs;
    }
}
