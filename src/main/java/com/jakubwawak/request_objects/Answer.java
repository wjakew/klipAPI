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
     * Data about loading answer data
     */
    public int apptoken_flag;
    public int sessiontoken_flag;

    public String answer_name;

    public String answer_error;
    public int answer_error_flag;
}
