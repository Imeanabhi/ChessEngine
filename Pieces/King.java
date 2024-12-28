
package Pieces;
import PieceAttributes.*;
public class King extends Piece{
    public King(PieceColor color,Position position)
    {
        super(color,position);
    }
@Override
    public boolean isValidMove(Position newPosition , Piece[][] board)
    {
        int rowDiff = Math.abs(position.getRow() - newPosition.getRow());
        int colDiff = Math.abs(position.getColumn() - newPosition.getColumn());
        boolean isOneSquareMove = rowDiff <= 1 && colDiff <= 1 && (rowDiff !=0 || colDiff != 0);
        if(isOneSquareMove!=true)
        {
            return false;
        }
        Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];
        if(destinationPiece == null)
        {
            return true;
        }
        else if(destinationPiece.getColor() != this.getColor())
        {
            return true;
        }
        return false;
       
}
}