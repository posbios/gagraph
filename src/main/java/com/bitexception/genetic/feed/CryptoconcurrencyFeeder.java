package com.bitexception.genetic.feed;

import com.bitexception.genetic.Response;
import com.bitexception.nio.HttpConversation;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

/**
 *
 * @author andon
 */
public class CryptoconcurrencyFeeder {

    public static List<CONCURRENCY> coins;
    private final Instant initialDate;
    private final long numberOfDays;

    public CryptoconcurrencyFeeder(Instant initialDate, long numberOfDays, CONCURRENCY... values) {
        CryptoconcurrencyFeeder.coins = Arrays.asList(values);
        this.initialDate = initialDate;
        this.numberOfDays = numberOfDays;
    }

    public enum CONCURRENCY {
        BTC("BTC"),
        ETH("ETH"),
        LTC("LTC"),
        BCH("BCH");

        private final static String pattern = "https://api.coinbase.com/v2/prices/{0}-EUR/spot?date={1}";
        private final String code;

        CONCURRENCY(String code) {
            this.code = code;
        }

        public String getUrl(Date date) {
            return MessageFormat.format(pattern, code, new SimpleDateFormat("yyyy-MM-dd").format(date));
        }
    }

    public double[][] getEnterprisesEvolutions() {
        double[][] result;
        result = new double[coins.size()][];

        IntStream.range(0, coins.size()).parallel().forEach((i) -> {
            List<Response> responses = new ArrayList<>();
            Jsonb jsonb = JsonbBuilder.create();
            try {
                for (int k = 0; k < this.numberOfDays; k++) {
                    Date date = Date.from(this.initialDate.plus(k, ChronoUnit.DAYS));
                    if (Date.from(this.initialDate.plus(k + 1, ChronoUnit.DAYS)).after(new Date())) {
                        break;
                    }
                    HttpConversation httpConversation = new HttpConversation(
                            new URI(coins.get(i).getUrl(date)))
                            .setContentType(HttpConversation.ContentType.JSON)
                            .setRequestMethod(HttpConversation.RequestMethod.GET);
                    httpConversation.setRequestProperty(new HttpConversation.RequestProperty("CB-VERSION", "2015-04-08"));
                    responses.add(jsonb.fromJson(httpConversation.conversation(), Response.class));
                }
                double[] test = new double[responses.size() - 1];
                for (int j = 0; j < responses.size() - 1; j++) {
                    float name = (responses.get(j + 1).data.amount - responses.get(j).data.amount) / responses.get(j).data.amount;
                    test[j] = name;
                }
                result[i] = test;
            } catch (IOException ex) {
                Logger.getLogger(CryptoconcurrencyFeeder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(CryptoconcurrencyFeeder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(CryptoconcurrencyFeeder.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        return result;
    }
}
