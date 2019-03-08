package itauser.com.itauser;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

public class adapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    ArrayList<String> arrayList;

    public adapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        if(arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.slide, container, false);

        PhotoView imageView = itemView.findViewById(R.id.viewPagerItem_image1);

        Glide.with(context).load(arrayList.get(position)).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}