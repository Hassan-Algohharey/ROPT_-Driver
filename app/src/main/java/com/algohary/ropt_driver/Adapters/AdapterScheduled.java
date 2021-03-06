package com.algohary.ropt_driver.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.algohary.ropt_driver.Activity.MapsActivity;
import com.algohary.ropt_driver.Models.ModelSecheduled;
import com.algohary.ropt_driver.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdapterScheduled extends RecyclerView.Adapter<AdapterScheduled.MyHolder> {
    Context context;
    List<ModelSecheduled> userList;

    public AdapterScheduled(Context context, List<ModelSecheduled> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_scheduled, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String Car = userList.get(position).getoCar();
        String Distance = userList.get(position).getoDis();
        String From = userList.get(position).getoFrom();
        final String Oid = userList.get(position).getoId();
        final String  latlngstart =userList.get(position).getoStart();
        final String  latlngend =userList.get(position).getoEnd();
        String Price = userList.get(position).getoPrice();
        String Status = userList.get(position).getoStatus();
        String Time = userList.get(position).getoTime();
        String To = userList.get(position).getoTo();
        String Uid = userList.get(position).getuId();
        String phone = userList.get(position).getuPhone();

        switch (Integer.parseInt(Car)) {
            case 1:
                try {
                    Picasso.get().load(R.drawable.truck_1).placeholder(R.drawable.truck_1).into(holder.imageView_car);
                } catch (Exception ignored) {
                }
                break;
            case 2:
                try {
                    Picasso.get().load(R.drawable.truck_2).placeholder(R.drawable.truck_2).into(holder.imageView_car);
                } catch (Exception ignored) {
                }
                break;
            case 3:
                try {
                    Picasso.get().load(R.drawable.truck_3).placeholder(R.drawable.truck_3).into(holder.imageView_car);
                } catch (Exception ignored) {
                }
                break;
        }

        holder.Dis.setText(Distance);
        holder.From.setText(From);
        holder.Price.setText(Price);
        holder.Time.setText(getDate(Long.parseLong(Time)));
        holder.To.setText(To);
        holder.Phone.setText(phone);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context.getApplicationContext(), MapsActivity.class);
                intent.putExtra("latlngStart",latlngstart);
                intent.putExtra("latlngEnd",latlngend);
                intent.putExtra("activity","order");
                intent.putExtra("oId",Oid);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        ImageView imageView_car;
        TextView Dis,From,Price,Time,To, Phone;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView_car = itemView.findViewById(R.id.image_place_scheduled);
            Dis = itemView.findViewById(R.id.distance_scheduled);
            From = itemView.findViewById(R.id.from_scheduled);
            Price = itemView.findViewById(R.id.price_scheduled);
            Time = itemView.findViewById(R.id.date_scheduled);
            To = itemView.findViewById(R.id.to_scheduled);
            Phone = itemView.findViewById(R.id.phone_scheduled);
        }
    }
    private String getDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        Date d = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mm a");
        return simpleDateFormat.format(d);

    }
}
