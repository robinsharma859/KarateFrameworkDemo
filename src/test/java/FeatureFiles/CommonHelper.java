package FeatureFiles;
import ch.qos.logback.core.util.FileUtil;
import com.intuit.karate.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommonHelper {


    public  static List<String> Files(String location) {
        List<String> jsonFiles = new ArrayList<String>();
        try{
            File files = new File(location);
         File[] fileList =    files.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if(pathname.getName().endsWith(".json"))
                    {
                        return true;
                    }
                    return false;
                }
            });
         System.out.println(fileList);
         for (File file : fileList)
         {
             jsonFiles.add((file.getName()));
         }
        }
        catch (Exception ex)
        {

        }
        return jsonFiles;
    }

    public static String GetToken()
    {
        return  "Token Generated";

    }
}



