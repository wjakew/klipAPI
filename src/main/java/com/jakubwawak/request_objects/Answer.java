/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.request_objects;

/**
 * Default object for storing request answer
 */
public class Answer {

    /**
     * apptoken_flag:
     *   1 - success to answer request
     *   0 - apptoken not initialized
     *  -1  - failed trying to answer request ( check answer_error_flag and answer_error )
     */
    public int apptoken_flag;

    /**
     * sessiontoken_flag
     * 1 - session correct
     * 0 - session not initialized
     * 2 - session has expired
     */
    public int sessiontoken_flag;

    /**
     * name of request endpoint
     */
    public String answer_name;

    /**
     * answer error
     */
    public String answer_error;

    /**
     * answer_error_flag:
     * error codes
     *  0 - success, no errors
     * -1 - failed to load request
     */
    public int answer_error_flag;

    /**
     * Variable to store response flags for different objects
     * default value: -99
     */
    public int object_flag;

    /**
     * Constructor
     */
    public Answer(){
        apptoken_flag = 0;
        sessiontoken_flag = 0;
        answer_name = "none";
        answer_error = "";
        answer_error_flag = 0;
        object_flag = -99;
    }
}
