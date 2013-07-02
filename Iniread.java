/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iniread;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author G2
 */
public class Iniread {

    private static Pattern bp = Pattern.compile("(true)|(false)");
    private static Pattern np = Pattern.compile("^[0-9]*$");
    private static Pattern dp = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            String path = getPath();
            System.out.println(path);
            Map config = new HashMap();
            readini(path, config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPath() {
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);

            String path = br.readLine();
            isr.close();
            br.close();
            return path;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void readini(
            String path,
            Map config)
            throws IOException {
        String currentline;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        try {
            while ((currentline = bufferedReader.readLine()) != null) {
                while (currentline.charAt(0) == '#') {
                    currentline = bufferedReader.readLine();
                }
                currentline = currentline.trim();
                String[] strArray = currentline.split("=");
                if (strArray.length == 1) {
                    String key = strArray[0].trim();
                    config.put(key, null);
                } else if (strArray.length == 2) {
                    String key = strArray[0].trim();
                    String value = strArray[1].trim();
                    Matcher bm = bp.matcher(value);
                    Matcher nm = np.matcher(value);
                    Matcher dm = dp.matcher(value);
                    if (bm.matches()) {
                        config.put(key, Boolean.parseBoolean(value));
                        //break;
                    } else if (nm.matches()) {
                        config.put(key, Integer.parseInt(value));
                        //break;
                    } else if (dm.matches()) {
                        config.put(key, Double.parseDouble(value));
                        //break;
                    } else {
                        config.put(key, value);
                    }
                }
            }
        } finally {
            bufferedReader.close();
        }
    }
}
