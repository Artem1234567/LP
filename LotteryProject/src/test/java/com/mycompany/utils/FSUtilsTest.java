package com.mycompany.utils;

import com.mycompany.constants.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import static org.assertj.core.api.Assertions.catchThrowable;

public class FSUtilsTest {
    
    @Test
    public void testReadLinesInputFile() throws Exception {
        String fileNameNull = null;
        Throwable thr0 = catchThrowable(() -> FSUtils.readLinesInputFile(fileNameNull));
        assertThat(thr0).isInstanceOf(IllegalArgumentException.class).hasMessage(Constants.NULL_OR_EMPTY_FILENAME);
        
        String fileNameIncorrect = "some";
        Throwable thr01 = catchThrowable(() -> FSUtils.readLinesInputFile(fileNameIncorrect));
        assertThat(thr01.getMessage()).contains(Constants.NOT_EXIST_FILE);
        
        String fileName01 = "names.txt";
        int expLines = 8;
        List lines = FSUtils.readLinesInputFile(fileName01);
        assertThat(lines.size()).isEqualTo(expLines);
    }
    
    @Test
    public void testParseLines() throws Exception {
        List nullList = null;
        Throwable thr0 = catchThrowable(() -> FSUtils.parseLines(nullList));
        assertThat(thr0).isInstanceOf(IllegalArgumentException.class).hasMessage(Constants.NULL_LINES_LIST);
        
        List empList = new ArrayList();
        Throwable thr1 = catchThrowable(() -> FSUtils.parseLines(empList));
        assertThat(thr1).isInstanceOf(Exception.class).hasMessage(Constants.EMPTY_LINES_LIST);
        
        List wrongTypeList = new ArrayList();
        wrongTypeList.add("Alison,Alice,Austria,1234567890");
        wrongTypeList.add(12);
        Throwable thr2 = catchThrowable(() -> FSUtils.parseLines(wrongTypeList));
        assertThat(thr2).isInstanceOf(Exception.class).hasMessage(Constants.NOT_CORRECT_LINE_PARAMS);
    }
    
    @Test
    public void testGetMapFromLine() throws Exception {
        Throwable thr0 = catchThrowable(() -> FSUtils.getMapFromLine(null));
        assertThat(thr0).isInstanceOf(IllegalArgumentException.class).hasMessage(Constants.NULL_LINE_PARAMS);
        
        Throwable thr00 = catchThrowable(() -> FSUtils.getMapFromLine(new String[]{}));
        assertThat(thr00).isInstanceOf(Exception.class).hasMessage(Constants.NOT_CORRECT_LINE_PARAMS);
        
        String[] str01 = {"Alison", "Alice"};
        Throwable thr1 = catchThrowable(() -> FSUtils.getMapFromLine(str01));
        assertThat(thr1).isInstanceOf(Exception.class).hasMessage(Constants.NOT_CORRECT_LINE_PARAMS);
        
        String[] str02 = {"Alison", "Alice", "Austria", "1234567890"};
        Map exp = new HashMap();
        exp.put(Constants.LN, "Alison");
        exp.put(Constants.FN, "Alice");
        exp.put(Constants.CN, "Austria");
        exp.put(Constants.TK, "1234567890");
        exp.put(Constants.CR, null);
        
        Map params = FSUtils.getMapFromLine(str02);
        assertThat(params.size()).isEqualTo(exp.size());
        assertThat(params.get(Constants.LN)).isEqualTo("Alison");
        assertThat(params.get(Constants.FN)).isEqualTo("Alice");
        assertThat(params.get(Constants.CN)).isEqualTo("Austria");
        assertThat(params.get(Constants.TK)).isEqualTo("1234567890");
        assertThat(params.get(Constants.CR)).isNull();
    }
}
