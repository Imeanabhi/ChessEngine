package Pieces;

import PieceAttributes.*;

public class Queen extends Piece{
    public Queen(PieceColor color,Position position)
    {
        super(color,position);
    }
@Override
public boolean isValidMove(Position newPosition , Piece[][] board)
{
    if (newPosition.equals(position)) {
        return false;
    }
    int rowDiff = Math.abs(newPosition.getRow() - position.getRow());
    int colDiff = Math.abs(newPosition.getColumn() - position.getColumn());

boolean straightLine = (position.getRow() == newPosition.getRow() || position.getColumn() == newPosition.getColumn());
boolean diagonal = (rowDiff == colDiff);
if(straightLine!=true && diagonal!=true)
{
    return false;
}
/*  The given below code cannot be used because it doesnt take care of cases like queen moving
in the same direction horizontally or vertically i.e rowDirection = 0; case or the colDirection = 0;
case

int rowDirection = newPosition.getRow()>position.getRow() ? 1: -1;
int colDirection = newPosition.getColumn()>position.getCol()?1:-1;
 */

//So we write it as 
int rowDirection = Integer.compare(newPosition.getRow(), position.getRow());
int colDirection = Integer.compare(newPosition.getColumn(), position.getColumn());
int currentRow = position.getRow() + rowDirection;
int currentCol = position.getColumn() + colDirection;
while(currentRow!=newPosition.getRow() || currentCol!=newPosition.getColumn())
{
    if(board[currentRow][currentCol]!=null)
    {
        return false;
    }
    currentRow+=rowDirection;
    currentCol+=colDirection;
}
Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];
if(destinationPiece==null)
{
    return true;
}
else if (destinationPiece.getColor() != this.getColor())
{
    return true;
}
return false;
}
}