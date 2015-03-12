package cartel.mines.nantes2015;

import beans.Course;
import adapters.ParticipantListeAdapter;
import android.app.ActionBar;
import android.app.ListActivity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;

public class CourseActivity extends ListActivity{
	
	ListView list;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_layout);
		
		Course course = (Course) getIntent().getSerializableExtra("course");
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(course.getMatchType());
		actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.vert_cartel)));
		
		list = getListView();
		
		list.setAdapter(new ParticipantListeAdapter(this, R.layout.participant_item, course.getParticipants()));
		
	}

}
