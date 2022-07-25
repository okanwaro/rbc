package com.rbc.stocks.entity;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@Data
public class StocksEntity {

    String quarter;
    String stock;
    String date;
    String open;
    String high;
    String low;
    String close;
    String volume;
    String percentChangePrice;
    String percentChangeVolumeOverLastWk;
    String previousWeeksVolume;
    String nextWeeksOpen;
    String nextWeeksClose;
    String percentChangeNextWeeksPrice;
    String daysToNextDividend;
    String percentReturnNextDividend;

    public static Float getAmount(String amount){
       return amount !=null ? Float.valueOf( amount.substring(1) ): null ;
    }
    public static Date convertDate(String date) throws Exception {
        SimpleDateFormat fromUser = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date dateConverted = null;
        try {
            Date dateFromUser = fromUser.parse(date); // Parse it to the exisitng date pattern and return Date type
            String dateMyFormat = myFormat.format(dateFromUser); // format it to the date pattern you prefer
            System.out.println(dateMyFormat); // outputs : 2009-05-19
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            dateConverted = formatter.parse(dateMyFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateConverted;
    }
}
