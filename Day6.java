import java.sql.SQLType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


class Coordinates {
    public final List<String> input;
    public int countPoint;
    public HashMap<Integer, HashSet<Integer>> coordXtoY = new HashMap<Integer, HashSet<Integer>>();
    public HashMap<Integer, HashSet<Integer>> coordYtoX = new HashMap<Integer, HashSet<Integer>>();
    public int startX = 0;
    public int startY = 0;

    Coordinates(List<String> input) {
        this.coordXtoY = generateXtoY(input);
        this.coordYtoX = generateYtoX(input);
        this.input = input;
    }

    private HashMap<Integer, HashSet<Integer>> generateXtoY(List<String> input){
        for (int i = 0; i < input.size(); i++){
            for (int j = 0; j < input.get(i).length(); j++){

                if(String.valueOf(input.get(i).charAt(j)).equals("#")){
                    if(!coordXtoY.containsKey(j)){
                        HashSet<Integer> initial = new HashSet<Integer>();
                        initial.add(i);
                        coordXtoY.put(j, initial);
                    } else {
                        HashSet<Integer> newValue = coordXtoY.get(j);
                        newValue.add(i);
                        coordXtoY.put(j, newValue);
                    }
                }

                if(String.valueOf(input.get(i).charAt(j)).equals("^")){
                    this.startX = j;
                    this.startY = i;
                }
            }
        }

        return coordXtoY;
    }

    private HashMap<Integer, HashSet<Integer>> generateYtoX(List<String> input){
        for (int i = 0; i < input.size(); i++){
            for (int j = 0; j < input.get(i).length(); j++){
                if(String.valueOf(input.get(i).charAt(j)).equals("#")){
                    if(!coordYtoX.containsKey(i)){
                        HashSet<Integer> initial = new HashSet<Integer>();
                        initial.add(j);
                        coordYtoX.put(i, initial);
                    } else {
                        HashSet<Integer> newValue = coordYtoX.get(i);
                        newValue.add(j);
                        coordYtoX.put(i, newValue);
                    }
                }
            }
        }

        return coordYtoX;
    }
}

public class Day6 extends BaseUtils {
    public static final String INPUT = getFileName(NUMBER_6, null);
    public static final String TEST = getFileName(NUMBER_6, NUMBER_1);
    public static final String TEST2 = getFileName(NUMBER_6, NUMBER_2);

    public static int minimum(List<Integer> input){
        int min = 0;
        if(input.isEmpty()) return -1;
        else if(input.size() == 1) return input.getFirst();
        else min = input.getFirst();
        for (Integer item : input){
            if(item < min){
                min = item;
            }
        }

        return min;
    }

    public static String nextFace(String face){
        return switch (face) {
            case ">" -> "v";
            case "v" -> "<";
            case "^" -> ">";
            case "<" -> "^";
            default -> null;
        };
    }

    public static int minDistance(HashMap<Integer, HashSet<Integer>> coord, int pivotPos, int perpendicularPos, String facing){
        List<Integer> temp = new ArrayList<>();
        for(Integer item : coord.get(pivotPos)){
            if(facing.equals("v") || facing.equals(">")){
                if(item > perpendicularPos) temp.add(item - perpendicularPos - 1);
            } else if (facing.equals("<") || facing.equals("^")){
                if(item < perpendicularPos) temp.add(perpendicularPos - item - 1);
            }
        }

        return minimum(temp);
    }

    public static List<List<Integer>> generateList(List<Integer> source, List<Integer> target){
        List<List<Integer>> initList = new ArrayList<>();

        if(source.getLast() == target.getLast()){
            int a = target.getFirst();
            int b = source.getFirst();
            int dist = a - b;
            for(int i = Math.min(a,b);  i <= Math.max(a,b); i ++){
                initList.add(List.of(i, source.getLast()));
            }
        } else {
            int a = target.getLast();
            int b = source.getLast();
            int dist = a - b;
            for(int i = Math.min(a,b);  i <= Math.max(a,b); i ++){
                initList.add(List.of(source.getFirst(), i));
            }
        }

        return initList;
    }

    public static HashSet<List<Integer>> stepRecord(List<String> input){
        Coordinates coord = new Coordinates(input);
        int initX = coord.startX;
        int initY = coord.startY;
        String face = "^";

        HashSet<List<Integer>> tempStep = new HashSet<>(List.of(List.of(initX, initY)));

        while(true){
            int dist = 0;
            if(face.equals("^") || face.equals("v")){
                dist = minDistance(coord.coordXtoY, initX, initY, face);
                if(dist == -1){
                    if(face.equals("^")){
                        tempStep.addAll(generateList(List.of(initX, initY), List.of(initX, 0)));
                    } else {
                        tempStep.addAll(generateList(List.of(initX, initY), List.of(initX, input.size() - 1)));
                    }
                    break;
                }

                if(face.equals("^")){
                    tempStep.addAll(generateList(List.of(initX, initY), List.of(initX, initY - dist)));
                    initY += (-1) * dist;
                } else {
                    tempStep.addAll(generateList(List.of(initX, initY), List.of(initX, initY + dist)));
                    initY += dist;

                }
            } else if (face.equals(">") || face.equals("<")){
                dist = minDistance(coord.coordYtoX, initY, initX, face);
                if(dist == -1){
                    //sum +=  face.equals("<") ? (initX - 1) : (input.getFirst().length() - initX - 1);
                    if(face.equals("<")){
                        tempStep.addAll(generateList(List.of(initX, initY), List.of(0, initY)));

                    } else {
                        tempStep.addAll(generateList(List.of(initX, initY), List.of(input.getFirst().length() - 1, initY)));
                        initX += dist;
                    }
                    break;
                }

                if(face.equals("<")){
                    tempStep.addAll(generateList(List.of(initX, initY), List.of(initX - dist, initY)));
                    initX += (-1) * dist;
                } else {
                    tempStep.addAll(generateList(List.of(initX, initY), List.of(initX + dist, initY)));
                    initX += dist;
                }
            }

            face = nextFace(face);
        }

        return tempStep;
    }

    public static boolean isContainLoop(List<String> input){
        Coordinates coord = new Coordinates(input);
        int initX = coord.startX;
        int initY = coord.startY;
        String face = "^";

        HashSet<List<Integer>> tempStep = new HashSet<>();

        while(true){
            List<Integer> step;
            if(face.equals("^") || face.equals("v")){
                try {
                    if(face.equals("^")){
                        step = List.of(initX, initY, 1);
                    } else {
                        step = List.of(initX, initY, 3);
                    }

                    if(tempStep.contains(step)) {
                        return true;
                    }

                    tempStep.add(step);
                    int tempY = face.equals("^") ? initY - 1: initY + 1;

                    if(String.valueOf(input.get(tempY).charAt(initX)).equals("#")){
                        face = nextFace(face);
                    } else {
                        initY = face.equals("^") ? initY - 1: initY + 1;
                    }

                } catch (IndexOutOfBoundsException err){
                    return false;
                }
            } else if (face.equals(">") || face.equals("<")){
                try {
                    if(face.equals(">")){
                        step = List.of(initX, initY, 2);
                    } else {
                        step = List.of(initX, initY, 4);
                    }

                    if(tempStep.contains(step)) {
                        return true;
                    }

                    tempStep.add(step);
                    int tempX = face.equals("<") ? initX - 1: initX + 1;
                    if(String.valueOf(input.get(initY).charAt(tempX)).equals("#")){
                        face = nextFace(face);
                    } else {
                        initX = face.equals("<") ? initX - 1: initX + 1;
                    }

                } catch (IndexOutOfBoundsException err){
                    return false;
                }
            }
        }
    }

    public static List<String> modifyInput(List<String> input, int posX, int posY, String modifyChar){
        List<String> newInput = new ArrayList<>(input);

        StringBuilder getLine = new StringBuilder(newInput.get(posY));
        getLine.replace(posX, posX + 1, modifyChar);
        newInput.set(posY, String.valueOf(getLine));

        return newInput;
    }

    public static int executePart1(List<String> input) {
        return stepRecord(input).size();
    }

    public static int executePart2(List<String> input) {
        int sum = 0;
        Coordinates coords = new Coordinates(input);
        int initX = coords.startX;
        int initY = coords.startY;

        HashSet<List<Integer>> records = stepRecord(input);

        for (List<Integer> rec : records){
            if(rec.getFirst() == initX && rec.getLast() == initY) continue;
            List<String> modify = modifyInput(input, rec.getFirst(), rec.getLast(), "#");
            boolean loopCheck = isContainLoop(modify);

            if(loopCheck){
                sum += 1;
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        List<String> input = parseFile(INPUT);
        showResult(() -> executePart2(input));
    }
}
