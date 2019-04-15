
/*
 * Name: LI Chaohui
 * Student ID: 55202995
 * Lab section: T01 
 * 
*/

public abstract class Block{
	Cell[] cells = new Cell[4];
	char shape;
	int status;//0, 1, 2, 3 (shape 'O' block only has status 0)
	public Block(Cell c1, Cell c2, Cell c3, Cell c4, char shape, int status) {
		cells[0] = c1;
		cells[1] = c2;
		cells[2] = c3;
		cells[3] = c4;
		this.shape = shape;
		this.status = status;
	}
/*
 *get and set methods
*/
	public Cell[] getCells() {
		return cells;
	}
	public char getShape() {
		return shape;
	}
	public int getStatus() {
		return status;
	}
	public BlockColor getColor() {
		return cells[0].getColor();
	}
	public void setStatus(int status) {
		if(shape == 'O')
			this.status = 0;
		else
			this.status = status;
	}
	/*
	 * move methods for Block
	 */
	public void moveleft() {
		for(int i = 0; i < 4; i++)
			cells[i].left();
	}
	public void moveright(){
		for(int i = 0; i < 4; i++) {
			cells[i].right();
		}
	}
	public void movedown(){
		for(int i = 0; i < 4; i++)
			cells[i].down();
	}
	//rotate method
	public void rotate() throws OutOfBoardException {
		switch(shape) {
		//"I' shape block
			case 'I':
				/*
				 * compute the average row and col numbers
				 * in order to find the center for rotation
				 */
				int row=0, col=0;
				for(int i=0; i<4; i++) {
					row += cells[i].getRow();
					col += cells[i].getCol();
				}
				row = Math.floorDiv(row, 4);
				col = Math.floorDiv(col, 4);
				if(status == 0) {
					for(int i=0; i<4; i++) {
						cells[i].setRow(row-1+i);
						cells[i].setCol(col+1);
					}
				}
				else if(status == 1) {
					for(int i=0; i<4; i++) {
						cells[i].setRow(row+1);
						cells[i].setCol(col-2+i);
					}
				}
				else if(status == 2) {
					for(int i=0; i<4; i++) {
						cells[i].setRow(row-2+i);
						cells[i].setCol(col);
					}
				}
				else if(status == 3) {
					for(int i=0; i<4; i++) {
						cells[i].setRow(row);
						cells[i].setCol(col-1+i);
					}
				}
				break;
			//'O' shape block doesn't rotate
			case 'O':
				break;
			//Other shapes: 3*3 matrix
			default:{
				int matrix[][] = new int[3][3];
				//initialize the matrix
				for(int i=0; i<3; i++)
					for(int j=0; j<3; j++)
						matrix[i][j] = 0;
				//find the center cell
				int r=0, c=0;
				for(int i=0; i<4; i++) {
					r += cells[i].getRow();
					c += cells[i].getCol();
				}
				if(shape=='S' || shape=='Z') {
					if(status == 0) {
						r = r/4 + 1;
						c /= 4;
					}
					else if(status == 1 || status == 2) {
						r /= 4;
						c /= 4;
					}
					else if(status == 3) {
						r /= 4;
						c = Math.floorDiv(c, 4) + 1;
					}
				}
				else {
					r = (int) (Math.round(r/4.0));
					c = (int) (Math.round(c/4.0));
				}
				//position the center into matrix[1][1]
				int d1 = r - 1;
				int d2 = c - 1;//offsets for row and col numbers
				for(int i=0; i<4; i++) {
					int a = cells[i].getRow();
					int b = cells[i].getCol();
					matrix[a-d1][b-d2] = 1;
				}
				//rotate the current block clockwise by 90бу
				int temp[][] = new int[3][3];
				for(int i = 0; i < 3; i++)
					for(int j = 0; j < 3; j++) {
						temp[i][j] = matrix[2-j][i];
					}
				//figure out the rotated cells
				int index = 0;
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++)
						if(temp[i][j] == 1) {
							cells[index].setRow(i + d1);
							cells[index++].setCol(j + d2);
						}
					}
			}
			break;
		}
		status++;
		if(status >= 4)
			status = 0;
		if(shape == 'O')
			status = 0;
	}
	/*
	 * generate various blocks randomly
	 * set the initial status as 0
	 */
	public static Block randomBlock() {
		switch((int)(Math.random()*7)) {
		case 0:
			return new IBlock();
		case 1:
			return new JBlock();
		case 2:
			return new LBlock();
		case 3:
			return new OBlock();
		case 4:
			return new SBlock();
		case 5:
			return new TBlock();
		case 6:
			return new ZBlock();
		}
		return null;
	}
	/*
	 * rewrite toString() in Block
	 */
	public String toString() {
		String str="[";
		for(int i=0; i<3; i++) {
			str += cells[i].toString() +"; ";
		}
		str += cells[3].toString()+": "+this.shape+", "+this.status+"]";
		return str;
	}
}

/*
 * Subclasses for all shapes of blocks
 */
class IBlock extends Block {
	public IBlock(){
		super(new Cell(0,3,BlockColor.I_COLOR),
		      new Cell(0,4,BlockColor.I_COLOR),
		      new Cell(0,5,BlockColor.I_COLOR),
		      new Cell(0,6,BlockColor.I_COLOR), 'I', 0);
		}
}

class JBlock extends Block {
	public JBlock(){
		super(new Cell(0,3,BlockColor.J_COLOR),
		      new Cell(0,4,BlockColor.J_COLOR),
		      new Cell(0,5,BlockColor.J_COLOR),
		      new Cell(-1,3,BlockColor.J_COLOR), 'J', 0);
		}

}

class LBlock extends Block {
	public LBlock(){
		super(new Cell(0,3,BlockColor.L_COLOR),
		      new Cell(0,4,BlockColor.L_COLOR),
		      new Cell(0,5,BlockColor.L_COLOR),
		      new Cell(-1,5,BlockColor.L_COLOR), 'L', 0);
		}

}

class OBlock extends Block {
	public OBlock(){
		super(new Cell(0,4,BlockColor.O_COLOR),
		      new Cell(-1,4,BlockColor.O_COLOR),
		      new Cell(-1,5,BlockColor.O_COLOR),
		      new Cell(0,5,BlockColor.O_COLOR), 'O', 0);
		}

}

class SBlock extends Block {
	public SBlock(){
		super(new Cell(0,3,BlockColor.S_COLOR),
		      new Cell(0,4,BlockColor.S_COLOR),
		      new Cell(-1,4,BlockColor.S_COLOR),
		      new Cell(-1,5,BlockColor.S_COLOR), 'S', 0);
		}
}

class TBlock extends Block {
	public TBlock(){
		super(new Cell(0,3,BlockColor.T_COLOR),
		      new Cell(0,4,BlockColor.T_COLOR),
		      new Cell(0,5,BlockColor.T_COLOR),
		      new Cell(-1,4,BlockColor.T_COLOR), 'T', 0);
		}

}

class ZBlock extends Block {
	public ZBlock(){
		super(new Cell(-1,3,BlockColor.Z_COLOR),
		      new Cell(0,4,BlockColor.Z_COLOR),
		      new Cell(-1,4,BlockColor.Z_COLOR),
		      new Cell(0,5,BlockColor.Z_COLOR), 'Z', 0);
		}

}





