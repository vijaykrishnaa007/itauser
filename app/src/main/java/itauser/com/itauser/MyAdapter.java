package itauser.com.itauser;


import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    ArrayList<Profile1> profiles;

    public MyAdapter(Context c,ArrayList<Profile1> p)
    {
        context=c;
        profiles=p;
    }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardforsponsors,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.sponsor_name.setText(profiles.get(i).getName());
        Picasso.get().load(profiles.get(i).getImage()).into(myViewHolder.sponsorimage);

    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView sponsor_name;
        ImageView sponsorimage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sponsor_name=itemView.findViewById(R.id.sponsorname);
            sponsorimage=itemView.findViewById(R.id.sponsorimageid);
        }

    }
}
