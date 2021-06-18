public class NonogramFactory{
	
	private final int size;
	private Nonogram [] nonograms;

	public NonogramFactory(){
		size = 1;
		nonograms = new Nonogram [3];
		int [][] columnClues1 = {
            { 1, 2 },
            { 2, 1, 2, 1 },
            { 4, 2, 1, 1 },
            { 3, 2, 1, 4, 1 },
            { 4, 2, 3 },
            { 4, 1 },
            { 3, 2 },
            { 8 }
        };

		int [][] rowsClues1 = {
            { 1, 1 },
            { 3 },
            { 4 },
            { 2, 3 },
            { 5, 1 },
            { 2, 2 },
            { 1, 1 },
            { 4, 1 },
            { 1, 1 },
            { 1, 1 },
            { 1, 1 },
            { 3, 1 },
            { 1, 2, 2 },
            { 3 },
            { 5 },
            { 1 }
        };

		int [][] columnClues2 = {
				{ 2 },
				{ 1, 1, 1 },
				{ 2 },
				{ 3 },
				{ 2, 1 }
		};

		int [][] rowsClues2 = {
				{ 4 },
				{ 3 },
				{ 1, 1 },
				{ 1 },
				{ 2, 1 }
		};

		int [][] columnClues3 = {
				{ 5 },
				{ 6 },
				{ 7 },
				{ 4, 1 },
				{ 3, 3 },
				{ 9 },
				{ 1, 7 },
				{ 1 },
				{ 1 },
				{ 2 }
		};

		int [][] rowsClues3 = {
				{ 1 },
				{ 4 },
				{ 3 },
				{ 5, 2 },
				{ 2, 2, 1 },
				{ 3, 2 },
				{ 3, 2 },
				{ 3, 4 },
				{ 7 },
				{ 3, 3 }
		};

		nonograms[0] = new Nonogram(16,8, rowsClues1,columnClues1);
		nonograms[1] = new Nonogram(5,5, rowsClues2,columnClues2);
		nonograms[2] = new Nonogram(10,10, rowsClues3,columnClues3);
	}

	public Nonogram getNonogram(){
        return nonograms[1];
	}
}