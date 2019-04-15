/*
 * Name: LI Chaohui
 * Student ID: 55202995
 * Lab section: T01 
 * 
*/
public class Board {
    static final int BOARD_HEIGHT=20, BOARD_WIDTH=10;
	private BlockColor[][] cells = new BlockColor[BOARD_HEIGHT][BOARD_WIDTH];
	public Block activeBlock;
	private int numFullLinesRemoved = 0;
	
	public Board() {
		for(int i=0; i<BOARD_HEIGHT;i++)
			for(int j=0; j<BOARD_WIDTH;j++)
				cells[i][j] = BlockColor.NO_COLOR;
	}
	
	
	public Block activeBlock() {
		return activeBlock;	
	}
	public BlockColor bloackAt(int x, int y) {
		return cells[x][y];
	}
	public void clear() {
		activeBlock = null;
		for(int i=0; i<BOARD_HEIGHT;i++)
			for(int j=0; j<BOARD_WIDTH;j++)
				cells[i][j] = BlockColor.NO_COLOR;
	}
	public void blockLanded() {
		Cell[] landedCells = activeBlock.getCells();
		int x,y;
		for(int i=0; i<4;i++) {
			x=landedCells[i].getRow();
			y=landedCells[i].getCol();
			//ignore the newly generated block
			if(x<0)
				continue;
			cells[x][y] = landedCells[i].getColor();
		}
		activeBlock = null;
	}
	/*
	 * According to the initialization
	 * If column 3-6 in row 0 is occupied,
	 * no block can be generated
	 */
	public boolean canMove() {
		for(int i=3;i<=6;i++) {
			if(cells[0][i]!=BlockColor.NO_COLOR)
				return false;
		}
		return true;
	}
	public boolean rotate() throws OutOfBoardException{
		Cell[] currentCells = activeBlock.getCells();
		//newly generated cannot rotate immediately
		if(currentCells[1].getRow()<1)
			throw new OutOfBoardException();
		activeBlock.rotate();
		Cell[] rotatedCells = activeBlock.getCells();
		int x,y;
		for(int i=0; i<4;i++) {
			x=rotatedCells[i].getRow();
			y=rotatedCells[i].getCol();
			//if rotated block is out of board, restore its original status
			if(x<0||y<0||x>=BOARD_HEIGHT||y>=BOARD_WIDTH) {
				for(int j=0; j<3;j++)
					activeBlock.rotate();
				throw new OutOfBoardException();
			}
			//if occupied by previous landed blocks
			if(cells[x][y] != BlockColor.NO_COLOR) {
				for(int j=0; j<3;j++)
					activeBlock.rotate();
				return false;
			}
		}
		return true;
	}
	
	public boolean oneLineDown() {
		Cell[] currentCells = activeBlock.getCells();
		int x,y;
		for(int i=0; i<4;i++) {
			x=currentCells[i].getRow()+1;
			y=currentCells[i].getCol();
			if(x>=BOARD_HEIGHT || cells[x][y] != BlockColor.NO_COLOR) {
				return false;
			}	
		}
		activeBlock.movedown();
		return true;
	}
	public boolean moveLeft() throws OutOfBoardException {
		Cell[] currentCells = activeBlock.getCells();
		int x,y;
		for(int i=0; i<4;i++) {
			x=currentCells[i].getRow();
			y=currentCells[i].getCol()-1;
			//newly created (at row 0) or leftmost block cannot move left
			if(y<0||x<0) {
				throw new OutOfBoardException();
			}
			if(cells[x][y] != BlockColor.NO_COLOR)
				return false;
		}
		activeBlock.moveleft();
		return true;
	}
	public boolean moveRight() throws OutOfBoardException {
		Cell[] currentCells = activeBlock.getCells();
		int x,y;
		for(int i=0; i<4;i++) {
			x=currentCells[i].getRow();
			y=currentCells[i].getCol()+1;
			//newly created (at row 0) or rightmost block cannot move right
			if(y>=BOARD_WIDTH||x<0) {
				throw new OutOfBoardException();
			}
			if(cells[x][y] != BlockColor.NO_COLOR)
				return false;
		}
		activeBlock.moveright();
		return true;
	}
	public boolean newBlock() {
		if(canMove()) {
			activeBlock = Block.randomBlock();
			return true;
		}
		else
			return false;
	}
	//Find the topmost occupied row
	public int removeFullLines() {
		int count = 0;
		boolean find = true;
		for(int i=0;i<BOARD_HEIGHT;i++) {
			find = true;
			//Check if the row is full from the top to the bottom
			for(int j=0;j<BOARD_WIDTH;j++) {
				if(cells[i][j]==BlockColor.NO_COLOR) {
					find = false;
					break;
				}
			}
			//Full row found
			if(find == true) {
				//move above rows one line down row by row
				for(int k=i;k>0;k--)
					for(int x=0;x<BOARD_WIDTH;x++)
						cells[k][x] = cells[k-1][x];
				//clear the top row 
				for(int k=0;k<BOARD_WIDTH;k++)
					cells[0][k] = BlockColor.NO_COLOR;
				i--;
				count++;
			}
		}
		numFullLinesRemoved += count;
		return count;
	}
	public int numFullLinesRemoved() {
		return numFullLinesRemoved;
	}	
	
}
