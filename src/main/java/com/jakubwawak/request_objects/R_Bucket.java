/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.request_objects;

/**
 * Object for creating response data to /bucket() endpoint
 */
public class R_Bucket extends Answer{

    /**
     * Constructor
     */
    public R_Bucket(){
        super.answer_name = "";
        super.apptoken_flag = 0;
        super.sessiontoken_flag = 0;
        super.answer_error = "";
        super.answer_error_flag = 0;
    }

}
