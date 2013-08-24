package de.bitowl.ld27.astar;

/**
* A heuristic that uses the tile that is closest to the target
* as the next best tile.
* http://code.google.com/p/a-star-java/source/browse/AStar/src/aStar/heuristics/ClosestHeuristic.java?r=7
*/
public class ClosestHeuristic implements AStarHeuristic {

       public float getEstimatedDistanceToGoal(int startX, int startY, int goalX, int goalY) {        
               float dx = goalX - startX;
               float dy = goalY - startY;
              
               //float result = (float) (Math.sqrt((dx*dx)+(dy*dy)));
              
               //Optimization! Changed to distance^2 distance: (but looks more "ugly")
              
               float result = (float) (dx*dx)+(dy*dy);
              
              
               return result;
       }

}

