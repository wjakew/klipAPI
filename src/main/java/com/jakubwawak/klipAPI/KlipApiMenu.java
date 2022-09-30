/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.klipAPI;

import com.jakubwawak.maintanance.ConsoleColors;
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
    public KlipApiMenu(){
        System.out.println("Database status: "+KlipApiApplication.database.connected+", API status: "+KlipApiApplication.database.check_api_enabled());
        System.out.println(header);
        exit_flag = true;
    }

    /**
     * Function for running menu functionality
     */
    public void run(String[] args){
        while(exit_flag){
            System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT+"klipAPI>"+ConsoleColors.RESET);
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
                    KlipApiApplication.database.nl.log_dump();
                    System.exit(0);
                }
                case "start":
                {
                    if ( KlipApiApplication.database.connected)
                    {
                        if ( KlipApiApplication.database.check_api_enabled() == 1){
                            SpringApplication.run(KlipApiApplication.class, args);
                        }
                        else{
                            System.out.println("API not enabled");
                        }
                    }
                    else
                        System.out.println("Cannot run without database connection!");
                    break;
                }
                case "enable":
                {
                    KlipApiApplication.database.set_api_enabled();
                    System.out.println("API status changed to: "+KlipApiApplication.database.check_api_enabled());
                    break;
                }
                case "disable":
                {
                    KlipApiApplication.database.set_api_disabled();
                    System.out.println("API status changed to: "+KlipApiApplication.database.check_api_enabled());
                    break;
                }
                case "info":
                {
                    System.out.println("klipAPI "+KlipApiApplication.version);
                    System.out.println("Build: "+KlipApiApplication.build);
                    System.out.println("API Status: "+KlipApiApplication.database.check_api_enabled());
                    System.out.println("by Jakub Wawak / kubawawak@gmail.com");
                    break;
                }
            }
        }
    }
}
