package com.example.captainhumza.fyp_version3.Customer.Fragments.ExpandableListDirectory;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.captainhumza.fyp_version3.Classes.ConstantClass;
import com.example.captainhumza.fyp_version3.Classes.ProductCategory;
import com.example.captainhumza.fyp_version3.Classes.Products;
import com.example.captainhumza.fyp_version3.MainActivity;
import com.example.captainhumza.fyp_version3.app.AppController;
import com.example.captainhumza.fyp_version3.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Captain Humza on 10/5/2017.
 */

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ProductCategory> productCategories;
    private HashMap<ProductCategory, List<Products>> expandableListDetail;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public List<Products> cartList = new ArrayList<Products>();
    public OnImageClickListener mListener;
    int counter = 0;

    public CustomExpandableListAdapter(Context context, List<ProductCategory> expandableListTitle,
                                       HashMap<ProductCategory, List<Products>> expandableListDetail) {
        this.context = context;
        this.productCategories = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Products getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.productCategories.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final View v = convertView;
        final String expandedListText = getChild(listPosition, expandedListPosition).productName;
        final String expandableImage = getChild(listPosition, expandedListPosition).productImage;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        try
        {

            ImageView im = (ImageView)convertView.findViewById(R.id.SingleCart);
            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Products product = new Products();
                    product.productName = expandedListText;
                    product.productImage = expandableImage;

                    cartList.add(product);
                    if(cartList.size()> 0) {
                        TextView tex = (TextView) MainActivity.instance().findViewById(R.id.actionbar_notifcation_textview);
                        counter++;
                        tex.setText( counter+"");
                    }
                    //Toast.makeText(context, "yahoooo", Toast.LENGTH_SHORT).show();
                }

            });

        }catch (Exception ex)
        {
            Toast.makeText(context, ex.getMessage() + "yahoo  wala", Toast.LENGTH_SHORT).show();
        }
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnailSubProduct);
        thumbNail.setImageUrl(ConstantClass.productImagePath+expandableImage,imageLoader);

        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        int ab = this.expandableListDetail.get(this.productCategories.get(listPosition)).size();
        return ab;
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.productCategories.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.productCategories.size();

    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    static int ab = 0;
    public int GetImageIndex(String title)
    {
        int res = 0;
        for (int i = 0; i < productCategories.size(); i++)
        {
            if(productCategories.get(i).ProductSubCat.equals(title)) {
                res = i;
                break;
            }
        }
        return res;

    }
    @Override
    public View getGroupView(final int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
      //  final String listTitle = (String) getGroup(listPosition);
        ProductCategory listObject = (ProductCategory) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        final String path = productCategories.get(GetImageIndex(listObject.ProductSubCat)).ProductSubImage;
        //int lsPosition = hashKey.size();

  //     if(hashKey.containsKey(listTitle) == false)
    //    {
            //Picasso.Builder builder = new Picasso.Builder(context);
           // builder.listener(new Picasso.Listener()
            //{
              //  @Override
                //public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {

                  //  Toast.makeText(context,exception.getMessage()+"here path __"+path,Toast.LENGTH_SHORT).show();
                    //exception.printStackTrace();
                //}

//            });*//*
            //final Bitmap[] temp = {null};
  //           Target mTarget;
    //        mTarget = new Target() {
      //          @Override
        //        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
          //          hashKey.put(listTitle , bitmap);
            //        imageView.setImageBitmap(hashKey.get(listTitle));
                    //imageViews.add(bitmap);
                    //temp[0] = bitmap;
              //  }

                //@Override
                //public void onBitmapFailed(Drawable errorDrawable) {

                //}

                //@Override
               // public void onPrepareLoad(Drawable placeHolderDrawable) {

                //}
            //};
            //builder.build().load("http://192.168.10.11/"+path).into(mTarget);
            //Picasso.with(context).load("http://192.168.10.13/"+path).into(mTarget);
            //imageView.setImageBitmap(temp[0]);
            //Picasso.with(convertView.getContext()).load("http://192.168.10.10/"+path).error(R.id.listTitle).into(imageView);
            //imageView.buildDrawingCache();
            //Bitmap bmap = imageView.getDrawingCache();
            //imageViews.add(bmap);
            //imageViews.add(imageView.setImage);
        //}
       // imageView.setImageBitmap(hashKey.get(listTitle));*/
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        thumbNail.setImageUrl(ConstantClass.productImagePath+path,imageLoader);
        //imageViews.
        //if(imageViews.size() > listPosition)
        //{

            //imageView.setImageBitmap(imageViews.get(listPosition));
        //}
        //imageView.setImageBitmap(imageViews.get(listPosition));
        //if(imageViews.get(listPosition) != null)
        //{

            //imageView.se
            //imageView = imageViews.get(listPosition);
        //}
        //imageView.setImageBitmap(ExpandableListDataPump.productCategories.get(GetImageIndex(listTitle)).decodedByte);categoryText
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);

        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listObject.ProductSubCat);
        TextView category = (TextView) convertView
                .findViewById(R.id.categoryText);
        category.setText(listObject.CategoryDescription);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    public void setFilter(List<ProductCategory> abc){
        productCategories = new ArrayList<>();
        productCategories.addAll(abc);
        notifyDataSetChanged();
    }

    public interface OnImageClickListener {
        public void onImageClicked();
    }
}
