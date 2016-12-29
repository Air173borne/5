package kursach;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;


public class Game15 {
	/**
	 * sideSize задано дефолтное значение, определяет размерность входных массивов
	 */
	private static int sideSize = 2;
	private static byte[] startField;
	private static byte[] terminateField;
	private static boolean isReadFromStream = true;  
	
	public static void main(String[] args) {
		/**
		 * Выводим сообщение для каждого размера массива, если число N у нас нечетное
		 */
		
		if (isReadFromStream) {
			try {
				startField = readStartState();
			} catch (IOException e) {
				e.printStackTrace();
				/**
				 * Закомментировал выход программы если на стадии проверки "Математическое описания" 
				 * получаем что нельзя данную матрицу привести в терминальное состояние.
				 */
				/*System.exit(1);*/	
			}
			
			if (sideSize == 2 && !State.checkState(startField)) {
				System.out
						.println("\nДанное состояние нельзя привести к терминальному.\n"
								+ "* Математическое описание:\n"
								+ "\tЕсли сумма N является нечётной, то решения головоломки не существует.\n"
								+ "\tСм. http://ru.wikipedia.org/wiki/Игра_в_15\n");
				/*System.exit(1);*/	
			}
			if (sideSize == 3 && !State.checkState(startField)) {
				System.out
						.println("\nДанное состояние нельзя привести к терминальному.\n"
								+ "* Математическое описание:\n"
								+ "\tЕсли сумма N является нечётной, то решения головоломки не существует.\n"
								+ "\tСм. http://ru.wikipedia.org/wiki/Игра_в_15\n");
				/*System.exit(1);*/	
			}
			if (sideSize == 4 && !State.checkState(startField)) {
				System.out
						.println("\nДанное состояние нельзя привести к терминальному.\n"
								+ "* Математическое описание:\n"
								+ "\tЕсли сумма N является нечётной, то решения головоломки не существует.\n"
								+ "\tСм. http://ru.wikipedia.org/wiki/Игра_в_15\n");
				/*System.exit(1);*/	
			}
			if (sideSize == 5 && !State.checkState(startField)) {
				System.out
						.println("\nДанное состояние нельзя привести к терминальному.\n"
								+ "* Математическое описание:\n"
								+ "\tЕсли сумма N является нечётной, то решения головоломки не существует.\n"
								+ "\tСм. http://ru.wikipedia.org/wiki/Игра_в_15\n");
				/*System.exit(1);*/	
			}
		
		}
		

		int size = sideSize * sideSize;
		
		terminateField = getTerminalState(sideSize, size);

		Rules Interface = new Evristix(sideSize, terminateField);
		State startState = new State(null, sideSize);

	
		startState.setField(startField);

		A_Search<State, Rules> A_Search = new A_Search<State, Rules>(Interface);
		long time = System.currentTimeMillis();
		Collection<Solution> res = A_Search.search(startState);
		time = System.currentTimeMillis() - time;

		if (res == null) {
			System.out.println("Решение не найдено.");
			return;
		} else {
			for (Solution s : res) {
				System.out.println(s.toString());
			}
		}
		System.out.println("Время решения головоломки: " + time + " мс");
		/* Количество ходов считается минус начальное*/
		System.out.println("Количество ходов для решения головоломки: " + (res.size() - 1));
		
	}

	/**
	 * Считывает начальное состояние из входного потока, определяя размерность
	 * поля по количеству строк в потоке.
	 * 
	 * @return массив байт, описывающий начальное состояние или null, если не
	 *         удалось прочесть начальное состояние.
	 * @throws IOException
	 */
	private static byte[] readStartState() throws IOException {
		System.out.println("Введите массив, размером n*n:");
		InputStreamReader istr = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(istr);
		String line = null;
		sideSize = 0;
		StringBuffer buf = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			if (line.isEmpty()) {
				break;
			}
			buf.append(line + "\n");
			sideSize++;
		}
		String Solution = buf.toString();
		if (Solution.isEmpty()) {
			return null;
		} else {
			return State.parseField(Solution);
		}
	}

	/**
	 * Генерирует терминальное состояние, как упорядоченную последовательность чисел.
	 * 1 2 3 4
	 * 5 6 7 8
	 * 9 10 11 12
	 * 13 14 15 0
	 * 
	 * @param sideSize  		размер стороны поля.
	 * @param size 				размер.
	 * @return terminateField	возвращаем терминальное поле массива
	 */
	private static byte[] getTerminalState(int sideSize, int size) {
		if (terminateField == null) {
			terminateField = new byte[size];
			byte k = 0;
			for (int i = 0; i < sideSize; i++) {
				for (int j = 0; j < sideSize; j++) {
					terminateField[j + i * sideSize] = ++k;
					
				}
				
			}
			terminateField[size - 1] = 0;
			
		}
		return terminateField;
	}
}
