package org.newest.msorgapp;

import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

public class SongStudieSongs {
   MainActivity mainActivity = new MainActivity();


   public String[] DunSonDogSagNote = new String[]{
      /*1*/ "A4","E5","D5","C5","B4",/*2*/"C5","D5","C5","B4","G4",/*3*/"A4","E5","D5","C5","B4",/*4*/"C5","D5",
           /*5*/ "A4","E5","D5","C5","B4",/*6*/"C5","D5","C5","B4","G4",/*7*/"A4","A4","B4","C5","B4","A4",/*8*/"S",
            /*9*/ "A4","E5","D5","C5","B4", /*10*/"C5","D5","C5","B4","G4",/*11*/"A4","E5","D5","C5","B4",/*12*/"C5","D5",
           /*13*/ "A4","E5","D5","C5","B4",/*14*/"C5","D5","C5","B4","G4",/*15*/"A4","A4","B4","C5","B4","A4",/*16*/"G4","B4","A4",
           /*17*/"A5","A5","B5","C6","B5","A5",/*18*/"G5","B5","A5"
   };

   public double[] DunSonDogSagNoteDeg = new double[]{
      /*1*/1.0,1.0,0.5,1.0,0.5,/*2*/1.0,1.0,0.5,1.0,0.5,/*3*/1.0,1.0,0.5,1.0,0.5,/*4*/1.0,3.0,
           /*5*/1.0,1.0,0.5,1.0,0.5,/*6*/1.0,1.0,0.5,1.0,0.5,/*7*/1.0,0.5,0.5,0.5,0.5,1.0,/*8*/4.0,
           /*9*/1.0,1.0,0.5,1.0,0.5,/*10*/1.0,1.0,0.5,1.0,0.5,/*11*/1.0,1.0,0.5,1.0,0.5,/*12*/1.0,3.0,
           /*13*/1.0,1.0,0.5,1.0,0.5,/*14*/1.0,1.0,0.5,1.0,0.5,/*15*/1.0,0.5,0.5,0.5,0.5,1.0,/*16*/1.0,1.0,2.0,
           /*17*/1.0,0.5,0.5,0.5,0.5,1.0,/*18*/1.0,1.0,2.0
   };

   public int[] DunSonDogSagNoteRaw = new int[]{
      /*1*/R.raw.a4,R.raw.e5,R.raw.d5,R.raw.c5,R.raw.b4,/*2*/ R.raw.c5,R.raw.d5,R.raw.c5,R.raw.b4,R.raw.g4,
      /*3*/R.raw.a4,R.raw.e5,R.raw.d5,R.raw.c5,R.raw.b4,/*4*/ R.raw.c5,R.raw.d5,/*5*/R.raw.a4,R.raw.e5,R.raw.d5,R.raw.c5,R.raw.b4,
           /*6*/R.raw.c5,R.raw.d5,R.raw.c5,R.raw.b4,R.raw.g4,/*7*/R.raw.a4,R.raw.a4,R.raw.b4,R.raw.c5,R.raw.b4,R.raw.a4,/*8*/R.raw.a4,
           /*9*/R.raw.a4,R.raw.e5,R.raw.d5,R.raw.c5,R.raw.b4,/*10*/ R.raw.c5,R.raw.d5,R.raw.c5,R.raw.b4,R.raw.g4,
          /*11*/ R.raw.a4,R.raw.e5,R.raw.d5,R.raw.c5,R.raw.b4,/*12*/ R.raw.c5,R.raw.d5,/*13*/ R.raw.a4,R.raw.e5,R.raw.d5,R.raw.c5,R.raw.b4,
           /*14*/R.raw.c5,R.raw.d5,R.raw.c5,R.raw.b4,R.raw.g4,/*15*/R.raw.a4,R.raw.a4,R.raw.b4,R.raw.c5,R.raw.b4,R.raw.a4,
           /*16*/R.raw.g4,R.raw.b4,R.raw.a4,/*17*/R.raw.a5,R.raw.a5,R.raw.b5,R.raw.c6,R.raw.b5,R.raw.a5,
           /*18*/R.raw.g5,R.raw.b5,R.raw.a5
   };

}
