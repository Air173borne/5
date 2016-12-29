package kursach;



import java.util.Arrays;

/**
 * ������������ ��������� �������� ���� ����������� "��������".
 * @param res 	���������� ��������� �� ������� ��������� �� ������.
 * @param trim 	������� ������� � ������ � � ����� ������.
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
	 * ���������, �������� �� �������� ��������� � �������������. 
	 * 
	 * @param field	  ��������� �������.
	 * @return true - ���� ����� �������� � �������������.
	 * ��������� ������������� ����� ��� ��� ��������� ���������, 
	 * ��������� ������� �� "��������������� ��������"
	 */
	public static boolean checkState(byte[] field) {
		int N = 0;
		int e = 0;
		int sideSize = 2;
		for (int i = 0; i < field.length; i++) {
			/* ������������ ����� ���� ������ ������ (������ � 1). */
			if (field[i] == 0) {
				e = i / sideSize + 1;
			}
			if (i == 0)
				continue;
			/* ������������ ������� ���������� ������ ������� ������� */
			for (int j = i + 1; j < field.length; j++) {
				if (field[j] < field[i]) {
					N++;
				}
			}
		}
		N = N + e;
		System.out.println("N = " + N + "\n");
		/* ���� N �������� ��������, �� ������� ����������� �� ����������. */
		return (N & 1) == 0; // ������ ��� ������� ����� ����� 0
	}

	/**
	 * ���������� ��������� ������� � ���� ����������� ������� ����.
	 */
	
	public byte[] getField() {
		return field;
	}
	private byte[] field;

	/**
	 * ������������� ��������� �������
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
	 * ������� �������� ��������� ��������� �������
	 * 
	 * @param parent	 	��������������� ���������.
	 * @param sideSize		������ ������� ����.
	 */
	public State(Solution parent, int sideSize) {
		super(parent);
		this.sideSize = sideSize;
	}
}
