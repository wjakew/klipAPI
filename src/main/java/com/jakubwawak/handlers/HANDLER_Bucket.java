/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.handlers;

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
    @GetMapping("/bucket-create/{macaddress}/{apptoken}/{bucket_note}/{bucket_password")
    public R_Bucket create_bucket(@PathVariable String bucket_note, @PathVariable String bucket_password){

        // setting main response object
        R_Bucket r_bucket = new R_Bucket();

        //checking apptoken data


        return r_bucket;
    }
}
