/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */
package com.jakubwawak.maintanance;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Object for creating, storing and maintaning configuration files
 */
public class Configuration_Service {

    public String configuration_path;
    public ArrayList<String> raw_data;

    public String klip_raw_database; //field for storing database ip for the database
    public String klip_raw_database_user; //field for storing database user data
    public String klip_raw_database_password; // field for storing database password

    public String klip_raw_database_name;
    public ArrayList<Integer> blacklist;
    public String build;
    public boolean configuration_file_exists;
    LoGrabber nl;

    /**
     * raw file:
     * $configuration file for nami app / nami service
     * klip_raw_database%XXXXXXXXXXXXX
     * klip_raw_database_user%XXXXXXXXXXXXXXXXXX
     * klip_raw_database_password%XXXXXXXXXXXXXX
     * $by Jakub Wawak 2022 / j.wawak@usp.pl / kubawawak@gmail.com
     */

    /**
     * Constructor
     */
    public Configuration_Service(LoGrabber nl,String build){
        this.build = build;
        Path currentRelativePath = Paths.get("");
        this.nl = nl;
        configuration_path = "config_new.nami";
        String current_service_path = currentRelativePath.toAbsolutePath().toString();
        File dir = new File(current_service_path);
        File[] directoryListening = dir.listFiles();
        if ( directoryListening != null ){
            for(File child : directoryListening){
                if ( child.getAbsolutePath().contains(".nami") ){
                    configuration_path = child.getAbsolutePath();
                    nl.add("CONFIG-FILE-FOUND","Found configuration file: "+configuration_path);
                    configuration_file_exists = true;
                    break;
                }
            }
        }
        else{
            nl.add("CONFIG-FILE-NOTFOUND","Failed to found configuration file!");
            configuration_file_exists = false;
            configuration_path = "null";
        }
        this.klip_raw_database_user = "";
        this.klip_raw_database_password = "";
        blacklist = new ArrayList<>();
    }

    /**
     * Constructor with parameters for creating configuration files
     * @param klip_raw_database
     * @param klip_raw_database_user
     * @param klip_raw_database_password
     * @param nl
     */
    public Configuration_Service(String klip_raw_database,String klip_raw_database_user,String klip_raw_database_password,LoGrabber nl){
        this.klip_raw_database = klip_raw_database;
        this.klip_raw_database_user = klip_raw_database_user;
        this.klip_raw_database_password = klip_raw_database_password;
        this.nl = nl;
    }

    /**
     * Function for loading configuration file
     */
    public void load_config(){

        /**
         * raw file:
         * $configuration file for nami app / nami service
         * klip_raw_database%XXXXXXXXXXXXX
         * klip_raw_database_user%XXXXXXXXXXXXXXXXXX
         * klip_raw_database_password%XXXXXXXXXXXXXX
         * klip_raw_database_name%XXXXXXXXXXXXXXXXX
         * $by Jakub Wawak 2022 / j.wawak@usp.pl / kubawawak@gmail.com
         */
        try{
            FileReader fr = new FileReader(configuration_path );
            BufferedReader bfr = new BufferedReader(fr);
            String line;
            while((line = bfr.readLine()) != null){
                if ( line.contains("klip_raw_database%")){
                    klip_raw_database = line.split("%")[1];
                }
                else if (line.contains("klip_raw_database_user%")){
                    klip_raw_database_user = line.split("%")[1];
                }
                else if (line.contains("klip_raw_database_password")){
                    klip_raw_database_password = line.split("%")[1];
                }
                else if (line.contains("klip_raw_database_name")){
                    klip_raw_database_name = line.split("%")[1];
                }
            }
            nl.add("CONFIG-LOADED","Configuration file loaded");
        }catch(Exception e){
            System.out.println("Error! Failed to load lines ("+e.toString()+")");
            nl.add("CONFIG-FAILED","Error! Failed to load lines ("+e.toString()+")");
        }
    }

    /**
     * Function for scanning system
     */
    public void user_load_config() throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.print("klip_database_ip?");
        String user_raw_database = sc.nextLine();
        System.out.print("klip_database_user?");
        String user_raw_login = sc.nextLine();
        System.out.print("klip_database_name?");
        String user_raw_database_name = sc.nextLine();
        char[] ch;
        try{
            Console cnsl = System.console();
            ch = cnsl.readPassword("klip_database_password?");
        }catch(Exception e){
            System.out.print("klip_database_password?");
            String user_raw_password = sc.nextLine();
            ch = user_raw_password.toCharArray();
        }
        if ( !user_raw_database.equals("") && !user_raw_login.equals("") && !String.copyValueOf(ch).equals("") && !user_raw_database_name.equals("") ){
            klip_raw_database = user_raw_database;
            klip_raw_database_user = user_raw_login;
            klip_raw_database_password = String.copyValueOf(ch);
            klip_raw_database_name = user_raw_database_name;
            create_config(configuration_path);
        }
    }

    /**
     * Function for creating config file
     */
    public void create_config(String config_name) throws IOException {
        if ( config_name.equals(configuration_path)){
            config_name = configuration_path;
        }
        else {
            config_name = config_name.replaceAll(" ", "") + ".nami";
        }
        FileWriter fw = new FileWriter(config_name);
        fw.write("$configuration file for nami app / nami service - "+build+"\n");
        fw.write("klip_raw_database%"+klip_raw_database+"\n");
        fw.write("klip_raw_database_user%"+klip_raw_database_user+"\n");
        fw.write("klip_raw_database_password%"+klip_raw_database_password+"\n");
        fw.write("klip_raw_database_name%"+klip_raw_database_name+"\n");
        fw.write("\n$by Jakub Wawak 2022 / j.wawak@usp.pl / kubawawak@gmail.com\n");
        fw.close();
        nl.add("CONFIG-SUCCESS","Configuration file created and saved");
    }

    /**
     * Function for showing configuration file
     */
    public void show_config(){
        System.out.println("Current configuration:");
        System.out.println("klip_raw_database: "+klip_raw_database);
        System.out.println("klip_raw_database_name: "+klip_raw_database_name);
        System.out.println("klip_raw_database_user: "+klip_raw_database_user);
        System.out.println("klip_raw_database_password: "+klip_raw_database_password);
    }


    /**
     * Function for checking if object is empty
     * @return boolean
     */
    public boolean empty(){
        return klip_raw_database.equals("") && klip_raw_database_user.equals("") && klip_raw_database_password.equals("") && klip_raw_database_name.equals("");
    }
}
