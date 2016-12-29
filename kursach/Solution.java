package kursach;

/**
 * Представляет вершину графа решений.
 */
public abstract class Solution {

    /**
     * Возвращает вес состояния как сумму расстояния(от начального состояния
     * до текущего) и эвристической оценки(предполагаемого расстояния от
     * текущего состояния до терминального).
     */
	private int g;
    public int getF() {
        return g + h;
    }

    /**
     * Возвращает расстояние от начального состояния до текущего.
     */
    public int getG() {
        return g;
    }

    /**
     * Устанавливает значение оценки расстояния от начального состояния до
     * текущего.
     */
    public void setG(int g) {
        this.g = g;
    }

    /**
     * Возвращает эвристическую оценку расстояния от текущего состояния до
     * терминального.
     */
    private int h;
    public int getH() {
        return h;
    }

    /**
     * Устанавливает значение эвристической оценки расстояния от текущего состояния до конечного.
     */
    public void setH(int h) {
        this.h = h;
    }

    /**
     * Возвращает предшествующее состояние.
     */
    private Solution parent;
    public Solution getParent() {
        return parent;
    }

    public void setParent(Solution parent) {
        this.parent = parent;
    }

    public Solution(Solution parent) {
        this.parent = parent;
    }
}
