package com.byul.web.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SyrupType {

    private String name;

    private int count;

    private int additionalCharge = 600;

    public SyrupType(String name, String count) {
        this.name = name;
        this.count = Integer.parseInt(count);
    }

    public static String dataToDB(List<SyrupType> syrup) {
        if(syrup == null) {
            return "";
        }
        return String.join("/", syrup.stream()
                .map(each -> each.name + " " + each.count)
                .collect(Collectors.toList()));
    }

    public static List<SyrupType> dataToWeb(String syrup) {
        if(syrup == null || syrup == "") {
            return new ArrayList<>();
        }
        return Arrays.stream(syrup.split("/"))
                .map(each -> {
                    String[] types = each.split(" ");
                    return new SyrupType(types[0], types[1]);
                }).toList();
    }

    public static int sumAdditionalCharge(List<SyrupType> syrups) {
        return syrups.stream().mapToInt(each -> each.getCount() * each.getAdditionalCharge()).sum();
    }

}
