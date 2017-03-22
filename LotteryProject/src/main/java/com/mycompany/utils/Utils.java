package com.mycompany.utils;

import com.mycompany.constants.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Utils {

    public static Integer findCredits(String input, String winning) throws Exception {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(Constants.NULL_OR_EMPTY_WINN);
        } else if (winning == null || winning.trim().isEmpty()) {
            throw new IllegalArgumentException(Constants.NULL_OR_EMPTY_INPUT);
        }

        int amountCredits = 0;
        String w = input;
        int index = 0;
        for (int i = 0; i < winning.length(); i++) {
            char v = winning.charAt(i);
            if (w.contains(String.valueOf(v))) {
                index = w.indexOf(v);
                amountCredits++;
                w = w.substring(index + 1);
                if (w.isEmpty()) {
                    break;
                }
            }
        }
        return amountCredits;
    }

    public static Optional<String> getInputFileName(String[] args) throws Exception {
        if (args == null) {
            throw new Exception(Constants.WRONG_ARGS);
        }
        return Optional.ofNullable(args[0]);
    }

    public static Optional<String> getWinningNumber(String[] args) throws Exception {
        if (args == null) {
            throw new Exception(Constants.WRONG_ARGS);
        }
        return Optional.ofNullable(args[1]);
    }

    public static List<Map<String, String>> countCreditLines(List<Map<String, String>> linesMaps, String winning) throws Exception {
        List<Map<String, String>> creditsMaps = new ArrayList();
        for (Map lineMap : linesMaps) {
            Integer credits1 = Utils.findCredits((String) lineMap.get(Constants.TK), winning);
            Integer credits2 = Utils.findCredits(winning, (String) lineMap.get(Constants.TK));

            Integer credits = credits1 > credits2 ? credits1 : credits2;

            lineMap.replace(Constants.CR, credits.toString());
            creditsMaps.add(lineMap);
        }
        return creditsMaps;
    }

    public static void printWinnersOnly(List<Map<String, String>> creditMaps) {
        System.out.println("Results of lottery:");
        creditMaps.stream().filter((creditMap) -> (!Integer.valueOf((String) creditMap.get(Constants.CR)).equals(0))).map((creditMap)
                -> String.format("%s, %s, %s, %s",
                        creditMap.get(Constants.LN),
                        creditMap.get(Constants.FN),
                        creditMap.get(Constants.CN),
                        creditMap.get(Constants.CR))).forEachOrdered((str) -> {
            System.out.println(str);
        });
    }

    public static List<Map<String, String>> joinParticipants(List<Map<String, String>> creditMaps) {
        List<Map<String, String>> joined = new ArrayList();
        joined.add(creditMaps.get(0));
        for (int i = 1; i < creditMaps.size(); i++) {
            Integer index = joinedConsistsParticipant(joined, creditMaps.get(i));
            if (index != null) {
                int currCredits = Integer.parseInt(joined.get(index).get(Constants.CR));
                int newCredits = Integer.parseInt(creditMaps.get(i).get(Constants.CR));
                joined.get(index).replace(Constants.CR, String.valueOf(currCredits + newCredits));
            } else {
                joined.add(creditMaps.get(i));
            }
        }
        return joined;
    }

    private static Integer joinedConsistsParticipant(List<Map<String, String>> joined, Map participant) {
        for (Map map : joined) {
            if (map.get(Constants.LN).equals(participant.get(Constants.LN))
                    && map.get(Constants.FN).equals(participant.get(Constants.FN))
                    && map.get(Constants.CN).equals(participant.get(Constants.CN))) {
                return joined.indexOf(map);
            }
        }
        return null;
    }

    /**
     * Sort list of maps by Surname, Firstname and Country.
     */
    public static List<Map<String, String>> sortParticipants(List<Map<String, String>> joined) {
        Collections.sort(joined, (Map<String, String> o1, Map<String, String> o2) -> {
            int res = o1.get(Constants.LN).compareTo(o2.get(Constants.LN));
            if (res == 0) {
                res = o1.get(Constants.FN).compareTo(o2.get(Constants.FN));
            }
            if (res == 0) {
                res = o1.get(Constants.CN).compareTo(o2.get(Constants.CN));
            }
            return res;
        });
        return joined;
    }

    public static void checkWinning(String winning) throws NumberFormatException {
        Integer.parseInt(winning);
    }
}
