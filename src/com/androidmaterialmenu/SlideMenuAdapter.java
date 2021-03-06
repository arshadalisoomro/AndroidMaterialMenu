package com.androidmaterialmenu;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by hp1 on 28-12-2014.
 */
public class SlideMenuAdapter extends RecyclerView.Adapter<SlideMenuAdapter.ViewHolder> {

	private static final int TYPE_HEADER = 0;
	private static final int TYPE_ITEM = 1;

	private String mNavTitles[];
	private int mIcons[];

	private String name;
	private int profile;
	private String email;
    
	private Context context;

	private DisplayImageOptions options;
	private ImageLoader imageLoader=ImageLoader.getInstance();
	
	public SlideMenuAdapter(Context context,String Titles[], int Icons[], String Name,String Email, int Profile) {
		
		this.context=context;
		mNavTitles = Titles;
		mIcons = Icons;
		name = Name;
		email = Email;
		profile = Profile;
		
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_profilepic)
				.showImageForEmptyUri(R.drawable.ic_profilepic)
				.showImageOnFail(R.drawable.ic_profilepic).cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(true).build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
	}


	public static class ViewHolder extends RecyclerView.ViewHolder {
		int Holderid;

		TextView textView;
		ImageView imageView,shape;
		CircleImageView profile;
		TextView Name;
		TextView email;

		public ViewHolder(View itemView, int ViewType) {
			super(itemView);

			if (ViewType == TYPE_ITEM) {
				textView = (TextView) itemView.findViewById(R.id.rowText);
				imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
				Holderid = 1;
			} else {
				Name = (TextView) itemView.findViewById(R.id.name);
				email = (TextView) itemView.findViewById(R.id.email);
				profile = (CircleImageView) itemView.findViewById(R.id.circleView);
				shape =  (ImageView) itemView.findViewById(R.id.shape);
				Holderid = 0;
			}
		}
	}


    @Override
    public SlideMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_row,parent,false); 
            ViewHolder vhItem = new ViewHolder(v,viewType); 
            return vhItem; 

        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.slidemenu_pfpic_header,parent,false);
            ViewHolder vhHeader = new ViewHolder(v,viewType); 
            return vhHeader; 
        }
        return null;

    }


	@Override
	public void onBindViewHolder(SlideMenuAdapter.ViewHolder holder,final int position) {
		if (holder.Holderid == 1) {
			holder.textView.setText(mNavTitles[position - 1]);
			holder.imageView.setImageResource(mIcons[position - 1]);
			
			holder.textView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (viewclickevent!=null) {
						viewclickevent.OnViewItemClick(position);
					}
				}
			});
			holder.imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (viewclickevent!=null) {
						viewclickevent.OnViewItemClick(position);
					}
				}
			});

		} else {
			imageLoader.displayImage(Constants.IMAGES[12], holder.profile, options);
			try {
				GradientDrawable gradientDrawable = (GradientDrawable) holder.shape.getBackground();
				gradientDrawable.setStroke(5, context.getResources().getColor(android.R.color.white));
			} catch (Exception e) {
				e.printStackTrace();
			}
			holder.Name.setText(name);
			holder.email.setText(email);
		}
	}

	@Override
	public int getItemCount() {
		return mNavTitles.length + 1;
	}

	@Override
	public int getItemViewType(int position) {
		if (isPositionHeader(position))
			return TYPE_HEADER;
		
		return TYPE_ITEM;
	}

	private boolean isPositionHeader(int position) {
		return position == 0;
	}

	public viewCLickEvent viewclickevent;

	public viewCLickEvent getViewclickevent() {
		return viewclickevent;
	}

	public void setViewclickevent(viewCLickEvent viewclickevent) {
		this.viewclickevent = viewclickevent;
	}

	public interface viewCLickEvent {
		public void OnViewItemClick(int position);
	}

}
