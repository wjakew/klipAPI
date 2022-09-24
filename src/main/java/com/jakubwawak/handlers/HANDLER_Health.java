/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.handlers;

import com.jakubwawak.request_objects.R_Health;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Object for handling health endpoint
 */
@RestController
public class HANDLER_Health {

    @GetMapping("/health")
    public R_Health get_health_response(){

        // setting main response object
        R_Health health_response = new R_Health();

        // checking apptoken data
        // not applicable - endpoint is not using apptoken checking

        // checking session token data with apptoken information and duration time
        // not applicable - endpoint is not using session token checking

        // returning object data
        return health_response;
    }
}
