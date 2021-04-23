package AIs;

import java.util.LinkedList;

import java.util.List;
import GameBoard.Board;
import GameBoard.Position;
import GameBoard.Robot;
import GameBoard.Cards.ProgramCard;
import GameBoard.Components.Flag;
import GameBoard.Components.SimpleComponent;

public class Ultron implements AI {

	private Robot robot;
	private Board board;
	private  boolean moveY = false;
	private static LinkedList<GameBoard.Position> flags;
	
	@Override
	public void chooseCards(Robot bot, Board board) {
		this.robot = bot;this.board = board;
		GameBoard.Position playerPosition = this.trackPlayerPosition(); 
		flags = this.allocateFlags();;
		if(playerPosition != null)
			selectCards(robot.getAvailableCards(), playerPosition);
	}	
	
	/**
	 * Locates all flags in the board and return a list of their position
	 * @return
	 */
	private LinkedList<Position> allocateFlags() {
		LinkedList<Position> flags = new LinkedList<>();
		for(int i = 0; i < board.getHeight(); i++) {
			for(int q = 0; q < board.getHeight(); q++) {
				 SimpleComponent comp = (SimpleComponent) board.getForgridAt(q, i);
				 if(comp != null && comp instanceof Flag) {
					 flags.add(new GameBoard.Position(q,i));
				 }
			}
		}
			return flags;
	}
	
	private GameBoard.Position trackPlayerPosition() {
		for(int i = 0; i < board.getHeight(); i++) {
			for(int q = 0; q < board.getHeight(); q++) {
				if(board.getRobotAt(i,q) != null) {
				if(board.getRobotAt(i,q).equals(robot)) {
						return new GameBoard.Position(i,q);
					}
				}
			}
		}
		return null;
	}
	
	private LinkedList<Integer> getDirectionToFlag(int x1, int y1, int x2, int y2) {
		java.util.LinkedList<Integer> dir = new java.util.LinkedList<>();
				if (x2-x1 < 0) {
					dir.add(-1);
					}
				if (x2-x1 > 0){
					dir.add(1);
				}
				if (y2-y1 < 0){
					dir.add(0);
				}
				if (y2-y1 > 0){
					dir.add(2);
				}
				return dir;
	}
	/**
	 * Calculates and returns position of the nearest flag to the robot 
	 * @param x
	 * @param y
	 * @param pos
	 * @return
	 */
	private Position getNearestFlag(int x, int y, java.util.LinkedList<GameBoard.Position> pos) {
		int xPos = 0; int yPos = 0;int sum4 = 5000;
		for(int i = 0; i < pos.size(); i++) {
			int distance = (int) Math.sqrt( Math.pow(pos.get(i).getX() - x, 2) +Math.pow(pos.get(i).getY() - y, 2) );
			if(distance < sum4) {
				sum4 = distance;
				xPos = pos.get(i).getX(); yPos = pos.get(i).getY();
			}
		}
		GameBoard.Position po = new GameBoard.Position(xPos, yPos);
		return po;
		
	}
	
	
	private void selectCards(java.util.List<ProgramCard> cards, GameBoard.Position playerPosition) {

		java.util.List<ProgramCard> allCards = new java.util.ArrayList<>();
		int xDistance = getNearestFlag(playerPosition.getX(), playerPosition.getY(), flags).getX();
		int yDistance = getNearestFlag(playerPosition.getX(), playerPosition.getY(), flags).getY();
		LinkedList<Integer> dir = getDirectionToFlag(playerPosition.getX(), playerPosition.getY(), xDistance, yDistance);
		int diff = 0;
		
		
		if(playerPosition.getX() == xDistance) {
			moveY = true;
		}
		
		if(playerPosition.getX() == xDistance && playerPosition.getY() == yDistance) {
			flags.remove(this.getFlag(xDistance, yDistance, flags));
			xDistance = getNearestFlag(playerPosition.getX(), playerPosition.getY(), flags).getX();
			yDistance = getNearestFlag(playerPosition.getX(), playerPosition.getY(), flags).getY();
			dir = getDirectionToFlag(playerPosition.getX(), playerPosition.getY(), xDistance, yDistance);
			
			System.out.println("Robot is on flag! DIRECTION TO GO: "+dir);
			moveY = false;
		}
		
		ProgramCard dirCard = filterCard(cards, dir.getFirst());
		if(dirCard != null) {
			allCards.add(dirCard);
		}
		
		try {
			if(!moveY) {
				diff = Math.abs(xDistance - playerPosition.getX());
			}
			else {
				diff = Math.abs((yDistance)-playerPosition.getY()); 
				moveY = false;
			}
			java.util.List<ProgramCard> STEPS = filterCard(cards, playerPosition, diff);
		
			for(ProgramCard card: STEPS) {
				allCards.add(card);
			}
			for(int i = 0; i < robot.getChosenCardSlots(); i++) {
				if(i < allCards.size()) {
					robot.chooseCard(allCards.get(i));
					}
				}
			
		}catch(Exception e) {
				System.out.println(e);
		}	
		
				
	}
	
	/**
	 * filters cards given and picks a correct direction to a flag
	 * @param cards
	 * @param direc
	 * @return
	 */
	private ProgramCard filterCard(List<ProgramCard> cards, int direc) {

		ProgramCard mainCard = null;
		for(ProgramCard card: cards) {
		if(card.getRotation() == direc && robot.getDirection() != direc) {
			if(direc >= 0) {
				robot.setDirection(direc);
				}
			if( direc < 0) {
			mainCard = card;
				}
			}
		}
		return mainCard;
	}
	
	/**
	 * Filters the cards given, and return list of proper cards which potentially can lead to visiting a flag
	 * @param card
	 * @param playerPosition
	 * @param diff
	 * @return
	 */
	private List<ProgramCard> filterCard(java.util.List<ProgramCard> card, GameBoard.Position playerPosition, int diff) {
		int steps = 0; 
		
		java.util.List<ProgramCard> stepCards = new java.util.ArrayList<>();
		for(ProgramCard cards: card) {
			if(cards.getRotation() == 0 && cards.getDistance() > 0) {
				if(steps != diff ) {
					if(steps + cards.getDistance() > diff) {
						continue;
					}
					else {
						steps += cards.getDistance();
						stepCards.add(cards);
					}
				}
			}
			
			if(steps > diff) {
				continue;
			}
		}
		return stepCards;
	}
	
	private Position getFlag(int x, int y, LinkedList<Position> flags) {
		int i = 0;
		for(; i < flags.size(); i++) {
			if(flags.get(i).getX() == x && flags.get(i).getY() == y) {
				return flags.get(i);
			}
		}
		return null;
	}

}
