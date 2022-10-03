/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.handlers;

import com.jakubwawak.connection_engine.Apptoken;
import com.jakubwawak.database.Database_Bucket;
import com.jakubwawak.klipAPI.KlipApiApplication;
import com.jakubwawak.request_objects.R_Bucket;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Object for handling bucket requests
 */
public class HANDLER_Bucket {

    /**
     * Endpoint for creating bucket
     * @param bucket_note
     * @param bucket_password
     * @return R_Bucket
     */
    @GetMapping("/bucket-create/{macaddress}/{apptoken}/{bucket_note}/{bucket_password}")
    public R_Bucket create_bucket(@PathVariable String macaddress,@PathVariable String apptoken,@PathVariable String bucket_note, @PathVariable String bucket_password){

        // setting main response object
        R_Bucket r_bucket = new R_Bucket();
        r_bucket.answer_name = "/bucket-create";

        //checking apptoken data
        Apptoken apptoken_obj = new Apptoken(apptoken,macaddress);

        if ( apptoken_obj.check_apptoken() == 1 ){
            // apptoken correct
            r_bucket.apptoken_flag = 1;
            // endpoint resolve

            Database_Bucket db = new Database_Bucket(KlipApiApplication.database);
            r_bucket.object_flag = db.create_bucket(bucket_note,bucket_password);

        }
        else{
            // wrong apptoken data or database error
            r_bucket.apptoken_flag = apptoken_obj.check_apptoken();
            if ( r_bucket.apptoken_flag == -2){
                r_bucket.answer_error = "database_error";
                r_bucket.answer_error_flag = -2;
            }
        }
        return r_bucket;
    }

    /**
     * Endpoint for archving bucket data
     * @param macaddress
     * @param apptoken
     * @param bucket_id
     * @param bucket_password
     * @return R_Bucket
     */
    @GetMapping("/bucket-archive/{macaddress}/{apptoken}/{bucket_id}/{bucket_passoword}")
    public R_Bucket archive_bucket(@PathVariable String macaddress, @PathVariable String apptoken,@PathVariable int bucket_id,@PathVariable String bucket_password){
        // setting main response object
        R_Bucket r_bucket = new R_Bucket();
        r_bucket.answer_name = "/bucket-create";

        //checking apptoken data
        Apptoken apptoken_obj = new Apptoken(apptoken,macaddress);

        if ( apptoken_obj.check_apptoken() == 1 ){
            // apptoken correct
            r_bucket.apptoken_flag = 1;
            // endpoint resolve

            Database_Bucket db = new Database_Bucket(KlipApiApplication.database);
            r_bucket.object_flag = db.archive_bucket(bucket_id,bucket_password);

        }
        else{
            // wrong apptoken data or database error
            r_bucket.apptoken_flag = apptoken_obj.check_apptoken();
            if ( r_bucket.apptoken_flag == -2){
                r_bucket.answer_error = "database_error";
                r_bucket.answer_error_flag = -2;
            }
        }
        return r_bucket;
    }


}
