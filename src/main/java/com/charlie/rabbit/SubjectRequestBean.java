package com.charlie.rabbit;

public interface SubjectRequestBean {
	public void registerObserver(ObserverRequestBean o);
	public void removeObserver(ObserverRequestBean o);
	public void notifyObserversRequestBean();
}
