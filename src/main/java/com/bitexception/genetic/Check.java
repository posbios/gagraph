package com.bitexception.genetic;

import com.bitexception.genetic.feed.CryptoconcurrencyFeeder;
import com.bitexception.nio.HttpConversation;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

/**
 *
 * @author andoni
 */
public class Check {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String test = "AED," +
"AFN," +
"ALL," +
"AMD," +
"ANG," +
"AOA," +
"ARS," +
"AUD," +
"AWG," +
"AZN," +
"BAM," +
"BBD," +
"BDT," +
"BGN," +
"BHD," +
"BIF," +
"BMD," +
"BND," +
"BOB," +
"BRL," +
"BSD," +
"BTC," +
"BTN," +
"BWP," +
"BYN," +
"BYR," +
"BZD," +
"CAD," +
"CDF," +
"CHF," +
"CLF," +
"CLP," +
"CNH," +
"CNY," +
"COP," +
"CRC," +
"CUC," +
"CVE," +
"CZK," +
"DJF," +
"DKK," +
"DOP";
        
        Arrays.asList(test.split(",")).forEach(v -> {
            System.out.println(MessageFormat.format("{0}(\"{0}\"),", v));
        });
        
//        double[][] enterprisesEvolutions = new CryptoconcurrencyFeeder(CryptoconcurrencyFeeder.CONCURRENCY.BTC).getEnterprisesEvolutions();
//        Jsonb jsonb = JsonbBuilder.create();
//
//        try {
//            HttpConversation httpConversation = new HttpConversation(new URI("https://api.coinbase.com/v2/prices/BTC-EUR/spot" + "?" + "date=2018-01-01")).setContentType(HttpConversation.ContentType.JSON).setRequestMethod(HttpConversation.RequestMethod.GET);
//            httpConversation.setRequestProperty(new HttpConversation.RequestProperty("CB-VERSION", "2015-04-08"));
//            String test = httpConversation.conversation();
//            Response r = jsonb.fromJson(test, Response.class);
//            System.out.println(r);
//        } catch (IOException ex) {
//            Logger.getLogger(Check.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(Check.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//
//        try {
//            HttpConversation httpConversation = new HttpConversation(new URI("https://min-api.cryptocompare.com/data/histominute?fsym=BTC&tsym=EUR&limit=60&aggregate=3&e=CCCAGG")).setContentType(HttpConversation.ContentType.JSON).setRequestMethod(HttpConversation.RequestMethod.GET);
//            String test = httpConversation.conversation();
//            Response r = jsonb.fromJson(test, Response.class);
//            System.out.println(r);
//        } catch (IOException ex) {
//            Logger.getLogger(Check.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(Check.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
    }
}
