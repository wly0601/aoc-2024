import java.util.List;

class Grid {
    public final List<String> grid;
    private final int length;
    public String target;

    Grid(List<String> input, String target) {
        this.grid = input;
        this.target = target;
        this.length = target.length();
    }

    public String getRight(int posX, int posY){
        StringBuilder res = new StringBuilder();
        for (int i = posX; i < posX + this.length; i ++){
            res.append(this.grid.get(posY).charAt(i));
        }
        return res.toString();
    }

    public String getLeft(int posX, int posY){
        StringBuilder res = new StringBuilder();
        for (int i = posX; i > posX - this.length; i --){
            res.append(this.grid.get(posY).charAt(i));
        }
        return res.toString();
    }

    public String getTop(int posX, int posY){
        StringBuilder res = new StringBuilder();
        for (int i = posY; i > posY - this.length; i --){
            res.append(this.grid.get(i).charAt(posX));
        }
        return res.toString();
    }

    public String getBottom(int posX, int posY){
        StringBuilder res = new StringBuilder();
        for (int i = posY; i < posY + this.length; i ++){
            res.append(this.grid.get(i).charAt(posX));
        }
        return res.toString();
    }

    public String getBottomRight(int posX, int posY){
        StringBuilder res = new StringBuilder();
        int offset = 0;
        for (int i = posX; i < posX + this.length; i ++){
            res.append(this.grid.get(posY + offset).charAt(i));
            offset ++;
        }
        return res.toString();
    }

    public String getTopRight(int posX, int posY){
        StringBuilder res = new StringBuilder();
        int offset = 0;
        for (int i = posX; i < posX + this.length; i ++){
            res.append(this.grid.get(posY + offset).charAt(i));
            offset --;
        }
        return res.toString();
    }

    public String getBottomLeft(int posX, int posY){
        StringBuilder res = new StringBuilder();
        int offset = 0;
        for (int i = posX; i > posX - this.length; i --){
            res.append(this.grid.get(posY + offset).charAt(i));
            offset ++;
        }
        return res.toString();
    }

    public String getTopLeft(int posX, int posY){
        StringBuilder res = new StringBuilder();
        int offset = 0;
        for (int i = posY; i > posY - this.length; i --){
            res.append(this.grid.get(i).charAt(posX + offset));
            offset --;
        }
        return res.toString();
    }
}

public class Day4 extends BaseUtils {
    public static final String INPUT = getFileName(NUMBER_4, null);
    public static final String TEST = getFileName(NUMBER_4, NUMBER_1);
    public static final String TEST2 = getFileName(NUMBER_4, NUMBER_2);
    public static final String XMAS = "XMAS";
    public static final int START_X = XMAS.length() - 1;
    public static final int START_Y = XMAS.length() - 1;
    public static final int PADDING = XMAS.length() - 1;

    public static boolean findXMas(List<String> grid, int posX, int posY){
        String topLeft = String.valueOf(grid.get(posY - 1).charAt(posX - 1));
        String topRight = String.valueOf(grid.get(posY - 1).charAt(posX + 1));
        String bottomLeft = String.valueOf(grid.get(posY + 1).charAt(posX - 1));
        String bottomRight = String.valueOf(grid.get(posY + 1).charAt(posX + 1));

        if(topLeft.equals(topRight) && topLeft.equals("M") && bottomLeft.equals(bottomRight) && bottomLeft.equals("S")) {
            return true;
        } else if (topLeft.equals(bottomLeft) && topLeft.equals("M") && topRight.equals(bottomRight) && topRight.equals("S")){
            return true;
        }  else if (bottomLeft.equals(bottomRight) && bottomLeft.equals("M") && topLeft.equals(topRight) && topRight.equals("S")){
            return true;
        } else if (bottomRight.equals(topRight) && bottomRight.equals("M") && topLeft.equals(bottomLeft) && topLeft.equals("S")){
            return true;
        }

        return false;
    }

    public static int executePart1(List<String> input) {
        int count = 0;
        List<String> extendedInput = generatePadding(input, PADDING, PADDING, PADDING,PADDING);
//        System.out.println(extendedInput);
        Grid newInput = new Grid(extendedInput, XMAS);

        for (int i = START_Y; i < extendedInput.size() - START_Y; i ++) {
            for (int j = START_X; j < extendedInput.get(i).length() - START_X; j++){
                if(newInput.getRight(j, i).equals(XMAS)){
//                    System.out.println(j + "," + i + " -> right " + newInput.getRight(j, i));
                    count ++;
                }
                if(newInput.getLeft(j, i).equals(XMAS)){
//                    System.out.println(j + "," + i + " -> left " + newInput.getLeft(j, i));
                    count ++;
                }
                if(newInput.getTop(j, i).equals(XMAS)){
//                    System.out.println(j + "," + i + " -> top " + newInput.getTop(j, i));
                    count ++;
                }
                if(newInput.getBottom(j, i).equals(XMAS)){
//                    System.out.println(j + "," + i + " -> bottom " + newInput.getBottom(j, i));
                    count ++;
                }
                if(newInput.getTopRight(j, i).equals(XMAS)){
//                    System.out.println(j + "," + i + " -> top-right " + newInput.getTopRight(j, i));
                    count ++;
                }
                if(newInput.getBottomRight(j, i).equals(XMAS)){
//                    System.out.println(j + "," + i + " -> bottom-right " + newInput.getBottomRight(j, i));
                    count ++;
                }
                if(newInput.getBottomLeft(j, i).equals(XMAS)){
//                    System.out.println(j + "," + i + " -> bottom-left " + newInput.getBottomLeft(j, i));
                    count ++;
                }
                if(newInput.getTopLeft(j, i).equals(XMAS)){
//                    System.out.println(j + "," + i + " -> top-left " + newInput.getTopLeft(j, i));
                    count ++;
                }

            }
        }
        return count;
    }

    public static int executePart2(List<String> input) {
        int count = 0;
        for (int i = 1; i < input.size() - 1; i ++) {
            for (int j = 1; j < input.get(i).length() - 1; j++){
                if(String.valueOf(input.get(i).charAt(j)).equals("A") && findXMas(input, j, i)){
                    count ++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        List<String> input = parseFile(INPUT);
        showResult(() -> executePart1(input));
    }
}