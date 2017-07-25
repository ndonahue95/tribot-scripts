package scripts.webwalker_logic.local.walker_engine.bfs;


import org.tribot.api2007.types.RSTile;
import scripts.webwalker_logic.local.walker_engine.WaitFor;
import scripts.webwalker_logic.shared.NodeInfo;
import scripts.webwalker_logic.shared.PathFindingNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {

    private static final int DEFAULT_OFFSET = 12;
    public static int OFFSET_SEARCH = DEFAULT_OFFSET;

    public static PathFindingNode bfsClosestToPath(ArrayList<RSTile> path, PathFindingNode start){
        return bfsClosestToPath(path, start, -1);
    }

    public static PathFindingNode bfsClosestToPath(ArrayList<RSTile> path, PathFindingNode start, int limit){
        if (path == null || start == null){
            return null;
        }
        if (path.contains(start.getRSTile())) {
            return start;
        }
        NodeInfo.clearMemory(start.getClass());

        int iteration = 0;
        Queue<PathFindingNode> queue = new LinkedList<>();
        queue.add(start);
        NodeInfo.create(queue.peek()).traversed = true;

        while (!queue.isEmpty()){
            if (iteration != -1 && iteration++ == limit){
                break;
            }
            PathFindingNode current = queue.remove();
            for (PathFindingNode neighbor : current.getNeighbors()){
                NodeInfo.Details nodeInfo = NodeInfo.create(neighbor);
                if (nodeInfo.traversed){
                    continue;
                }
                nodeInfo.traversed = true;
                if (path.contains(neighbor.getRSTile())){
                    return neighbor;
                }
                queue.add(neighbor);
            }
        }
        return null;
    }

    /**
     *
     * @param start
     * @param end
     * @param limit limit tile search distance.
     * @return
     */
    public static boolean isReachable(PathFindingNode start, PathFindingNode end, int limit){
        if (start == null || end == null){
            return false;
        }
        if (start.equals(end)) {
            return true;
        }
        NodeInfo.clearMemory(start.getClass());

        int iteration = 0;
        Queue<PathFindingNode> queue = new LinkedList<>();
        queue.add(start);
        NodeInfo.create(queue.peek()).traversed = true;

        while (!queue.isEmpty()){
            if (iteration != -1 && iteration++ == limit){
                return false;
            }
            PathFindingNode current = queue.remove();
            for (PathFindingNode neighbor : current.getNeighbors()){
                NodeInfo.Details nodeInfo = NodeInfo.create(neighbor);
                if (nodeInfo.traversed){
                    continue;
                }

                nodeInfo.traversed = true;

                if (neighbor.equals(end)){
                    return true;
                }


                queue.add(neighbor);
            }
        }
        return false;
    }

    /**
     * Basic BFS search
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean isReachable(PathFindingNode start, PathFindingNode end){
        return isReachable(start, end, -1);
    }


    public static PathFindingNode getRandomTileNearby(PathFindingNode start){
        NodeInfo.clearMemory(start.getClass());

        int limit = WaitFor.random(1, OFFSET_SEARCH), currentLimit = 0;
        Queue<PathFindingNode> queue = new LinkedList<>();
        queue.add(start);
        NodeInfo.create(queue.peek()).traversed = true;

        while (!queue.isEmpty()){

            PathFindingNode current = queue.remove();

            if (++currentLimit > limit){
                return current;
            }

            if (start.distance(current) > limit){
                return current;
            }
            for (PathFindingNode neighbor : current.getNeighbors()){
                NodeInfo.Details nodeInfo = NodeInfo.create(neighbor);
                if (nodeInfo.traversed){
                    continue;
                }

                nodeInfo.traversed = true;

                queue.add(neighbor);
            }
        }
        return null;
    }


}
