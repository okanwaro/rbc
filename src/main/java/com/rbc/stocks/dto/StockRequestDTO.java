package com.rbc.stocks.dto;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;


@Data
@Entity
@Table(name = "STOCKS")
public class StockRequestDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @NotNull(message = "Please Enter quarter value")
    Integer quarter;
    @NotNull(message = "Please Enter Stock name")
    String stock;
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Please Enter date name")
//    @JsonFormat(pattern = "MM/dd/yyyy",shape = JsonFormat.Shape.STRING)
    Date date;
    @NotNull(message = "Please Enter open stock price")
    Float open;
    @NotNull(message = "Please Enter high price for stock")
    Float high;
    @NotNull(message = "Please Enter low price for stock")
    Float low;
    @NotNull(message = "Please Enter close price for stock")
    Float close;
    @NotNull(message = "Please Enter volume of stock")
    Long volume;
    @NotNull(message = "Please Enter percentChangePrice")
    Double percentChangePrice;
    @NotNull(message = "Please Enter percentChangeVolumeOverLastWk")
    Float percentChangeVolumeOverLastWk;
    @NotNull(message = "Please Enter previousWeeksVolume")
    Long previousWeeksVolume;
    @NotNull(message = "Please Enter nextWeeksOpen")
    Float nextWeeksOpen;
    @NotNull(message = "Please Enter nextWeeksClose")
    Float nextWeeksClose;
    @NotNull(message = "Please Enter percentChangeNextWeeksPrice")
    Float percentChangeNextWeeksPrice;
    @NotNull(message = "Please Enter daysToNextDividend")
    Integer daysToNextDividend;
    @NotNull(message = "Please Enter percentReturnNextDividend")
    Double percentReturnNextDividend;

    public static Float getAmount(String amount) {
        if(amount.length()==0){
            return 0.0f;
        }
        else if (amount.startsWith("$")){
//        return (amount != null || !amount.equals("")  || amount.length()==0) ? Float.valueOf(amount.substring(1)): 0.0f;
        return Float.valueOf(amount.substring(1));
        }
        return Float.valueOf(amount);
    }

    public static LocalDateTime covertDate(String date) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm/dd/yyyy", Locale.ENGLISH);
        LocalDateTime dateConverted = LocalDateTime.parse(date, formatter);
        return dateConverted;
    }

    public static Float checkFloatEmpty(String string){
        if(string.length()==0){
            return 0.0f;
        }
        return Float.valueOf(string);
    }
    public static Long checkLongEmpty(String string){
        if(string.length()==0){
            return 0L;
        }
        return Long.valueOf(string);
    }

    public static Integer checkIntegerEmpty(String string){
        if(string.length()==0){
            return 0;
        }
        return Integer.valueOf(string);
    }
    public static Double checkDoubleEmpty(String string){
        if(string.length()==0){
            return 0.0;
        }
        return Double.valueOf(string);
    }

    public static Date covertDates(String date) throws Exception {
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
