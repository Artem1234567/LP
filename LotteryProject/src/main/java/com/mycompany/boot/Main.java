package com.mycompany.boot;

import com.mycompany.constants.Constants;
import com.mycompany.utils.FSUtils;
import com.mycompany.utils.Utils;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Artem S
 */
public class Main {
    
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        System.out.println("Welcome to the LCS lottery!");
        try {
            Optional<String> fileName = Utils.getInputFileName(args);
            Optional<String> winningNumber = Utils.getWinningNumber(args);
            if (!winningNumber.isPresent()) {
                System.out.println("Sorry, winning number was not set!");
                return;
            }
            Utils.checkWinning(winningNumber.get());
            
            if (fileName.isPresent()) {
                List lines = FSUtils.readLinesInputFile(fileName.get());
                List<Map<String, String>> linesMaps = FSUtils.parseLines(lines);
                List<Map<String, String>> creditMaps = Utils.countCreditLines(linesMaps, winningNumber.get());
                List<Map<String, String>> joined = Utils.joinParticipants(creditMaps);
                List<Map<String, String>> sorted = Utils.sortParticipants(joined);
                
                Utils.printWinnersOnly(sorted);
                
            } else {
                System.out.println(Constants.NULL_OR_EMPTY_FILENAME);
            }
            
        } catch (NumberFormatException ex) {
            System.out.println(Constants.WRONG_WINNING_TYPE);
        } catch(ArrayIndexOutOfBoundsException ex) {
            System.out.println(Constants.NULL_OR_EMPTY_WINN_INP);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("Good bay!");
        }
    }
}
