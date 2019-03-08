package itauser.com.itauser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private DrawerLayout drawerobj;
    private ActionBarDrawerToggle mtoggle;
    ViewPager viewPager;
    LinearLayout sliderdotspanel;
    private int dotscount;
    private ImageView[] dots;
    Button events,user,sponsors,map;
    FirebaseAuth auth;
    private ProgressDialog load;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        events=findViewById(R.id.events);
        auth=FirebaseAuth.getInstance();
        map=findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,psgmap.class));
            }
        });
        sponsors=findViewById(R.id.sponsors);
        drawerobj=findViewById(R.id.drawerlayout);
        mtoggle=new ActionBarDrawerToggle(this,drawerobj,R.string.open,R.string.close);
        drawerobj.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        load=new ProgressDialog(this,R.style.loadstyle);
        findViewById(R.id.insta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.instagram.com/sai_god_i/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        findViewById(R.id.fb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/Psgtech.ITA/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        findViewById(R.id.schedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,schedule.class));
            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,events.class));
            }
        });

        user=findViewById(R.id.userinfo);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,userinfo.class));
            }
        });
        FirebaseDatabase.getInstance().getReference().child("USERS").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                transfer.dataSnapshot=dataSnapshot;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
sponsors.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(MainActivity.this,sponsors.class));
    }
});
        viewPager=findViewById(R.id.viewPager);
        sliderdotspanel=findViewById(R.id.Sliderdots);

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        dotscount=viewPagerAdapter.getCount();
        dots=new ImageView[dotscount];
        for(int i=0;i<dotscount;i++)
        {
            dots[i]=new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0,4,0);
            sliderdotspanel.addView(dots[i],params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int pos) {
                for(int i=0;i<dotscount;i++)
                {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));
                }
                dots[pos].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,3000);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id= menuItem.getItemId();

        if(id==R.id.nav_logout)
        {
            auth.signOut();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }
        if(id==R.id.nav_changepass)
        {
            startActivity(new Intent(MainActivity.this,changepass.class));
            finish();
        }
        if(id==R.id.nav_profile)
        {
            startActivity(new Intent(MainActivity.this,userinfo.class));
            finish();
        }
        if(id==R.id.nav_contact)
        {
            startActivity(new Intent(MainActivity.this,contact.class));
            finish();
        }
        if(id==R.id.nav_about)
        {
            startActivity(new Intent(MainActivity.this,about.class));
            finish();
        }

        return false;
    }

    public void onBackPressed()
    {


    }
    public class MyTimerTask extends TimerTask
    {

        @Override
        public void run()
        {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem()==0){
                        viewPager.setCurrentItem(1);
                    }
                    else if(viewPager.getCurrentItem()==1)
                    {
                        viewPager.setCurrentItem(2);
                    }
                    else if(viewPager.getCurrentItem()==2)
                    {
                        viewPager.setCurrentItem(3);
                    }
                    else if(viewPager.getCurrentItem()==3)
                    {
                        viewPager.setCurrentItem(4);
                    }
                    else if(viewPager.getCurrentItem()==4)
                    {
                        viewPager.setCurrentItem(5);
                    }
                    else if(viewPager.getCurrentItem()==5)
                    {
                        viewPager.setCurrentItem(6);
                    }
                    else if(viewPager.getCurrentItem()==6)
                    {
                        viewPager.setCurrentItem(7);
                    }
                    else if(viewPager.getCurrentItem()==7)
                    {
                        viewPager.setCurrentItem(8);
                    }
                    else if(viewPager.getCurrentItem()==8)
                    {
                        viewPager.setCurrentItem(9);
                    }

                    else
                    {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}
