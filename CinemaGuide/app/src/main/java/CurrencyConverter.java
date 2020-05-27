import java.util.ArrayList;

public class CurrencyConverter {

    private ArrayList<String> currencyId;
    private  ArrayList <Float> currency_today_course;

    public CurrencyConverter(){

        currency_today_course.add((float) 1.0);
        currency_today_course.add((float) 1.5);
        currencyId.add("USD");
        currencyId.add("RUB");

        float cost = 23;
        float result = 0;

        result = convertToFrom(currencyId.get(0), currencyId.get(1), cost);
    }

    private float convertToFrom(String currencyFrom, String currencyTo, float cost) {
        float result = 0;
        int pos_from = currencyId.indexOf(currencyFrom);
        int pos_to = currencyId.indexOf(currencyTo);
        result = cost/currency_today_course.get(pos_from)*currency_today_course.get(pos_to);
        return  result;
    }
}
