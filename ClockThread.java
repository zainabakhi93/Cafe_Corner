package Backend;

import javax.swing.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class ClockThread extends Thread {


    JLabel clockLabel;


    public ClockThread(JLabel clockLabel){

        this.clockLabel = clockLabel;

    }



    public void run(){


        while(true){


            LocalTime time = LocalTime.now();

            DateTimeFormatter format =
                    DateTimeFormatter.ofPattern("HH:mm:ss");

            String timeText = "Time: " + time.format(format);

            SwingUtilities.invokeLater(() -> {
                clockLabel.setText(timeText);
            });



            try{

                Thread.sleep(1000);

            }

            catch(Exception e){

                System.out.println(e);

            }


        }


    }


}