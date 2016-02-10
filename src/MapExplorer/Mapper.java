package MapExplorer;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Oliver Coulson on 10/02/2016.
 */
public class Mapper {
//    private static List<List<MapNode>> allPossible = new LinkedList<>();
//
//    public static List<MapNode> findShortestPath(MapNode current, MapNode destination) {
//
//        if (current.equals(destination)) {
//            allPossible.stream().min((listA, listB) -> {
//                if (listA.size() == listB.size()) {
//                    return 0;
//                } else {
//                    return (listA.size() > listB.size()) ? 1 : -1;
//                }
//            }).get();
//
//        }
//
//
//        Set<MapNode> neighbours = current.getNeighbours();
//
//        if (neighbours.size() == 1) {
//            return findShortestPath(neighbours.stream().findFirst().get(), destination));
//        } else if (neighbours.size() == 2) {
//            MapNode first = neighbours.stream().min((mapNodeA, mapNodeB) -> mapNodeA.compareTo(mapNodeB)).get();
//            neighbours.remove(first);
//            MapNode second = neighbours.stream().min((mapNodeA, mapNodeB) -> mapNodeA.compareTo(mapNodeB)).get();
//            List<MapNode> firstList = findShortestPath(first, destination);
//            List<MapNode> secondList = findShortestPath(second, destination);
//
//            return (firstList.size() > secondList.size()) ? secondList : firstList;
//
//        } else if (neighbours.size() == 3) {
//            MapNode first = neighbours.stream().min((mapNodeA, mapNodeB) -> mapNodeA.compareTo(mapNodeB)).get();
//            neighbours.remove(first);
//            MapNode second = neighbours.stream().min((mapNodeA, mapNodeB) -> mapNodeA.compareTo(mapNodeB)).get();
//            neighbours.remove(second);
//            MapNode third = neighbours.stream().min((mapNodeA, mapNodeB) -> mapNodeA.compareTo(mapNodeB)).get();
//            List<MapNode> firstList = findShortestPath(first, destination);
//            List<MapNode> secondList = findShortestPath(second, destination);
//            List<MapNode> thirdList = findShortestPath(third, destination);
//
//            if (firstList)
//
//        } else if (neighbours.size() == 4) {
//            MapNode first = neighbours.stream().min((mapNodeA, mapNodeB) -> mapNodeA.compareTo(mapNodeB)).get();
//            neighbours.remove(first);
//            MapNode second = neighbours.stream().min((mapNodeA, mapNodeB) -> mapNodeA.compareTo(mapNodeB)).get();
//            neighbours.remove(second);
//            MapNode third = neighbours.stream().min((mapNodeA, mapNodeB) -> mapNodeA.compareTo(mapNodeB)).get();
//            neighbours.remove(third);
//            MapNode fourth = neighbours.stream().min((mapNodeA, mapNodeB) -> mapNodeA.compareTo(mapNodeB)).get();
//            allPossible.add(findShortestPath(first, destination);
//            allPossible.add(findShortestPath(second, destination));
//            allPossible.add(findShortestPath(third, destination));
//            allPossible.add(findShortestPath(fourth, destination));
//
//        }
//    }
}
