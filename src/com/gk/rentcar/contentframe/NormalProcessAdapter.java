package com.gk.rentcar.contentframe;

import java.util.LinkedList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.gk.net.MyImageListener;
import com.gk.rentcar.R;
import com.gk.rentcar.entity.CarTypeEntity;

public class NormalProcessAdapter extends BaseAdapter{
	private LinkedList<CarTypeEntity>  listItems; 
	private Context context;
	private ImageLoader imageLoader;
	public NormalProcessAdapter(LinkedList<CarTypeEntity> listItems, Context context, ImageLoader imageLoader) {
		super();
		this.listItems = listItems;
		this.context = context;
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CarHolder holder;
		if(convertView != null) holder = (CarHolder) convertView.getTag();
		else{
			convertView = View.inflate(context, R.layout.normalprocess_adapter_item, null);
			holder = new CarHolder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.normal_car_imageview);
			holder.txtTitle = (TextView) convertView.findViewById(R.id.normal_car_title);
			holder.txtIntro = (TextView) convertView.findViewById(R.id.normal_car_intro);
		}
		holder.imageView.setTag(listItems.get(position).getCarImgUrl());
		holder.txtTitle.setText(listItems.get(position).getCarType()+" p:"+ position);
		holder.txtIntro.setText(listItems.get(position).getCarInfo());
		
		
		imageLoader.get(listItems.get(position).getCarImgUrl(), 
				new MyImageListener(holder.imageView)
//				, holder.imageView.getWidth(),holder.imageView.getHeight()
				);
		convertView.setTag(holder);
		return convertView;
	}
	static public class CarHolder{
		public ImageView imageView;
		public TextView txtTitle;
		public TextView txtIntro;
	}
}

