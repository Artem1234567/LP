package com.mycompany.utils;

import com.mycompany.constants.Constants;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class FSUtils {
    
    public static List readLinesInputFile(String fileName) throws IOException {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException(Constants.NULL_OR_EMPTY_FILENAME);
        }
        return FileUtils.readLines(new File(Constants.FILES_DIRECTORY, fileName), Constants.ENCODING_NAME);
    }
    
    public static List<Map<String, String>> parseLines(List list) throws Exception {
        if (list == null) {
            throw new IllegalArgumentException(Constants.NULL_LINES_LIST);
        } else if (list.isEmpty()) {
            throw new Exception(Constants.EMPTY_LINES_LIST);
        }
        List<Map<String, String>> maps = new ArrayList();
        for (Object lineObj : list) {
            String line = String.valueOf(lineObj);
            String[] params = splitLine(line);
            Map lineMap = getMapFromLine(params);
            maps.add(lineMap);
        }
        return maps;
    }
    
    private static String[] splitLine(String line) throws Exception {
        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException(Constants.NULL_OR_EMPTY_LINE);
        }
        return line.split(Constants.LINE_DELIMITER);
    }

    public static Map getMapFromLine(String[] params) throws Exception {
        if (params == null) {
            throw new IllegalArgumentException(Constants.NULL_LINE_PARAMS);
        } else if (params.length != 4) {
            throw new Exception(Constants.NOT_CORRECT_LINE_PARAMS);
        }
        Map map = new HashMap(5);
        map.put(Constants.LN, params[0]);
        map.put(Constants.FN, params[1]);
        map.put(Constants.CN, params[2]);
        map.put(Constants.TK, params[3]);
        map.put(Constants.CR, null);
        
        return map;
    }
}
