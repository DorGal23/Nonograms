import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Solution {

    //public int fitness;                     // Current fitness
    public double fitness;                     // Current fitness
    public StringBuilder chromosome;        // Flat matrix
    private Nonogram nonogram;
    public LinkedList<String>[] rowSolutions;   // List of possible row solutions, each cell represents a row
    public LinkedList<String>[] colSolutions;   // List of possible column solutions, each cell represents a column

    public Solution(Nonogram nonogram){
        this.nonogram = nonogram;
        fitness = -1;

        chromosome = new StringBuilder(nonogram.rows * nonogram.columns);

        for(int i=0; i < nonogram.rows * nonogram.columns; i++){
            chromosome.insert(i, Math.random() < 0.5 ? '0' : '1');
        }

        rowSolutions = new LinkedList[nonogram.rows];
        colSolutions = new LinkedList[nonogram.columns];
        for(int i=0; i<nonogram.rows; i++){
            rowSolutions[i] = new LinkedList<>();
        }
        for(int i=0; i<nonogram.rows; i++){
            colSolutions[i] = new LinkedList<>();
        }

        calcSolutions();
        calcFitness();
    }

//    public void calcFitness(){
//        fitness = rowsAndColsFitness();
//    }
//
//    private double rowsAndColsFitness(){
//        double rowsSum = checkGoodRows();
//        double colsSum = checkGoodCols();
//        return rowsSum+colsSum;
//    }

    /**------------------NEW FITNESS----------------**/

    public void calcFitness(){
        fitness = rowsAndColsFitness();
    }

    private double rowsAndColsFitness(){
        double singleFitness=0;
        double maxSingleFitness=0;
        double totalFitness=0;
        String solution;

        for (int i=0; i<rowSolutions.length; i++){                                      // all rows solutions
            for (int j=0; j<rowSolutions[i].size(); j++){                               // single row solutions
                solution = rowSolutions[i].get(j);
                String row = getRow(i);
                if (solution.charAt(0) == getRow(i).charAt(0)){
                    singleFitness++;
                }
                for (int k=1; k<solution.length(); k++){
                    if (solution.charAt(k) == getRow(i).charAt(k)){
                        singleFitness++;
                    }
                    singleFitness = singleFitness/2;
                }
                if (singleFitness > maxSingleFitness){
                    maxSingleFitness = singleFitness;
                }
                singleFitness=0;
            }
            totalFitness += maxSingleFitness;
            singleFitness = 0;
            maxSingleFitness = 0;
        }

        for (int i=0; i<colSolutions.length; i++){                                      // iterate all cols solutions
            for (int j=0; j<colSolutions[i].size(); j++){                               // single col solutions
                solution = colSolutions[i].get(j);
                if (solution.charAt(0) == getColumn(i).charAt(0)){
                    singleFitness++;
                }
                for (int k=0; k<solution.length(); k++){
                    if (solution.charAt(k) == getColumn(i).charAt(k)){
                        singleFitness++;
                    }
                    singleFitness = singleFitness/2;
                }
                if (singleFitness > maxSingleFitness){
                    maxSingleFitness = singleFitness;
                }
                singleFitness=0;
            }
            totalFitness += maxSingleFitness;
            singleFitness = 0;
            maxSingleFitness = 0;
        }

        //totalFitness = totalFitness/(nonogram.columns+nonogram.rows);
        return totalFitness;
    }

    /**----------------END NEW FITNESS--------------**/

    /*Returns the number of rows following the clues*/
    private int checkGoodRows(){
        int sum = 0;
        for (int i=0; i<nonogram.rows; i++){
            sum += checkRow(i);
        }
        return sum;
    }

    /*Returns the number of columns following the clues*/
    private int checkGoodCols(){
        int sum = 0;
        for (int i=0; i<nonogram.columns; i++){
            sum += checkCol(i);
        }
        return sum;
    }

    /*Return 1 if row number rowIndex follows the clues, 0 otherwise*/
    private int checkRow(int rowIndex){
        int [] rowClues = nonogram.rowsClues[rowIndex];
        String row = getRow(rowIndex);

        return checkStringToClues(row, rowClues);
    }

    /*Return 1 if column number columnIndex follows the clues, 0 otherwise*/
    private int checkCol(int columnIndex){
        int[] colClues = nonogram.columnClues[columnIndex];
        String col = getColumn(columnIndex);

        return checkStringToClues(col, colClues);
    }

    private int checkStringToClues(String string, int [] cluesArray){
        int [] stringGrouping = stringToOnes(string);

        if (stringGrouping.length != cluesArray.length){
            if (stringGrouping.length == 0 && cluesArray.length == 1 && cluesArray[0] ==  0){
                return 1;
            }
            return 0;
        }

        for (int i=0; i<stringGrouping.length; i++){
            if (stringGrouping[i] != cluesArray[i]){
                return 0;
            }
        }

        return 1;
    }

    public static int[] removeZeroes(int [] toClean){
        int numberOfZeroes = toClean.length;

        for (int i=0; i<toClean.length; i++){
            if (toClean[i] != 0){
                numberOfZeroes--;
            }
            else{
                break;
            }
        }

        int [] toRet = new int [toClean.length - numberOfZeroes];

        for (int i=0; i<toRet.length; i++){
            toRet[i] = toClean[i];
        }

        return toRet;
    }


    public static int[] stringToOnes(String string){
        int [] ones = new int [(string.length()/2)+1];
        int sum = 0;
        int index = 0;

        for (int i=0; i<string.length(); i++){
            if (string.charAt(i) == '1'){
                sum++;
            }
            else{
                if (sum != 0){
                    ones[index] = sum;
                    sum = 0;
                    index++;
                }
            }
        }
        if (sum != 0){
            ones[index] = sum;
        }

        return removeZeroes(ones);
    }

    /**------------------------NEW-----------------------**/

    /**-------------UTILITY---------------**/
    public int binomi(int n, int k) {
        if ((n == k) || (k == 0))
            return 1;
        else
            return binomi(n - 1, k) + binomi(n - 1, k - 1);
    }

    public int[] subArray(int[] array, int beg) {
        return Arrays.copyOfRange(array, beg, array.length);
    }

    /**---------END UTILITY---------------**/

    public void calcSolutions(){
        calcRowsSolutions();
        calcColsSolutions();
    }

    public void calcRowsSolutions(){
        for (int i=0; i< nonogram.rows; i++){
            calcSolutions(i, 'r');
        }
    }

    public void calcColsSolutions(){
        for (int i=0; i< nonogram.columns; i++){
            calcSolutions(i, 'c');
        }
    }

    public void calcSolutions(int index, char flag){
        int [] clues;
        int numOfCells;
        if (flag == 'r'){
            clues = nonogram.rowsClues[index];
            numOfCells = nonogram.columns;
        }
        else{
            clues = nonogram.columnClues[index];
            numOfCells = nonogram.rows;
        }
        int sumOfClues = Arrays.stream(clues).sum();
        int countOfClues = clues.length;

        int numOfSolutions = binomi(numOfCells-sumOfClues+1, countOfClues); // how many possible solutions for row/col

        generateSolutions(clues, numOfCells, new StringBuilder(), index, flag);

    }

    public void generateSolutions(int[] clues, int numOfCells, StringBuilder solution, int index, char flag){
        if (numOfCells < 0 || clues.length>0 && numOfCells==0){
            return;
        }
        else if (clues.length==0 && numOfCells>0){
            generateSolutions(clues, numOfCells-1, new StringBuilder(solution+"0"), index, flag);
        }
        else if (clues.length==0 & numOfCells==0){
            addSolution(solution, index, flag);
        }
        else{                                                           // there are clues and there are cells to fill
            generateSolutions(clues, numOfCells-1, new StringBuilder(solution+"0"), index, flag); // padding zeroes
            //generateSolutions(clues, numOfCells--, solution.append('0'), index, flag); // padding zeroes
            for (int i = 0; i < clues[0]; i++) {                             // filling the clue in the cells
                solution.append('1');
                numOfCells--;
            }
            //generateSolutions(subArray(clues, 1), numOfCells-1, new StringBuilder(solution+"0"), index, flag); // padding zeroes
            //generateSolutions(subArray(clues, 1), numOfCells-1, solution, index, flag); // padding zero after clue

            if (clues.length==1 && numOfCells==0){
                addSolution(solution, index, flag);
            }
            else {
                clues = subArray(clues,1);
                solution.append('0');
                numOfCells--;
                generateSolutions(clues, numOfCells, solution, index, flag);
                //generateSolutions(subArray(clues, 1), numOfCells -1, solution.append('0'), index, flag); // padding zero after clue
            }
        }
    }

    private void addSolution(StringBuilder solution, int index, char flag){
        if (flag == 'r'){
            rowSolutions[index].add(String.valueOf(solution));
        }
        else{
            colSolutions[index].add(String.valueOf(solution));
        }
    }


    /**-------------------- END NEW ---------------------**/

    public String getRow(int index){
        String row = "";

        for (int i=0; i<nonogram.columns; i++){
            row = row + chromosome.charAt(i+index*nonogram.columns);
        }

        return row;
    }

    public String getColumn(int index){
        String column = "";

        for (int i=index; i<nonogram.rows * nonogram.columns; i += nonogram.columns){
            column = column + chromosome.charAt(i);
        }

        return column;
    }


    public String toString(){
        String toRet = "-----------------------------------------------------------------------\n";
        toRet += "           ";

        int longestColumnClue = getLongestColumnClue();
        for (int i=0; i< longestColumnClue; i++){
            for (int j=0; j<nonogram.columnClues.length; j++){
                if (i < nonogram.columnClues[j].length){
                    toRet += " " + String.valueOf(nonogram.columnClues[j][i]) + " ";
                }
                else{
                    toRet += "   ";
                }
            }
            toRet += "\n           ";
        }
        for (int j=0; j < nonogram.columns; j++){
            toRet = toRet + " _ ";
        }
        toRet = toRet + "\n";
        for (int i=0, row = 0; i < nonogram.rows * nonogram.columns; i += nonogram.columns, row++){
            int space = 5;
            for (int j=0; j< nonogram.rowsClues[row].length; j++){
                space--;
                toRet += String.valueOf(nonogram.rowsClues[row][j]) + " ";
            }
            for (int j=0; j< space; j++){
                toRet+= "  ";
            }
            toRet += "|";
            for (int j=0; j < nonogram.columns; j++){
                toRet = toRet + (chromosome.charAt(i+j) == '1' ? " # " : " ` ");
            }
            toRet = toRet + "\n";
        }
        return toRet;
    }

    public int getLongestColumnClue(){
        int max = 0;

        for (int i=0; i < nonogram.columns; i++){
            if (nonogram.columnClues[i].length > max){
                max = nonogram.columnClues[i].length;
            }
        }

        return max;
    }
}
