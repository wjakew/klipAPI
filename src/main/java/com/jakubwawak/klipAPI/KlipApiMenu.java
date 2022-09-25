/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.klipAPI;

import org.springframework.boot.SpringApplication;

import java.util.Scanner;

/**
 * KlipApiMenu object for creating menu in terminal
 */
public class KlipApiMenu {

    String header = " _ __ ___   ___ _ __  _   _ \n" +
            "| '_ ` _ \\ / _ \\ '_ \\| | | |\n" +
            "| | | | | |  __/ | | | |_| |\n" +
            "|_| |_| |_|\\___|_| |_|\\__,_|";

    boolean exit_flag;

    /**
     * Constructor
     */
    public void KlipApiMenu(){
        System.out.println("Database status: "+KlipApiApplication.database.connected+", API status: "+KlipApiApplication.database.check_api_enabled());
        System.out.println(header);
        exit_flag = true;
    }

    /**
     * Function for running menu functionality
     */
    public void run(String[] args){
        while(exit_flag){
            System.out.print("klipAPI>");
            Scanner sc = new Scanner(System.in);
            String user_input = sc.nextLine();
            mind(user_input,args);
        }
    }

    /**
     * Function for running menu items using user_input
     * @param user_input
     */
    public void mind(String user_input,String [] args){
        for(String word : user_input.split(" ")){
            switch(word){
                case "exit":
                {
                    System.out.println("API Exit...");
                    System.out.println("Bye");
                    KlipApiApplication.log.log_dump();
                    System.exit(0);
                }
                case "start":
                {
                    if ( KlipApiApplication.database.connected)
                        SpringApplication.run(KlipApiApplication.class, args);
                    else
                        System.out.println("Cannot run without database connection!");
                    break;
                }
                case "info":
                {
                    System.out.println("klipAPI "+KlipApiApplication.version);
                    System.out.println("Build: "+KlipApiApplication.build);
                    System.out.println("by Jakub Wawak / kubawawak@gmail.com");
                    break;
                }
            }
        }
    }
}
