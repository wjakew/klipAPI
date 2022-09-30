/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.klipAPI;

import com.jakubwawak.database.Database_Connector;
import com.jakubwawak.maintanance.Configuration_Service;
import com.jakubwawak.maintanance.LoGrabber;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.module.Configuration;

/**
 * Main application object
 */
@SpringBootApplication
public class KlipApiApplication {

	public static String version =  "v1.0.0";
	public static String build = "KLIP300922REV1";

	public static Database_Connector database;
	public static LoGrabber log;

	/**
	 * Main application function
	 * @param args
	 */
	public static void main(String[] args) {
		show_header();
		log = new LoGrabber("klipapi_");
		Configuration_Service cs = new Configuration_Service(log,build);
		if ( cs.configuration_file_exists ){
			cs.load_config();
			//configuration exists - loaded data
			//api can start
			run(args,log,cs);
		}
		else{
			// need to load data from user - configuration file not exists
			log.add("Loading configuration data from user...","CONFIG-FILE-NOTFOUND");
			try{
				cs.user_load_config();
				cs.create_config("config_new.nami");
				cs.load_config();
				//configutarion ready - loaded from user
				//api can start
				run(args,log,cs);
			}catch(Exception e){
				log.add("Major error ("+e.toString()+")","MAJOR-API-ERROR");
			}
		}
	}

	/**
	 * Function for running main program
	 * @param args
	 */
	public static void run(String[] args,LoGrabber log,Configuration_Service cs){
		database = new Database_Connector(cs,log);
		database.connect();
		if (database.connected){
			KlipApiMenu menu = new KlipApiMenu();
			menu.run(args);
		}
		else{
			log.add("Failed to connect to database!","DATABASE-CONNECTION-FAILED");
		}
	}
	/**
	 * Function for showing application header
	 */
	public static void show_header(){
		String header = " _    _ _         _    ____ ___\n" +
				"| | _| (_)_ __   / \\  |  _ \\_ _|\n" +
				"| |/ / | | '_ \\ / _ \\ | |_) | |\n" +
				"|   <| | | |_) / ___ \\|  __/| |\n" +
				"|_|\\_\\_|_| .__/_/   \\_\\_|  |___|\n" +
				"         |_| "+version+"/"+build;

		System.out.println(header);
	}
}
