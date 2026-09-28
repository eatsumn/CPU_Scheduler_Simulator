package org.lorelei.cpu_scheduler;

import java.util.ArrayList;
import java.util.Arrays;

public class Settings {
    public static ArrayList<String> algorithmChoices = new ArrayList<String>(Arrays.asList("First Come First Serve", "Short Job Next", "Round Robin"));
    static ArrayList<String> secretAlgos = new ArrayList<String>(Arrays.asList("Random Next", "Arrival-Burst Product"));
    public static boolean enableSecretAlgos = false;
     public static boolean randomFloat = false;
     public static int randomDecimalPlace = 10;

     public static int FPS = 60;

     public static void updateAlgos(boolean secretAlgo){
         if(secretAlgo){
             algorithmChoices.addAll(secretAlgos);
         }else {
             algorithmChoices.removeAll(secretAlgos);
         }
         enableSecretAlgos = secretAlgo;
     }


}
