package Pieces;

import PieceAttributes.*;

public class Pawn extends Piece {
    public Pawn(PieceColor color, Position position) {
        super(color,position);
    }
@Override
public boolean isValidMove(Position newPosition,Piece[][] board)
{
    //White Pawns Move -1 and Black Pawns move +1
    int forwardDirection = (color==PieceColor.WHITE)?-1:1;
    int rowDiff = (newPosition.getRow()-position.getRow())*forwardDirection;
    int colDiff=(newPosition.getColumn()-position.getColumn());
    if(rowDiff==1 && colDiff==0 && board[newPosition.getRow()][newPosition.getColumn()]!=null)
    {
        return true;
    }
    boolean isStartingPostion = (color==PieceColor.WHITE && position.getRow()==6 || (color==PieceColor.BLACK 
    && position.getRow()==1));
    if(colDiff==0 && rowDiff==2 && isStartingPostion && board[newPosition.getRow()][newPosition.getColumn()]==null)
    {
        int middleRow = position.getRow()+forwardDirection;
        if(board[middleRow][position.getColumn()]==null)
        {
            return true;
        }
    }
    if(Math.abs(colDiff)==1 && rowDiff==1 && board[newPosition.getRow()][newPosition.getColumn()]!=null 
    && board[newPosition.getRow()][newPosition.getColumn()].color!=this.color)
    {
        return true;
    }
    return false;
}

}