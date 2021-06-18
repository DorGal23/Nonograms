public class Nonogram {
	
	public int rows;
	public int columns;
	public int [][] rowsClues;
	public int [][] columnClues;

	public Nonogram(int rows, int columns, int [][] rowsClues, int[][] columnClues){
		this.rows = rows;
		this.columns = columns;
		this.rowsClues = rowsClues;
		this.columnClues = columnClues;
	}
}