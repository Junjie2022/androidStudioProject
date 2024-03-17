package info.hccis.grading.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

/**
 * Has some useful methods to be used in our programs.
 *
 * @author bjmaclean
 * @since Oct 19, 2021
 */
public class CisUtility {

    private static Scanner input = new Scanner(System.in);

    /**
     * Return the default currency String value of the double passed in as a
     * parameter.
     *
     * @param inputDouble double to be formatted
     * @return String in default currency format
     * @author BJM
     * @since 20211020
     */
    public static String toCurrency(double inputDouble) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(inputDouble);
    }

    /**
     * Get input from the user using the console
     *
     * @param prompt Prompt for the user
     * @return String entered by the user
     * @author BJM
     * @since 20211020
     */
    public static String getInputString(String prompt) {

        System.out.println(prompt + " -->");
        String output = input.nextLine();
        return output;
    }

    /**
     * Get input from the user using the console
     *
     * @param prompt Prompt for the user
     * @return The double entered by the user
     * @author BJM
     * @since 20211020
     */
    public static double getInputDouble(String prompt) {

        String inputString = getInputString(prompt);
        double output = Double.parseDouble(inputString);
        return output;
    }

    /**
     * Get input from the user using the console
     *
     * @param prompt Prompt for the user
     * @return The double entered by the user
     * @author BJM
     * @since 20211020
     */
    public static int getInputInt(String prompt) {

        String inputString = getInputString(prompt);
        int output = Integer.parseInt(inputString);
        return output;
    }

    /**
     * Get input boolean from the user using the console
     *
     * @param prompt Prompt for the user
     * @return boolean as specified by user input
     * @author BJM
     * @since 20211108
     */
    public static boolean getInputBoolean(String prompt) {

        String inputString = getInputString(prompt + " (y/n)");
        if (inputString.equalsIgnoreCase("y")) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Get input boolean from the user using the console
     *
     * @param prompt Prompt for the user
     * @return boolean as specified by user input
     * @author BJM
     * @since 20211108
     */
    public static boolean getInputBoolean(String prompt, String affirmative, String negative) {

        String inputString = getInputString(prompt + " (" + affirmative + "/" + negative + ")");
        if (inputString.equalsIgnoreCase(affirmative)) {
            return true;
        } else {
            return false;
        }

    }


    /**
     * Provide today's date in the specified format
     * https://mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/
     *
     * @param format Date format desired
     * @return Today's date in specified format
     * @author BJM
     * @since 20211021
     */
    public static String getTodayString(String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd";
        }
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());

    }

    /**
     * Get a random number between min and max
     *
     * @author BJM
     * @since 20211109
     */
    public static int getRandom(int min, int max) {
        Random rand = new Random();
        int theRandomNumber = rand.nextInt((max - min) + 1) + min;
        return theRandomNumber;
    }

    public static void createFile(Activity activity, String fileName) {

        try (
                FileOutputStream fos = activity.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            Toast.makeText(activity.getApplicationContext(), "Created " + fileName, Toast.LENGTH_SHORT).show();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeToFile(Activity activity, String fileName, String content) {
        try (FileOutputStream fos = activity.openFileOutput(fileName, Context.MODE_APPEND)) {
            fos.write(content.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String readFromFile(Activity activity, String fileName){
        FileInputStream fis;
        try {
            fis = activity.openFileInput(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder strBuild = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(isr)) {
            String line = reader.readLine();
            while (line != null) {
                strBuild.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return strBuild.toString();
    }
}
