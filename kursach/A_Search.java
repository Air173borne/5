package kursach;

import java.util.*;

/**
 * Реализует алгоритм поиска решения А*.
 * Вес (F) каждой вершины вычисляется как сумма расстояния от начальной вершины до текущей (G)
 * и эвристическое предположение о расстоянии от текущей вершины, до терминальной (H).
 * Fi = Gi + Hi, где i - текущая вершина массива.
 */

public class A_Search<TSolution extends Solution, TInterface extends Interface<TSolution>> {
	private TInterface Interface;
	private int closedSolutions = 0;
    /**
     * Применяет алгоритм А* для поиска крадчайшего пути до терминального
     * состоянияот указанного.
     * LinkedList — 				реализует интерфейс List.
     * @param startSolution - 			начальное состояние.
     * @param open 					список открытых вершин.
     * @param close 				список закрытых вершин.
     * @param G - 					текущяя вершина.
     * @param H - 					терминальное состояние.
     * @return completeSolution		последовательность состояний от заданного до терминального.
     */

    public Collection<Solution> search(TSolution startSolution) {
        LinkedList<Integer> close = new LinkedList<Integer>();
        LinkedList<TSolution> open = new LinkedList<TSolution>();
        open.add(startSolution);
        startSolution.setG(0);
        startSolution.setH(Interface.getH(startSolution));

        while (!open.isEmpty()) {
            TSolution x = getSolutionWithMinF(open);
            if (Interface.isTerminate(x)) {
                closedSolutions = close.size();
                return completeSolution(x);
            }
            open.remove(x);
            close.add(x.hashCode());
            List<TSolution> neighbors = Interface.getNeighbors(x);
            for (TSolution neighbor : neighbors) {
                if (close.contains(neighbor.hashCode())) {
                    continue;
                }
                int g = x.getG() + Interface.getDistance(x, neighbor);
                boolean isGBetter;
                if (!open.contains(neighbor)) {
                    neighbor.setH(Interface.getH(neighbor));
                    open.add(neighbor);
                    isGBetter = true;
                } else {
                    isGBetter = g < neighbor.getG();
                }
                if (isGBetter) {
                    neighbor.setParent(x);
                    neighbor.setG(g);
                }
            }
        }
        return null;
    }

    public int getClosedSolutionsCount() {
        return closedSolutions;
    }

    /**
     * Создает объект для поиска терминального состояния по указанным правилам.
     *
     * @param Interface правила, в соответствии с которыми будет производиться поиск
     *              терминального состояния.
     */
    public A_Search(TInterface Interface) {
        if (Interface == null) {
            throw new IllegalArgumentException("Правила не существуют.");
        }
        this.Interface = Interface;
    }

    /**
     * Находит вершину в списке open с наименьшим значением веса.
     *
     * @param open 	список открытых вершин.
     * @param F - 	вес вершины
     * @param min 	присваиваем максимально возможную величину типа int
     * @return res 	вершину с наименьшим весом.
     */
    private TSolution getSolutionWithMinF(Collection<TSolution> open) {
        TSolution res = null;
        int min = Integer.MAX_VALUE;
        for (TSolution Solution : open) {
            if (Solution.getF() < min) {
                min = Solution.getF();
                res = Solution;
            }
        }
        return res;
    }

    /**
     * Составляет последовательность состояний пройденных от начального
     * состояния до конечного.
     *
     * @param terminate найденное конечное состояние.
     * @return path		последовательность состояний пройденных от начального состояния до конечного.
     */
    private Collection<Solution> completeSolution(TSolution terminate) {
        LinkedList<Solution> path = new LinkedList<Solution>();
        Solution c = terminate;
        while (c != null) {
            path.addFirst(c);
            c = c.getParent();
        }
        return path;
    }   
}
