package itauser.com.itauser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


    public class ViewPagerAdapter extends PagerAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private Integer [] images={R.drawable.psg_logo,R.drawable.bugboss,R.drawable.computergeeks,R.drawable.crimescene,R.drawable.cryptica,R.drawable.doctype,R.drawable.funbuzz,R.drawable.maze,R.drawable.runsomewhere,R.drawable.zesttech};

        public ViewPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=layoutInflater.inflate(R.layout.custom_layout,null);
            ImageView imageView=(ImageView) view.findViewById(R.id.imageView11);
            imageView.setImageResource(images[position]);

            ViewPager vp=(ViewPager) container;
            vp.addView(view,0);
            return view;

        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            ViewPager vp= (ViewPager) container ;
            View view=(View) object;
            vp.removeView(view);
        }
    }

