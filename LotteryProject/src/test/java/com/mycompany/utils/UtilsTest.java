package com.mycompany.utils;

import com.mycompany.constants.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class UtilsTest {

    private static final String winNull = null;
    private static final String inputNull = null;

    private static final String winning01 = "456000123";
    
    @Test
    public void testFindCredits() throws Exception {

        Throwable thr0 = catchThrowable(() -> Utils.findCredits(winning01, inputNull));
        assertThat(thr0).isInstanceOf(IllegalArgumentException.class).hasMessage(Constants.NULL_OR_EMPTY_INPUT);

        String input0 = "9988778899";
        Throwable thr1 = catchThrowable(() -> Utils.findCredits(winNull, input0));
        assertThat(thr1).isInstanceOf(IllegalArgumentException.class).hasMessage(Constants.NULL_OR_EMPTY_WINN);

        String input01 = "9988778899";
        int expCred01 = 0;
        int credits01 = Utils.findCredits(input01, winning01);
        assertThat(credits01).isEqualTo(expCred01);

        String input02 = "1111999911";
        int expCred02 = 1;
        int credits02 = Utils.findCredits(input02, winning01);
        assertThat(credits02).isEqualTo(expCred02);

        String input03 = "9988776650";
        int expCred03 = 2;
        int credits03 = Utils.findCredits(input03, winning01);
        assertThat(credits03).isEqualTo(expCred03);

        String input04 = "0000000000";
        int expCred04 = 3;
        int credits04 = Utils.findCredits(input04, winning01);
        assertThat(credits04).isEqualTo(expCred04);

        String input05 = "1122334455";
        int expCred05 = 3;
        int credits05 = Utils.findCredits(winning01, input05);
        assertThat(credits05).isEqualTo(expCred05);

        String input06 = "1234567890";
        int expCred06 = 4;
        int credits06 = Utils.findCredits(input06, winning01);
        assertThat(credits06).isEqualTo(expCred06);

        String input07 = "4680468023";
        int expCred07 = 6;
        int credits07 = Utils.findCredits(input07, winning01);
        assertThat(credits07).isEqualTo(expCred07);
    }
    
    @Test
    public void testSortParticipants() {
        System.out.println("People");
        List<Map<String, String>> people = new ArrayList<Map<String, String>>();
        people.add(new HashMap() {
            {
                put(Constants.LN, "Alison");
                put(Constants.FN, "Alice");
                put(Constants.CN, "Austria");
                put(Constants.CR, "6");
            }
        });
        people.add(new HashMap() {
            {
                put(Constants.LN, "Bert");
                put(Constants.FN, "Bertram");
                put(Constants.CN, "Belgium");
                put(Constants.CR, "2");
            }
        });
        people.add(new HashMap() {
            {
                put(Constants.LN, "Carlson");
                put(Constants.FN, "Cynthia");
                put(Constants.CN, "China");
                put(Constants.CR, "1");
            }
        });
        people.add(new HashMap() {
            {
                put(Constants.LN, "Carlson");
                put(Constants.FN, "Cynthia");
                put(Constants.CN, "Chile");
                put(Constants.CR, "3");
            }
        });
        people.add(new HashMap() {
            {
                put(Constants.LN, "Daneson");
                put(Constants.FN, "Dan Dilbert");
                put(Constants.CN, "Denmark");
                put(Constants.CR, "6");
            }
        });
        people.add(new HashMap() {
            {
                put(Constants.LN, "Dilbert Daneson");
                put(Constants.FN, "Dan");
                put(Constants.CN, "Denmark");
                put(Constants.CR, "3");
            }
        });
        System.out.println("Sort by SN and FN anc CN:");

        List<Map<String, String>> sorted = Utils.sortParticipants(people);
        Map<String, String> first = sorted.get(0);
        String LN1 = first.get(Constants.LN);
        String FN1 = first.get(Constants.FN);
        String CN1 = first.get(Constants.CN);
        assertThat(LN1).isEqualTo("Alison");
        assertThat(FN1).isEqualTo("Alice");
        assertThat(CN1).isEqualTo("Austria");

        Map<String, String> last = sorted.get(people.size() - 1);
        String LN2 = last.get(Constants.LN);
        String FN2 = last.get(Constants.FN);
        String CN2 = last.get(Constants.CN);
        assertThat(LN2).isEqualTo("Dilbert Daneson");
        assertThat(FN2).isEqualTo("Dan");
        assertThat(CN2).isEqualTo("Denmark");
    }
}
