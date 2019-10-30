package com.prodevsblog.phplogin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> jenis = new ArrayList<String>();
        jenis.add("Sepeda Motor");
        jenis.add("Sedan");
        jenis.add("Minibus");
        jenis.add("Bus");
        jenis.add("Truk");


        List<String> bahan_bakar = new ArrayList<String>();
        bahan_bakar.add("RON 88");
        bahan_bakar.add("RON 92");
        bahan_bakar.add("HSD");


        List<String> kondisi = new ArrayList<String>();
        kondisi.add("B");
        kondisi.add("RR");
        kondisi.add("RB");


        expandableListDetail.put("Jenis Kendaraan", jenis);
        expandableListDetail.put("Bahan Bakar", bahan_bakar);
        expandableListDetail.put("Kondisi", kondisi);
        return expandableListDetail;
    }
}
