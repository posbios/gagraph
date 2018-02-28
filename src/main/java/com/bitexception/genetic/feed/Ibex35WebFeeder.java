package com.bitexception.genetic.feed;

import com.bitexception.nio.HttpConversation;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.*;
import java.text.MessageFormat;
import static java.net.URLDecoder.decode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 *
 * @author andon
 */
public class Ibex35WebFeeder {

    public static List<ENTERPRISE> banks;

    public Ibex35WebFeeder(ENTERPRISE... values) {
        this.banks = Arrays.asList(values);
    }

    public enum ENTERPRISE {
//        IBEX35("?async=rmids:%2Fm%2F04ww1p,lang:en,country:us,cd:true,{0},ext:false,ei:YFHaWPLwNcOAUc-dr9AF,_fmt:json&ei=YFHaWPLwNcOAUc-dr9AF&yv=2"),
//        ABENGOA("?async=rmids:%2Fg%2F1hbvhywxj,lang:en,country:us,cd:true,{0},ext:false,ei:pv3YWJqgE4uAUdOascgF,_fmt:json&ei=pv3YWJqgE4uAUdOascgF&yv=2"),
        ABERTIS("?async=rmids:%2Fg%2F1hbvhj0mx,lang:en,country:us,cd:true,{0},ext:false,ei:vIzWWMH-JsusUYK0veAG,_fmt:json&ei=vIzWWMH-JsusUYK0veAG&yv=2"),
        ACCIONA("?async=rmids:%2Fg%2F1hbvlvxf0,lang:en,country:us,cd:true,{0},ext:false,ei:s4zWWKvHL4HuUIrliLAG,_fmt:json&ei=s4zWWKvHL4HuUIrliLAG&yv=2"),
        ACERINOX("?async=rmids:%2Fg%2F1hbvqjw64,lang:en,country:us,cd:true,{0},ext:false,ei:74zWWKDFMYvSU4DvnoAD,_fmt:json&ei=74zWWKDFMYvSU4DvnoAD&yv=2"),
        ACS("?async=rmids:%2Fg%2F1hbvpq4xr,lang:en,country:us,cd:true,{0},ext:false,ei:Oo3WWJ-DNYLZU8-bsKgC,_fmt:json&ei=Oo3WWJ-DNYLZU8-bsKgC&yv=2"),
        AENA("?async=rmids:%2Fg%2F11bc0c6qnf,lang:en,country:us,cd:true,{0},ext:false,ei:VI3WWPuLIMHbUfqSuZgB,_fmt:json&ei=VI3WWPuLIMHbUfqSuZgB&yv=2"),
        AMADEUS("?async=rmids:%2Fg%2F1hbvzlgwh,lang:en,country:us,cd:true,{0},ext:false,ei:LKDWWL6POMmGU5S3tPgP,_fmt:json&ei=LKDWWL6POMmGU5S3tPgP&yv=2"),
        ARCELOR_MITTAL("?async=rmids:%2Fm%2F01xxz1q,lang:en,country:us,cd:true,{0},ext:false,ei:cqDWWNHSLtC6UouPhvgN,_fmt:json&ei=cqDWWNHSLtC6UouPhvgN&yv=2"),
        //BANCO_POPULAR("?async=rmids:%2Fg%2F1hbvnb0rz,lang:en,country:us,cd:true,{0},ext:false,ei:lKvWWIiwFcKAU7uJj7AN,_fmt:json&ei=lKvWWIiwFcKAU7uJj7AN&yv=2"),
        BANCO_SABADELL("?async=rmids:%2Fg%2F1hbvlk_sy,lang:en,country:us,cd:true,{0},ext:false,ei:4avWWKzHHoHWUZagv4gB,_fmt:json&ei=4avWWKzHHoHWUZagv4gB&noj=1&yv=2"),
        BANKIA("?async=rmids:%2Fg%2F12hk0fkvl,lang:en,country:us,cd:true,{0},ext:false,ei:xqDWWL-eLsSHU_Tas6gG,_fmt:json&ei=xqDWWL-eLsSHU_Tas6gG&yv=2"),
        BANKINTER("?async=rmids:%2Fg%2F1hbvrmbsw,lang:en,country:us,cd:true,{0},ext:false,ei:8KDWWKLXE4X5ULWHjKgP,_fmt:json&ei=8KDWWKLXE4X5ULWHjKgP&yv=2"),
        BBVA("?async=rmids:%2Fm%2F0wq6xk4,lang:en,country:us,cd:true,{0},ext:false,ei:0KPWWMmuFMLYU7HYtcAN,_fmt:json&ei=0KPWWMmuFMLYU7HYtcAN&yv=2"),
//        BME("?async=rmids:%2Fg%2F1hbvvrxb1,lang:en,country:us,cd:true,{0},ext:false,ei:Mv7YWKnJB8urU5TfnLAI,_fmt:json&ei=Mv7YWKnJB8urU5TfnLAI&yv=2"),
        CAIXABANK("?async=rmids:%2Fg%2F1hbvk2l4g,lang:en,country:us,cd:true,{0},ext:false,ei:-6PWWPOGHoajU9ubm9AN,_fmt:json&ei=-6PWWPOGHoajU9ubm9AN&yv=2"),
        CELLNEX_TELECOM("?async=rmids:%2Fg%2F11bw44tjv9,lang:en,country:us,cd:true,{0},ext:false,ei:iqbWWOi6KYWvUZr0vJgD,_fmt:json&ei=iqbWWOi6KYWvUZr0vJgD&yv=2"),
        DIA("?async=rmids:%2Fg%2F12hk0qvqf,lang:en,country:us,cd:true,{0},ext:false,ei:a6fWWMmGFoSRUfqxq7AL,_fmt:json&ei=a6fWWMmGFoSRUfqxq7AL&yv=2"),
//        EBRO_FOODS("?async=rmids:%2Fg%2F1hbvngz9z,lang:en,country:us,cd:true,{0},ext:false,ei:eP_YWOq2JILWU_SxprAC,_fmt:json&ei=eP_YWOq2JILWU_SxprAC&yv=2"),
        ENAGAS("?async=rmids:%2Fg%2F1hbvq8_b4,lang:en,country:us,cd:true,{0},ext:false,ei:s6jWWJvHM8jWUbKar7AO,_fmt:json&ei=s6jWWJvHM8jWUbKar7AO&yv=2"),
        ENDESA("?async=rmids:%2Fg%2F1hbvjtn31,lang:en,country:us,cd:true,{0},ext:false,ei:OqrWWKfMBYfkUfnOhUA,_fmt:json&ei=OqrWWKfMBYfkUfnOhUA&yv=2"),
//        FCC_FOMENTO_DE_CONSTRUCCIONES_Y_CONTRATAS("?async=rmids:%2Fg%2F1hbvtx754,lang:en,country:us,cd:true,{0},ext:false,ei:8f_YWPPnM4auU7r-uIAD,_fmt:json&ei=8f_YWPPnM4auU7r-uIAD&yv=2"),
        FERROVIAL("?async=rmids:%2Fg%2F1hbvrgflj,lang:en,country:us,cd:true,{0},ext:false,ei:ZarWWJ3uCMX9UpDEkJgM,_fmt:json&ei=ZarWWJ3uCMX9UpDEkJgM&yv=2"),
//        GAMESA("?async=rmids:%2Fg%2F1hbvlszp9,lang:en,country:us,cd:true,{0},ext:false,ei:l6rWWJbcI8LZU6PphcgF,_fmt:json&ei=l6rWWJbcI8LZU6PphcgF&yv=2"),
        GAS_NATURA("?async=rmids:%2Fg%2F1hbvwsd_2,lang:en,country:us,cd:true,{0},ext:false,ei:qarWWNSbNon5UqvTgPAM,_fmt:json&ei=qarWWNSbNon5UqvTgPAM&yv=2"),
        GRIFOLS("?async=rmids:%2Fg%2F1hbvmsdn0,lang:en,country:us,cd:true,{0},ext:false,ei:zKrWWO3NCIPxUJ2LtvAJ,_fmt:json&ei=zKrWWO3NCIPxUJ2LtvAJ&yv=2"),
        IAG_IBERIA("?async=rmids:%2Fg%2F1hbvmjhqd,lang:en,country:us,cd:true,{0},ext:false,ei:7qrWWM7DLIHfU5-GlIgB,_fmt:json&ei=7qrWWM7DLIHfU5-GlIgB&yv=2"),
        IBERDROLA("?async=rmids:%2Fg%2F1hbvrs8fy,lang:en,country:us,cd:true,{0},ext:false,ei:DKvWWPGwKsPvUOCRtPAE,_fmt:json&ei=DKvWWPGwKsPvUOCRtPAE&yv=2"),
        INDITEX("?async=rmids:%2Fg%2F1hbvphj0m,lang:en,country:us,cd:true,{0},ext:false,ei:KKvWWM6YEsG9UbLgnlA,_fmt:json&ei=KKvWWM6YEsG9UbLgnlA&yv=2"),
        INDRA("?async=rmids:%2Fg%2F1hbvmqgq4,lang:en,country:us,cd:true,{0},ext:false,ei:Q6vWWJTMOcfpUpHAlPgO,_fmt:json&ei=Q6vWWJTMOcfpUpHAlPgO&yv=2"),
        INMOBILIARIA_COLONIAL("?async=rmids:%2Fg%2F1hbw01_jj,lang:en,country:us,cd:true,{0},ext:false,ei:i0L4WYWYGIbkUZv3r9AG,_fmt:json&ei=i0L4WYWYGIbkUZv3r9AG&yv=2"),
        MAPFRE("?async=rmids:%2Fg%2F1hbvtlbtp,lang:en,country:us,cd:true,{0},ext:false,ei:VKvWWJ2pC4G3UsfFk-AC,_fmt:json&ei=VKvWWJ2pC4G3UsfFk-AC&yv=2"),
        MELIA_HOTELS("?async=rmids:%2Fg%2F1hbvqgsgl,lang:en,country:us,cd:true,{0},ext:false,ei:KUD4WfrQEsKAUfDwhfAI,_fmt:json&ei=KUD4WfrQEsKAUfDwhfAI&yv=2"),
        MERLIN_PROPERTIES_MELIA_HOTELS_INT("?async=rmids:%2Fg%2F1hbvqgsgl,lang:en,country:us,cd:true,{0},ext:false,ei:zkH4WeHsIMr2UpfAibgD,_fmt:json&ei=zkH4WeHsIMr2UpfAibgD&yv=2"),
        TELE5_MEDIASET("?async=rmids:%2Fm%2F0s6g_bf,lang:en,country:us,cd:true,{0},ext:false,ei:YqvWWKzcBcbjUfOqmZgK,_fmt:json&ei=YqvWWKzcBcbjUfOqmZgK&yv=2"),
//        OHL("?async=rmids:%2Fg%2F1hbvjcsd_,lang:en,country:us,cd:true,{0},ext:false,ei:B__YWKyICoWqUYWWnPAI,_fmt:json&ei=B__YWKyICoWqUYWWnPAI&yv=2"),
        REE_RED_ELECTRICA("?async=rmids:%2Fg%2F1hbvnh09y,lang:en,country:us,cd:true,{0},ext:false,ei:pKvWWLfVKsmsU9Sgm-AL,_fmt:json&ei=pKvWWLfVKsmsU9Sgm-AL&yv=2"),
        BME_REP_REPSOL("?async=rmids:%2Fg%2F1hbvw4pky,lang:en,country:us,cd:true,{0},ext:false,ei:1ffYWJbHKsqjUMyXjvgI,_fmt:json&ei=1ffYWJbHKsqjUMyXjvgI&yv=2"),
//        MELIA_HOTELS_INTL("?async=rmids:%2Fg%2F1hbvqgsgl,lang:en,country:us,cd:true,{0},ext:false,ei:cqvWWI2UDYHTUZb4mFA,_fmt:json&ei=cqvWWI2UDYHTUZb4mFA&yv=2"),
//        MERLIN_PROP("?async=rmids:%2Fg%2F1q66rtmnd,lang:en,country:us,cd:true,{0},ext:false,ei:hKvWWNLyNsTmUvSOiZAL,_fmt:json&ei=hKvWWNLyNsTmUvSOiZAL&yv=2"),
//        SACYR_VALLHERMOSO("?async=rmids:%2Fg%2F1hbvtlbth,lang:en,country:us,cd:true,{0},ext:false,ei:rOXcWJdOhOpS8eKW0AE,_fmt:json&ei=rOXcWJdOhOpS8eKW0AE&yv=2"),
        SANTANDER("?async=rmids:%2Fm%2F0clbbbp,lang:en,country:us,cd:true,{0},ext:false,ei:86vWWKWAJ8LiU6qVjZgI,_fmt:json&ei=86vWWKWAJ8LiU6qVjZgI&noj=1&yv=2"),
        TELEFONICA("?async=rmids:%2Fm%2F01xy0t_,lang:en,country:us,cd:true,{0},ext:false,ei:GKzWWJDBGMPwUs6FpaAO,_fmt:json&ei=GKzWWJDBGMPwUs6FpaAO&noj=1&yv=2"),
        TECNICAS_REUNIDAS("?async=rmids:%2Fg%2F1hbvkfjv0,lang:en,country:us,cd:true,{0},ext:false,ei:B6zWWMH0Eon3UorfntAJ,_fmt:json&ei=B6zWWMH0Eon3UorfntAJ&noj=1&yv=2"),
        VISCOFAN("?async=rmids:%2Fg%2F1hbvl97df,lang:en,country:us,cd:true,{0},ext:false,ei:LKzWWPy8DMWqU6e6kpAB,_fmt:json&ei=LKzWWPy8DMWqU6e6kpAB&noj=1&yv=2");
        private String url;

        ENTERPRISE(String url) {
            this.url = "https://encrypted.google.com/async/finance_tnv" + url;
        }

        public String getUrl(RANGE range) {
            return MessageFormat.format(url, range.code);
        }

    }

    public enum RANGE {
        DAY("p:1d,i:8640"),
        TWO_DAY("p:2d,i:8640"),
        FIVE_DAY("p:5d,i:86400"),
        TEN_DAY("p:10d,i:86400"),
        MONTH("p:1M,i:86400"),
        THREE_MONTH("p:3M,i:86400"),
        YEAR("p:1Y,i:86400"),
        FIVE_YEAR("p:5Y,i:86400"),
        MAX("p:40Y,i:86400");
        private String code;

        RANGE(String code) {
            this.code = code;
        }
    }

//    public static void main(String[] args) {
//        new Ibex35WebFeeder().getEnterprisesEvolutions(RANGE.DAY, ENTERPRISE.FCC_FOMENTO_DE_CONSTRUCCIONES_Y_CONTRATAS);
//        out.println(ENTERPRISE.values()[0]);
//        out.println(ENTERPRISE.ABERTIS.getUrl(rangeCode));
//    }
    public double[][] getEnterprisesEvolutions(final RANGE range) {
        double[][] result;

        result = new double[banks.size()][];

        IntStream.range(0, banks.size()).parallel().forEach((i) -> {
            JsonArray jsons;
            try {
                jsons = getWebData(range, banks.get(i));
                referBankEvolutions(i, jsons, result);
                referBankVariations(i, result);
//                referBankTendence(i, result);
            } catch (IOException | URISyntaxException ex) {
                Logger.getLogger(Ibex35WebFeeder.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        return result;
    }

    private JsonArray getWebData(RANGE range, ENTERPRISE bank) throws IOException, URISyntaxException {
        JsonArray values;

        values = null;

        do {
            // get raw url data
            HttpConversation httpConversation = new HttpConversation(new URI(bank.getUrl(range))).setContentType(HttpConversation.ContentType.JSON).setRequestMethod(HttpConversation.RequestMethod.GET);

            String conversation = httpConversation.conversation();

            // obtain data as time and values
            JsonObject asJsonObject;

            String escaped;
            try {
                escaped = unescapeJavaString(String.valueOf(new JsonParser().parse(decode(conversation.split("\n")[1], "UTF-8")).getAsJsonObject().getAsJsonObject("Tnv").get("value")));
                asJsonObject = new JsonParser().parse(escaped.substring(1, escaped.length() - 1)).getAsJsonObject();
//            out.println(asJsonObject.getAsJsonArray("t"));
                values = asJsonObject.getAsJsonArray("v");
            } catch (Exception ex) {

            }
        } while (null == values);

        return (JsonArray) values.get(0);
    }

    private void referBankEvolutions(int index, JsonArray jsons, double[][] results) {
        double[] result;

        result = new double[jsons.size()];
        IntStream.range(0, jsons.size()).parallel().forEach((i) -> {
            result[i] = jsons.get(i).getAsDouble();
        });

        results[index] = result;
    }

    private void referBankVariations(int index, double[][] results) {
        double[] copy;
//        copy = new double[results[index].length];
        copy = Arrays.copyOf(results[index], results[index].length);
        results[index][results[index].length - 1] = 0;
        IntStream.range(1, results[index].length).parallel().forEach((i) -> {
            results[index][i - 1] = -1d + (copy[i] / copy[i - 1]);
        });
    }

    private void referBankTendence(int index, double[][] results) {
        IntStream.range(0, results[index].length).parallel().forEach((i) -> {
            results[index][i] = results[index][i] + results[index][i] * .5d + (Double.valueOf(i) / Double.valueOf(results[index].length));
        });
    }

    private String unescapeJavaString(String st) {

        StringBuilder sb = new StringBuilder(st.length());

        for (int i = 0; i < st.length(); i++) {
            char ch = st.charAt(i);
            if (ch == '\\') {
                char nextChar = (i == st.length() - 1) ? '\\' : st
                        .charAt(i + 1);
                // Octal escape?
                if (nextChar >= '0' && nextChar <= '7') {
                    String code = "" + nextChar;
                    i++;
                    if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
                            && st.charAt(i + 1) <= '7') {
                        code += st.charAt(i + 1);
                        i++;
                        if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
                                && st.charAt(i + 1) <= '7') {
                            code += st.charAt(i + 1);
                            i++;
                        }
                    }
                    sb.append((char) Integer.parseInt(code, 8));
                    continue;
                }
                switch (nextChar) {
                    case '\\':
                        ch = '\\';
                        break;
                    case 'b':
                        ch = '\b';
                        break;
                    case 'f':
                        ch = '\f';
                        break;
                    case 'n':
                        ch = '\n';
                        break;
                    case 'r':
                        ch = '\r';
                        break;
                    case 't':
                        ch = '\t';
                        break;
                    case '\"':
                        ch = '\"';
                        break;
                    case '\'':
                        ch = '\'';
                        break;
                    // Hex Unicode: u????
                    case 'u':
                        if (i >= st.length() - 5) {
                            ch = 'u';
                            break;
                        }
                        int code = Integer.parseInt(
                                "" + st.charAt(i + 2) + st.charAt(i + 3)
                                + st.charAt(i + 4) + st.charAt(i + 5), 16);
                        sb.append(Character.toChars(code));
                        i += 5;
                        continue;
                }
                i++;
            }
            sb.append(ch);
        }
        return sb.toString();
    }

}
