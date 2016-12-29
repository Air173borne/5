package kursach;



import java.util.Arrays;

/**
 * ѕредставл€ет состо€ние игрового пол€ головоломки "ѕ€тнашки".
 * @param res 	количество переходов из первого состо€ни€ во второе.
 * @param trim 	удал€ет пробелы в начале и в конце строки.
 */
public class State extends Solution {

	public static byte[] parseField(String str) {
		int i = 0;
		String[] lines = str.split("\n");
		byte[] res = new byte[lines.length * lines.length];
		for (String line : lines) {
			String[] value = line.trim().replaceAll("\\s+", ":").split(":");
			for (String v : value) {
				res[i] = Byte.parseByte(v.trim());
				i++;
			}
		}
		return res;
	}

	/**
	 * ѕровер€ет, возможно ли привести состо€ние к терминальному. 
	 * 
	 * @param field	  состо€ние массива.
	 * @return true - если можно привести к терминальному.
	 * ѕровер€ем математически перед тем как запустить программу, 
	 * использу€ формулу из "ћатематического описани€"
	 */
	public static boolean checkState(byte[] field) {
		int N = 0;
		int e = 0;
		int sideSize = 2;
		for (int i = 0; i < field.length; i++) {
			/* ќпредел€етс€ номер р€да пустой клетки (счита€ с 1). */
			if (field[i] == 0) {
				e = i / sideSize + 1;
			}
			if (i == 0)
				continue;
			/* ѕроизводитс€ подсчет количества клеток меньших текущей */
			for (int j = i + 1; j < field.length; j++) {
				if (field[j] < field[i]) {
					N++;
				}
			}
		}
		N = N + e;
		System.out.println("N = " + N + "\n");
		/* ≈сли N €вл€етс€ нечЄтной, то решени€ головоломки не существует. */
		return (N & 1) == 0; // ѕервый бит четного числа равен 0
	}

	/**
	 * ¬озвращает состо€ние массива в виде одномерного массива байт.
	 */
	
	public byte[] getField() {
		return field;
	}
	private byte[] field;

	/**
	 * ”станавливает состо€ние массива
	 */
	public void setField(byte[] field) {
		this.field = field;
		hash = Arrays.hashCode(field);
	}
	private int hash;
	
	private int sideSize;
	@Override
	public String toString() {
		if (field == null) {
			return "" + null;
		}
		StringBuffer sbf;
		sbf = new StringBuffer(field.length);
		for (int i = 0; i < sideSize; i++) {
			for (int j = 0; j < sideSize; j++) {
				sbf.append(field[j + i * sideSize]);
				sbf.append(" ");
			}
			sbf.append("\n");
		}
		return sbf.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof State)) {
			return false;
		}
		return hash == obj.hashCode();
	}

	@Override
	public int hashCode() {
		return hash;
	}

	/**
	 * —оздает описание состо€ни€ заданного массива
	 * 
	 * @param parent	 	предшествуюущее состо€ние.
	 * @param sideSize		размер стороны пол€.
	 */
	public State(Solution parent, int sideSize) {
		super(parent);
		this.sideSize = sideSize;
	}
}
