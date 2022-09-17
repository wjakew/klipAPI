/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.klipAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application object
 */
@SpringBootApplication
public class KlipApiApplication {

	public static String version =  "v1.0.0";
	public static String build = "KLIP170922REV1";

	/**
	 * Main application function
	 * @param args
	 */
	public static void main(String[] args) {
		show_header();
		SpringApplication.run(KlipApiApplication.class, args);

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
