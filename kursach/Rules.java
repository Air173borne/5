package kursach;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Определяет специфичные для задачи правила ее решения. В данной реализации
 * эвристика вычисляется как количество клеток, находящихся не на своих местах.
 */


public class Rules implements Interface<State> {
	
	protected int sideSize;
	protected int size;
	protected int[] actions;
	/**
	 * Создание коллекции из массива
	 */
	public List<State> getNeighbors(State currentState) {
		ArrayList<State> res = new ArrayList<State>();
		for (int i = 0; i < actions.length; i++) {
			byte[] field = doAction(currentState.getField(), actions[i]);
			if (field == null) {
				continue;
			}
			State Solution = new State(currentState, sideSize);
			Solution.setField(field);
			res.add(Solution);
		}
		return res;
	}

	/**
	 * Подсчитывает расстояние сотояний от a до b.
	 * 
	 * @param a 		первое состояние. Должно быть среди состояний, предшествующих b.
	 * @param b 		второе состояние.
	 * @return res		количество переходов от a до b.
	 */
	public int getDistance(State a, State b) {
		Solution c = b;
		int res = 0;
		while ((c != null) && (!c.equals(a))) {
			c = c.getParent();
			res++;
		}
		return res;
		/*
		 * На самом деле, в силу специфики реализации А*, данному методу
		 * достаточно всегда возвращать 1.
		 */
	}

	/**
	 * Эвристика вычисляется как количество клеток, находящихся не на своих местах (зная терминальное состояние).
	 */
	protected byte[] terminateState;
	public int getH(State Solution) {
		int res = 0;
		for (int i = 0; i < size; i++) {
			if (Solution.getField()[i] != terminateState[i]) {
				res++;
			}
		}
		return res;
	}

	public boolean isTerminate(State Solution) {
		return Arrays.equals(Solution.getField(), terminateState);
	}

	public byte[] getTerminateState() {
		return terminateState;
	}

	

	/**
	 * Применяет к состоянию правило.
	 * 
	 * @param field		 	начальное состояние.
	 * @param action	 	применяемое правило.
	 * @return 	null 		если состояние недопуступно. новое состояние, полученное в результате применения правила. 
	 */
	public byte[] doAction(byte[] field, int action) {
		/* Выполняется поиск пустой клетки, пустая клетка обозначется цифрой "0" */
		int zero = 0;
		for (; zero < field.length; zero++) {
			if (field[zero] == 0) {
				break;
			}
			if (zero >= field.length) {
				return null;
			}
		}
		
		/* Вычисляется индекс перемещаемой клетки */
		int number = zero + action;
		/* Проверяется допустимость хода */
		if (number < 0 || number >= field.length) {
			return null;
		}
		if ((action == 1) && ((zero + 1) % sideSize == 0)) {
			return null;
		}
		if ((action == -1) && ((zero + 1) % sideSize == 1)) {
			return null;
		}
		/*
		 * Создается новый массив, на котором меняются местами пустая и
		 * перемещаемая клетки
		 */
		byte[] newField = Arrays.copyOf(field, field.length);
		byte temp = newField[zero];
		newField[zero] = newField[number];
		newField[number] = temp;

		return newField;
	}

	/**
	 * Возможность хода в разные стороны
	 */
	private int left = -1;
	private int top;
	private int right = 1;
	private int bottom;
	/**
	 * Кидаем ошибки если у нас выполняется какое-то условие исключения 
	 * @param fieldSize				 	размер поля (количество клеток на одной стороне).
	 * @param terminateState	 		конечное сотояние.
	 * @param IllegalArgumentException	выдаём ошибку
	 */
	public Rules(int fieldSize, byte[] terminateState) {
		if (fieldSize < 2) {
			throw new IllegalArgumentException("Неверно задан размер.");
		}
		if (terminateState == null) {
			throw new IllegalArgumentException("Терминальное состояние не может быть null.");
		}

		this.sideSize = fieldSize;
		size = sideSize * sideSize;

		if (terminateState.length != size) {
			throw new IllegalArgumentException("Размер терминального состояния некоректный.");
		}
		this.terminateState = terminateState;
			top = -sideSize;
			bottom = sideSize;
			actions = new int[] { top, bottom, left, right };
	}
	
	/**
	 * Возвращает массив доступных действий.
	 */
	public int[] getActions() {
		return actions;
	}
}
