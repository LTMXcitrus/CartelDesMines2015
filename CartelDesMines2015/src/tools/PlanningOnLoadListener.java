package tools;

import java.util.ArrayList;

import beans.CalendarEvent;

public interface PlanningOnLoadListener {
	
	public void onLoadFinished(ArrayList<CalendarEvent> firstDayEvent, ArrayList<CalendarEvent> secondDayEvent,
			ArrayList<CalendarEvent> thirdDayEvent, ArrayList<CalendarEvent> fourthDayEvent);

}
