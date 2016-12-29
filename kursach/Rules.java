package kursach;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ���������� ����������� ��� ������ ������� �� �������. � ������ ����������
 * ��������� ����������� ��� ���������� ������, ����������� �� �� ����� ������.
 */


public class Rules implements Interface<State> {
	
	protected int sideSize;
	protected int size;
	protected int[] actions;
	/**
	 * �������� ��������� �� �������
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
	 * ������������ ���������� �������� �� a �� b.
	 * 
	 * @param a 		������ ���������. ������ ���� ����� ���������, �������������� b.
	 * @param b 		������ ���������.
	 * @return res		���������� ��������� �� a �� b.
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
		 * �� ����� ����, � ���� ��������� ���������� �*, ������� ������
		 * ���������� ������ ���������� 1.
		 */
	}

	/**
	 * ��������� ����������� ��� ���������� ������, ����������� �� �� ����� ������ (���� ������������ ���������).
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
	 * ��������� � ��������� �������.
	 * 
	 * @param field		 	��������� ���������.
	 * @param action	 	����������� �������.
	 * @return 	null 		���� ��������� ������������. ����� ���������, ���������� � ���������� ���������� �������. 
	 */
	public byte[] doAction(byte[] field, int action) {
		/* ����������� ����� ������ ������, ������ ������ ����������� ������ "0" */
		int zero = 0;
		for (; zero < field.length; zero++) {
			if (field[zero] == 0) {
				break;
			}
			if (zero >= field.length) {
				return null;
			}
		}
		
		/* ����������� ������ ������������ ������ */
		int number = zero + action;
		/* ����������� ������������ ���� */
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
		 * ��������� ����� ������, �� ������� �������� ������� ������ �
		 * ������������ ������
		 */
		byte[] newField = Arrays.copyOf(field, field.length);
		byte temp = newField[zero];
		newField[zero] = newField[number];
		newField[number] = temp;

		return newField;
	}

	/**
	 * ����������� ���� � ������ �������
	 */
	private int left = -1;
	private int top;
	private int right = 1;
	private int bottom;
	/**
	 * ������ ������ ���� � ��� ����������� �����-�� ������� ���������� 
	 * @param fieldSize				 	������ ���� (���������� ������ �� ����� �������).
	 * @param terminateState	 		�������� ��������.
	 * @param IllegalArgumentException	����� ������
	 */
	public Rules(int fieldSize, byte[] terminateState) {
		if (fieldSize < 2) {
			throw new IllegalArgumentException("������� ����� ������.");
		}
		if (terminateState == null) {
			throw new IllegalArgumentException("������������ ��������� �� ����� ���� null.");
		}

		this.sideSize = fieldSize;
		size = sideSize * sideSize;

		if (terminateState.length != size) {
			throw new IllegalArgumentException("������ ������������� ��������� �����������.");
		}
		this.terminateState = terminateState;
			top = -sideSize;
			bottom = sideSize;
			actions = new int[] { top, bottom, left, right };
	}
	
	/**
	 * ���������� ������ ��������� ��������.
	 */
	public int[] getActions() {
		return actions;
	}
}
