package edu.colostate.cs.cs414.chesshireCoders.jungleClient.game;

import java.util.ArrayList;
import java.util.List;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.GamePiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PieceType;

public class JungleGamePieceFactory {
	
	public static JungleGamePiece castPieceDown(GamePiece piece)
	{
		JungleGamePiece result = null;
		
		switch (piece.getPieceType()) {
		case CAT:
			result = new CatPiece(piece.getPlayerOwner(), piece.getColumn(), piece.getRow());
			break;
		case DOG:
			result = new DogPiece(piece.getPlayerOwner(), piece.getColumn(), piece.getRow());
			break;
		case ELEPHANT:
			result = new ElephantPiece(piece.getPlayerOwner(), piece.getColumn(), piece.getRow());
			break;
		case FOX:
			result = new FoxPiece(piece.getPlayerOwner(), piece.getColumn(), piece.getRow());
			break;
		case LEOPARD:
			result = new LeopardPiece(piece.getPlayerOwner(), piece.getColumn(), piece.getRow());
			break;
		case LION:
			result = new LionPiece(piece.getPlayerOwner(), piece.getColumn(), piece.getRow());
			break;
		case RAT:
			result = new RatPiece(piece.getPlayerOwner(), piece.getColumn(), piece.getRow());
			break;
		case TIGER:
			result = new TigerPiece(piece.getPlayerOwner(), piece.getColumn(), piece.getRow());
			break;
		default:
			break;
		}
		
		return result;
	}
	
	public static GamePiece castPieceUp(GamePiece piece)
	{
		GamePiece result = new GamePiece(piece.getPlayerOwner(), piece.getPieceType(), piece.getColumn(), piece.getRow())
				.setGameId(piece.getGameId())
				.setPieceId(piece.getPieceId());
		
		return result;
	}
	
	public static List<GamePiece> castPiecesUp(List<GamePiece> list)
	{
		List<GamePiece> results = new ArrayList<>();
		
		for (GamePiece piece : list) {
			results.add(castPieceUp(piece));
		}
		
		return results;
	}
	
	public static JungleGamePiece getPiece(PieceType pieceType)
	{
		JungleGamePiece result = null;
		
		switch (pieceType) {
			case CAT:
				result = new CatPiece(null, -1, -1);
				break;
			case DOG:
				result = new DogPiece(null, -1, -1);
				break;
			case ELEPHANT:
				result = new ElephantPiece(null, -1, -1);
				break;
			case FOX:
				result = new FoxPiece(null, -1, -1);
				break;
			case LEOPARD:
				result = new LeopardPiece(null, -1, -1);
				break;
			case LION:
				result = new LionPiece(null, -1, -1);
				break;
			case RAT:
				result = new RatPiece(null, -1, -1);
				break;
			case TIGER:
				result = new TigerPiece(null, -1, -1);
				break;
			default:
				break;
		}
		
		return result;
	}
}
