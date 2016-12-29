package kursach;

public class Evristix extends Rules {

    /** ���������: ��������� ������� �� ������ ������� ���������� �������. 
     * ����������� ������� � ������ ������������� ���������
     */
    @Override
    public int getH(State Solution) {
        int res = 0;
        int penalty = sideSize;
        for (int i = 0; i < size; i++) {
            if ((i+1) % sideSize == 0) {
                penalty--;
            }
            if (Solution.getField()[i] != terminateState[i]) {
                res += penalty;
            }
        }
        return res;
    }

    /**
     * @param fieldSize      ������ ���� (���������� ������ �� ����� �������).
     * @param terminateState �������� ��������.
     */
    public Evristix(int fieldSize, byte[] terminateState) {
        super(fieldSize, terminateState);
    }
}
