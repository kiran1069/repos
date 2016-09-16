package testsample.altvr.com.testsample.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import testsample.altvr.com.testsample.ImageDownloader;
import testsample.altvr.com.testsample.R;
import testsample.altvr.com.testsample.util.DatabaseUtil;
import testsample.altvr.com.testsample.util.ItemImageTransformation;
import testsample.altvr.com.testsample.util.LogUtil;
import testsample.altvr.com.testsample.vo.PhotoVo;

public class ItemsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LogUtil log = new LogUtil(ItemsListAdapter.class);
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_ITEM = 0;

    private static final int INVALID_DIMEN = -1;

    private final ItemListener mListener;
    private final int mImageWidth;
    private List<PhotoVo> mItems;
    private Context mContext;
    private DatabaseUtil mDbUtil;

    public interface ItemListener {
        void itemClicked(ItemViewHolder rowView, int position);
    }

    public ItemsListAdapter(List<PhotoVo> items, ItemListener listener, int imageWidth, Context context) {
        LogUtil.log("DIN GAN");
        mItems = items;
        mListener = listener;
        mImageWidth = imageWidth;
        mContext = context;
        mDbUtil = new DatabaseUtil(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int viewType) {
        LogUtil.log("ARE U HERE -- ");
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_photos_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        LogUtil.log("ARTE U HERE ");
        new ImageDownloader(((ItemViewHolder)holder).itemImage, mItems.get(position).previewURL).execute();
        ((ItemViewHolder) holder).saveText.setText(R.string.save);
        ((ItemViewHolder) holder).itemName.setText(mItems.get(position).tags);

        ((ItemViewHolder)holder).itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.log("SAVE VALUES");

                if (((ItemViewHolder) holder).saveText.getText().equals(mContext.getString(R.string.save))) {
                    ((ItemViewHolder) holder).saveText.setText(R.string.saved);
                    mDbUtil.saveImage(mItems.get(position).id, mItems.get(position).previewURL);
                } else {
                    ((ItemViewHolder) holder).saveText.setText(R.string.save);
                    mDbUtil.deleteImage(mItems.get(position).previewURL);
                }

            }
        });

        boolean isAvailable =  mDbUtil.getImage(mItems.get(position).previewURL);
        LogUtil.log("isAvailable "+isAvailable);
        if (isAvailable) {
            ((ItemViewHolder) holder).saveText.setText(R.string.saved);
        } else {
            ((ItemViewHolder) holder).saveText.setText(R.string.save);
        }
      	/*
         * YOUR CODE HERE
         *
         * For Part 1a, you should get the proper PhotoVo instance from the mItems collection,
         * image, text, etc, into the ViewHolder (which will be an ItemViewHolder.)
         *
         * For part 1b, you should attach a click listener to the save label so users can save
         * or delete photos from their local db.
         */
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isImageSizeGiven() {
        return mImageWidth != INVALID_DIMEN;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemImage;
        public TextView itemName;
        public TextView saveText;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.itemName);
            itemImage = (ImageView) itemView.findViewById(R.id.itemImage);
            saveText = (TextView) itemView.findViewById(R.id.saveText);
        }
    }
}
