package org.newest.msorgapp;

public class SongStudieHelper {


   public int[] ustlay = new int[]{2,1,1,2,2,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,2,2,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,2,2,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,2,2,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,2
            ,2,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,2,2,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,2,2,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,2,3};

    public int[]colorsdet = new int[]{1,0,0,1,1,0,0,1,0,0,1,1,0,0,1,0,0,1,0,0,1,1,0,0,1,0,0,1,1,0,0,1,0,0,1,0,0,1
            ,1,0,0,1,0,0,1,1,0,0,1,0,0,1,0,0,1,1,0,0,1,0,0,1,1,0,0,1,0,0,1,0,0,1,1,0,0,1,0,0,1,1,0,0,1,0,0,1,0,0,1,1,0,0,1,0,0,1,1,0,0,1,0,0,1,0,0,1,1,0,0,1,0,0,1,1,0,0,1,0,0,1,0,0,1,1};
    public int[] ustlaypadding = new int[]{0,1,2,3,4,1,2,4,1,2,3,4,1,2,4,1,2,4,1,2,3,4,1,2,4,1,2,3,4,1,2,4,1,2,4,1,2,3,4,1,2,4,1,2,3,4,1,2,4,1,2,4,1,2,3,4,1,2,4,1,2,3,4,1,2,4,1,2,4,1,2,3
            ,4,1,2,4,1,2,3,4,1,2,4,1,2,4,1,2,3,4,1,2,4,1,2,3,4,1,2,4,1,2,4,1,2,3,4,1,2,4,1,2,3,4,1,2,4,1,2,4,1,2,3,4};

    public String[] notagsustlay = new String[]{"A0","A#0","A#0","B0","C1","C#1","C#1","D1","D#1","D#1","E1","F1","F#1","F#1","G1","G#1","G#1","A1","A#1","A#1","B1",
            "C2","C#2","C#2","D2","D#2","D#2","E2","F2","F#2","F#2","G2","G#2","G#2","A2","A#2","A#2","B2",
            "C3","C#3","C#3","D3","D#3","D#3","E3","F3","F#3","F#3","G3","G#3","G#3","A3","A#3","A#3","B3",
            "C4","C#4","C#4","D4","D#4","D#4","E4","F4","F#4","F#4","G4","G#4","G#4","A4","A#4","A#4","B4",
            "C5","C#5","C#5","D5","D#5","D#5","E5","F5","F#5","F#5","G5","G#5","G#5","A5","A#5","A#5","B5",
            "C6","C#6","C#6","D6","D#6","D#6","E6","F6","F#6","F#6","G6","G#6","G#6","A6","A#6","A#6","B6",
            "C7","C#7","C#7","D7","D#7","D#7","E7","F7","F#7","F#7","G7","G#7","G#7","A7","A#7","A#7","B7","C8"
    };

    public String[] notagsaltlay = new String[]{"A0","A0","B0","B0","C1","C1","D1","D1","D1","E1","E1","F1","F1","G1","G1","G1","A1","A1","A1","B1","B1",
            "C2","C2","D2","D2","D2","E2","E2","F2","F2","G2","G2","G2","A2","A2","A2","B2","B2",
            "C3","C3","D3","D3","D3","E3","E3","F3","F3","G3","G3","G3","A3","A3","A3","B3","B3",
            "C4","C4","D4","D4","D4","E4","E4","F4","F4","G4","G4","G4","A4","A4","A4","B4","B4",
            "C5","C5","D5","D5","D5","E5","E5","F5","F5","G5","G5","G5","A5","A5","A5","B5","B5",
            "C6","C6","D6","D6","D6","E6","E6","F6","F6","G6","G6","G6","A6","A6","A6","B6","B6",
            "C7","C7","D7","D7","D7","E7","E7","F7","F7","G7","G7","G7","A7","A7","A7","B7","B7","C8"

    };

     public int[] allnotesust = new int[]{R.raw.a0,R.raw.bb0,R.raw.bb0,R.raw.b0,
             R.raw.c1,R.raw.db1,R.raw.db1,R.raw.d1,R.raw.eb1,R.raw.eb1,R.raw.e1,R.raw.f1,R.raw.gb1,R.raw.gb1,R.raw.g1,R.raw.ab1,R.raw.ab1,R.raw.a1,R.raw.bb1,R.raw.bb1,R.raw.b1,
             R.raw.c2,R.raw.db2,R.raw.db2,R.raw.d2,R.raw.eb2,R.raw.eb2,R.raw.e2,R.raw.f2,R.raw.gb2,R.raw.gb2,R.raw.g2,R.raw.ab2,R.raw.ab2,R.raw.a2,R.raw.bb2,R.raw.bb2,R.raw.b2,
             R.raw.c3,R.raw.db3,R.raw.db3,R.raw.d3,R.raw.eb3,R.raw.eb3,R.raw.e3,R.raw.f3,R.raw.gb3,R.raw.gb3,R.raw.g3,R.raw.ab3,R.raw.ab3,R.raw.a3,R.raw.bb3,R.raw.bb3,R.raw.b3,
             R.raw.c4,R.raw.db4,R.raw.db4,R.raw.d4,R.raw.eb4,R.raw.eb4,R.raw.e4,R.raw.f4,R.raw.gb4,R.raw.gb4,R.raw.g4,R.raw.ab4,R.raw.ab4,R.raw.a4,R.raw.bb4,R.raw.bb4,R.raw.b4,
             R.raw.c5,R.raw.db5,R.raw.db5,R.raw.d5,R.raw.eb5,R.raw.eb5,R.raw.e5,R.raw.f5,R.raw.gb5,R.raw.gb5,R.raw.g5,R.raw.ab5,R.raw.ab5,R.raw.a5,R.raw.bb5,R.raw.bb5,R.raw.b5,
             R.raw.c6,R.raw.db6,R.raw.db6,R.raw.d6,R.raw.eb6,R.raw.eb6,R.raw.e6,R.raw.f6,R.raw.gb6,R.raw.gb6,R.raw.g6,R.raw.ab6,R.raw.ab6,R.raw.a6,R.raw.bb6,R.raw.bb6,R.raw.b6,
             R.raw.c7,R.raw.db7,R.raw.db7,R.raw.d7,R.raw.eb7,R.raw.eb7,R.raw.e7,R.raw.f7,R.raw.gb7,R.raw.gb7,R.raw.g7,R.raw.ab7,R.raw.ab7,R.raw.a7,R.raw.bb7,R.raw.bb7,R.raw.b7,
             R.raw.c8

    };

     public int[] allnotesalt = new int[]{R.raw.a0,R.raw.a0,R.raw.b0,R.raw.b0,
             R.raw.c1,R.raw.c1,R.raw.d1,R.raw.d1,R.raw.d1,R.raw.e1,R.raw.e1,R.raw.f1,R.raw.f1,R.raw.g1,R.raw.g1,R.raw.g1,R.raw.a1,R.raw.a1,R.raw.a1,R.raw.b1,R.raw.b1,
             R.raw.c2,R.raw.c2,R.raw.d2,R.raw.d2,R.raw.d2,R.raw.e2,R.raw.e2,R.raw.f2,R.raw.f2,R.raw.g2,R.raw.g2,R.raw.g2,R.raw.a2,R.raw.a2,R.raw.a2,R.raw.b2,R.raw.b2,
             R.raw.c3,R.raw.c3,R.raw.d3,R.raw.d3,R.raw.d3,R.raw.e3,R.raw.e3,R.raw.f3,R.raw.f3,R.raw.g3,R.raw.g3,R.raw.g3,R.raw.a3,R.raw.a3,R.raw.a3,R.raw.b3,R.raw.b3,
             R.raw.c4,R.raw.c4,R.raw.d4,R.raw.d4,R.raw.d4,R.raw.e4,R.raw.e4,R.raw.f4,R.raw.f4,R.raw.g4,R.raw.g4,R.raw.g4,R.raw.a4,R.raw.a4,R.raw.a4,R.raw.b4,R.raw.b4,
             R.raw.c5,R.raw.c5,R.raw.d5,R.raw.d5,R.raw.d5,R.raw.e5,R.raw.e5,R.raw.f5,R.raw.f5,R.raw.g5,R.raw.g5,R.raw.g5,R.raw.a5,R.raw.a5,R.raw.a5,R.raw.b5,R.raw.b5,
             R.raw.c6,R.raw.c6,R.raw.d6,R.raw.d6,R.raw.d6,R.raw.e6,R.raw.e6,R.raw.f6,R.raw.f6,R.raw.g6,R.raw.g6,R.raw.g6,R.raw.a6,R.raw.a6,R.raw.a6,R.raw.b6,R.raw.b6,
             R.raw.c7,R.raw.c7,R.raw.d7,R.raw.d7,R.raw.d7,R.raw.e7,R.raw.e7,R.raw.f7,R.raw.f7,R.raw.g7,R.raw.g7,R.raw.g7,R.raw.a7,R.raw.a7,R.raw.a7,R.raw.b7,R.raw.b7,
             R.raw.c8



     };

 }
