/*
 * Name: LI Chaohui
 * Student ID: 55202995
 * Lab section: T01 
 * 
*/
public class Cell {
	int row;
	int col;
	BlockColor color;
	public Cell(int row, int col, BlockColor color) {
		this.row = row;
		this.col = col;
		this.color = color;
	}
	/*
	 * get and set methods
	 */
	public int getRow() {
		return this.row;
	}
	public int getCol() {
		return this.col;
	}
	public BlockColor getColor() {
		return this.color;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public void setColor(BlockColor color) {
		this.color = color;
	}
	/*
	 * methods for movement
	 */
	public void left() {
		col--;
	}
	public void right() {
		col++;
	}
	public void down() {
		row++;
	}
	//rewrite toString() in Cell
	public String toString() {
		return "(" + row + ", " + col + ", " + color + ")";
	}	
}
