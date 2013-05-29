package cz.muni.pb138.log4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;

public class AppUtils {
    private static Logger log = Logger.getLogger(AppUtils.class);
    
    public static void crash(String errorMessage) {
        log.error(errorMessage);
        throw new RuntimeException(errorMessage);
    }
    
    public static void crash(String errorMessage, Exception exception) {
        log.error(errorMessage);
        log.debug(errorMessage, exception);
        throw new RuntimeException(errorMessage,exception);
    }
    
    public static String prefix(String str) {
        return "log4j." + str;        
    }
    
    public static String join(List<?> list, String delim) {
        int len = list.size();
        if (len == 0) {
                return "";
            }

        StringBuilder sb = new StringBuilder(list.get(0).toString());
        for (int i = 1; i < len; i++) {
            sb.append(delim);
            sb.append(list.get(i).toString());
        }

        return sb.toString();
    }
    
    public static void store(List<String> prop, File file) {
       
        
        try {
            if (!file.exists()) {
                    file.createNewFile();
            }                        
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            //initial line
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            bw.write("# generated by log4j converter, time: " + dateFormat.format(date));
            bw.newLine();            
            bw.newLine();
            
            
            for (String line : prop) {               
                bw.write(line);
                bw.newLine();
            }
            
            bw.close();


        } catch (IOException e) {
                crash("Cannot write into output file", e);
        }
    }
    
    public static List<String> addParams (List<String> prop, String prefix, Map<String, String> params ) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
                prop.add(AppUtils.prefix(prefix + "." + entry.getKey() + " = "+entry.getValue()));
        }
        return prop;
    }
}
