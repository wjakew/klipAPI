/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.klipAPI;

import com.jakubwawak.connection_engine.Apptoken;
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

                case "apptoken":
                {
                    menu_apptoken(user_input);
                    break;
                }
                case "info":
                {
                    System.out.println("com/jakubwawak/klipAPI " +KlipApiApplication.version);
                    System.out.println("Build: "+KlipApiApplication.build);
                    System.out.println("API Status: "+KlipApiApplication.database.check_api_enabled());
                    System.out.println("by Jakub Wawak / kubawawak@gmail.com");
                    break;
                }
            }
        }
    }

    /**
     * Function for maintaining apptoken data
     * @param user_input
     */
    void menu_apptoken(String user_input){
        for(String word : user_input.split(" ")){
            switch(word){
                case "create":
                {
                    // apptoken create mac_address
                    if ( user_input.split(" ").length == 3){
                        Apptoken apptoken = new Apptoken(user_input.split(" ")[2]);
                    }
                    else{
                        System.out.println("Wrong apptoken command usage.");
                    }
                    break;
                }
                case "delete":
                {
                    // apptoken delete apptoken_id
                    if ( user_input.split(" ").length == 3){
                        try{
                            int apptoken_id = Integer.parseInt(user_input.split(" ")[3]);
                            Apptoken apptoken = new Apptoken(apptoken_id);
                            if ( apptoken.remove_apptoken() == 1){
                                System.out.println("Apptoken removed!");
                            }
                            else{
                                System.out.println("Error removing apptoken");
                            }
                        }catch(Exception e){
                            System.out.println("Failed, wrong command usage.");
                        }
                    }
                    break;
                }
                case "list":
                {
                    // apptoken list
                    Apptoken apptoken = new Apptoken();
                    for(String data: apptoken.show_apptoken_list()){
                        System.out.println(data);
                    }
                    break;
                }
            }
        }
    }
}
