package clids.ex5.search;


import java.util.Date;
import java.util.Iterator;





/**
 * This class implements a DFS algorithm. More specifically, the performSearch()
 * method that it supplies implements a time-limited and depth-limited DFS, and gets as input parameters
 * maxDepth and timeout values. Your implementation should work with ANY object of any class that correctly
 * implements the SearchBoard & BoardMove interfaces.
 * @author jjgold, mishelle
 *
 * @param <B>
 * @param <M>
 */
public class MyDepthFirstSearch<B extends SearchBoard<M>, M extends BoardMove> 
implements DepthFirstSearch<B, M> {

	/**
	 * Note: This method assumes that the graph is a tree, so it doesn't have to
	 * check for loops.
	 * 
	 * Performs Depth-First search, up to depth maxDepth, and using at most
	 * timeOut ms, starting from the given SearchBoardEntry.
	 * 
	 * @param startBoard
	 *            - the starting board of the search.
	 * @param maxDepth
	 *            - Maximal depth of your search. maxDepth 0 means that you
	 *            check only the given board; maxDepth 1 means that you check
	 *            only startBoard and its children, etc.
	 * @param timeOut
	 *            - time in milliseconds as estimated by java Date object
	 * 
	 * @return SearchBoardEntry with highest non-negative quality() found within
	 *        the given time (timeOut) and depth (maxDepth).
	 *        If all of the observed quality values are negative, returns null.
	 */
	public B performSearch(B startBoard, int maxDepth, long timeOut){
		long time = new Date().getTime() + timeOut;

		// calls to recursiveSearch
		B bestBoard = recursiveSearch((B)startBoard, maxDepth, time);
	
		return bestBoard;
	}



	@SuppressWarnings("unchecked")
	private B recursiveSearch(B startBoard, int maxDepth, long timeOut){

		// checks whether the time has passed, the depth is 0 or this is the best solution
		if (timeOut<= new Date().getTime() || maxDepth == 0 || startBoard.isBestSolution()){

			return startBoard;
		}
		// the bestBoard for certain level in the tree 
		B bestBoard = (B) startBoard.getCopy();
		Iterator<M> iterator = startBoard.getMovesIterator();

		while(iterator.hasNext()){
			M move = iterator.next();
			// performs move in the board
			startBoard.doMove(move);
			// checks if the move is necessary
			if (!(startBoard.getQualityBound() < bestBoard.getQuality())){
				B temp = recursiveSearch(startBoard, --maxDepth, timeOut);
				if (temp.isBestSolution()){
					return temp;
				}
				// update the best board
				if (temp.getQuality() > bestBoard.getQuality()){
					bestBoard = temp;
				}

			}
			// undo the last move
			startBoard.undoMove(move);

		}



		return bestBoard;

	}


}
