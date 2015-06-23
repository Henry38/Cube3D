package application;

public interface Observable {
	public void addObserver(Observer obs);
	public void removeObserver();
	public void notifyObserver(int line, int column);
}
