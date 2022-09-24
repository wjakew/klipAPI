/*
Jakub Wawak
kubawawak@gmail.com
all rights reseved
 */
package com.jakubwawak.maintanance;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class for collecting log from program
 */
public class LoGrabber {

    // collection for storing log list data
    public ArrayList<String> log_list;
    Date start_time;

    String file_prefix;

    LocalDateTime raw_start_time;

    int debug = 1; // flag for printing log data

    /**
     * Constructor
     */
    public LoGrabber(String file_prefix){
        this.file_prefix = file_prefix;
        log_list = new ArrayList<>();
        start_time = new Date();
        add("LOG_START","LOG STARTED - "+start_time.toString());
        raw_start_time = LocalDateTime.now(ZoneId.of("Europe/Warsaw"));
    }

    /**
     * Function for adding
     * @param log_code
     * @param log_desc
     */
    public void add(String log_code,String log_desc){
        Date date = new Date();
        log_list.add(date.toString()+" - "+log_code+" - "+log_desc);

        if ( debug == 1 ){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT+date.toString()+" - "+ConsoleColors.PURPLE_BOLD_BRIGHT+log_code+": "+ ConsoleColors.BLUE_BOLD_BRIGHT+log_desc);
        }
    }

    /**
     * Function for dumping log data to file
     * @throws IOException
     */
    public void log_dump(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
            Date now = new Date();
            String strDate = sdf.format(now);
            String filename = file_prefix+strDate+".log";
            FileWriter fw = new FileWriter(filename);
            for(String line : log_list){
                fw.write(line+"\n");
            }
            LocalDateTime end_time = LocalDateTime.now(ZoneId.of("Europe/Warsaw"));
            long minutes = ChronoUnit.MINUTES.between(raw_start_time,end_time);
            fw.write("Job time: from - "+raw_start_time.toString()+" to: "+end_time.toString()+" apr: "+minutes+" minutes.");
            fw.write("File closed! by Jakub Wawak / kubawawak@gmail.com / j.wawak@usp.pl");
            fw.close();
        }catch(Exception e){
            System.err.println("Error saving log to file ("+e.toString()+")");
        }
    }
}
