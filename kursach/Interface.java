package kursach;

import java.util.List;

/**
 * Задаются правила для нахождения терминального состояния
 * Создаём интерфейс, правил прохождения вершин, который потом используем в FifteenInterface
 */
public interface Interface<TSolution extends Solution> {

	/**
	 * Возвращает список состояний, в которые может быть осуществлен переход из
	 * указанного состояния.
	 * 
	 * @param currentSolution	 	текущее состояние, для которого раскрываются соседние.
	 * @return 					список состояний, в которые может быть осуществлен переход из указанного состояния.
	 */
	List<TSolution> getNeighbors(TSolution currentSolution);

	/**
	 * Возвращает растояние между указанными состояниями.
	 * 
	 * @param a 	первое состояние.
	 * @param b 	второе состояние.
	 * @return 		растояние между указанными состояниями.
	 */
	int getDistance(TSolution a, TSolution b);

	/**
	 * Вычисляет эвристическую оценку расстояния от указанного состояния до конечного.
	 * 
	 * @param Solution 	текущее состояние.
	 * @return 			значение оценки расстояния от указанного состояния до конечного.
	 */
	int getH(TSolution Solution);

	/**
	 * Проверяет состояние, не является ли оно конечным.
	 * 
	 * @param Solution	 состояние.
	 * @return true, если состояние конечное.
	 */
	boolean isTerminate(TSolution Solution);
}
