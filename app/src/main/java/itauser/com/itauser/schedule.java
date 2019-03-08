package itauser.com.itauser;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class schedule extends FragmentActivity {

    ArrayList<String> arrayList;
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        arrayList=new ArrayList<String>();
        arrayList.add("https://firebasestorage.googleapis.com/v0/b/informationtechnologyevent.appspot.com/o/schedule.png?alt=media&token=11b9596c-7a0e-451c-a3bf-c1d4af6480f0");
        arrayList.add("https://firebasestorage.googleapis.com/v0/b/informationtechnologyevent.appspot.com/o/Godi%2FIMG_20180216_191820-COLLAGE.jpg?alt=media&token=14d98c9d-2cce-49d6-85e4-508f8f4216f0");
        mPager=findViewById(R.id.pager);
        mPager.setAdapter(new adapter(schedule.this,arrayList));
    }


}