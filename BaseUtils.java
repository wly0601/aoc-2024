import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BaseUtils {

    private long startTime;

    public static final DecimalFormat df = new DecimalFormat("0.00000");
    public static final String STRING_TO_STRING = "stringToString";
    public static final String STRING_TO_ARRAY = "stringToArray";
    public static final String BASE_INPUT_PATH = "inputs";
    public static final String INPUT_FORMAT = ".txt";
    public static final String NUMBER_1 = "1";
    public static final String NUMBER_2 = "2";
    public static final String NUMBER_3 = "3";
    public static final String NUMBER_4 = "4";
    public static final String NUMBER_5 = "5";
    public static final String NUMBER_6 = "6";
    public static final String NUMBER_7 = "7";
    public static final String NUMBER_8 = "8";
    public static final String NUMBER_9 = "9";
    public static final String NUMBER_10 = "10";
    public static final String NUMBER_11 = "11";
    public static final String NUMBER_12 = "12";
    public static final String NUMBER_13 = "13";
    public static final String NUMBER_14 = "14";
    public static final String NUMBER_15 = "15";
    public static final String NUMBER_16 = "16";
    public static final String NUMBER_17 = "17";
    public static final String NUMBER_18 = "18";
    public static final String NUMBER_19 = "19";
    public static final String NUMBER_20 = "20";
    public static final String NUMBER_21 = "21";
    public static final String NUMBER_22 = "22";
    public static final String NUMBER_23 = "23";
    public static final String NUMBER_24 = "24";
    public static final String NUMBER_25 = "25";
    public static final String PART_1 = "PART1";
    public static final String PART_2 = "PART2";

    public static String getFileName(String dayNo, String testNo) {
        return testNo == null ? "d" + dayNo : "d" + dayNo + "-t" + testNo;
    }

    public static List<Integer> parseToInt(List<String> input){
        List<Integer> res = new ArrayList<Integer>();
        for (String item : input){
            res.add(Integer.parseInt(item));
        }

        return res;
    }

    public static List<List<String>> convertTo2DList(List<String> grid){
        List<List<String>> res = new ArrayList<List<String>>();
        for (String s : grid){
            res.add(List.of(s.split("")));
        }

        return res;
    }

    public static List<String> generatePadding(List<String> grid, int top, int left, int bottom, int right){
        List<String> newGrid = new ArrayList<String>(grid);
        String newRow = " ".repeat(left + right + newGrid.getFirst().length());
        newGrid.replaceAll(item -> " ".repeat(left) + item + " ".repeat(right));
        for (int i = 0; i < top; i ++){
            newGrid.addFirst(newRow);
        }

        for (int i = 0; i < bottom; i ++){
            newGrid.add(newRow);
        }

        return newGrid;
    }

    public static List<String> parseFile(String fileName) {
        String filePath = BASE_INPUT_PATH + "/" + fileName + INPUT_FORMAT;
        List<String> result = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            result.addAll(lines);
        } catch (IOException e) {
            System.out.println("File not Found : " + e.getMessage());
        }

        return result;
    }

    public static <T> void showResult(Supplier<T> input) {
        long startTime = System.nanoTime();
        System.out.println("Answer \t: " + input.get());
        double executedTime = (double) (System.nanoTime() - startTime) / 1000000;
        System.out.printf("Time \t: " + df.format(executedTime) + " ms" + "\n");
        System.out.println("---------------------------- ");
    }
}
