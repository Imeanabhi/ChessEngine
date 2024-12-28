package Pieces;
import PieceAttributes.*;


public class Rook extends Piece {
    public Rook(PieceColor color, Position position)
    {
        super(color,position);
    }
    @Override
    public boolean isValidMove(Position newPosition,Piece[][] board)
    {
        if(position.getRow()==newPosition.getRow())
        {
            int columnStart = Math.min(position.getColumn(),newPosition.getColumn())+1;
            int columnEnd = Math.max(position.getColumn(),newPosition.getColumn());
            for(int col = columnStart;col<columnEnd;col++)
            {
                if(board[position.getRow()][col]!=null)
                {
                    return false;
                }
            }
        }
        else if(position.getColumn() == newPosition.getColumn())
        {
int rowStart = Math.min(position.getRow(),newPosition.getRow())+1;
int rowEnd = Math.max(position.getRow(),newPosition.getRow());
    for(int row = rowStart;row<rowEnd;row++)
    {
            if(board[row][position.getColumn()]!=null)
        {
            return false;
        }
    }
        }
else{
    return false;
}
//We have checked all the places in between now lets check for the destination place
Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];
if(destinationPiece==null)
{
    return true;
}
else if(destinationPiece.getColor()!=this.getColor())
{
    return true;
}
return false;
    }
}