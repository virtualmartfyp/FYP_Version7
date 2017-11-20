package com.example.captainhumza.fyp_version3.Customer.Fragments.ExpandableListDirectory;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.captainhumza.fyp_version3.Classes.ConstantClass;
import com.example.captainhumza.fyp_version3.Classes.ProductCategory;
import com.example.captainhumza.fyp_version3.Classes.Products;
import com.example.captainhumza.fyp_version3.DataBase.DataBaseHandler;
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
    public List<Products> cartList = new ArrayList<Products>();;
    public OnImageClickListener mListener;
    public static int counter = 0;

    public CustomExpandableListAdapter(Context context, List<ProductCategory> expandableListTitle,
                                       HashMap<ProductCategory, List<Products>> expandableListDetail) {
        this.context = context;
        this.productCategories = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        try
        {
            TextView xxh = (TextView)MainActivity.instance().findViewById(R.id.actionbar_notifcation_textview);
            xxh.setText(counter+"");
        }catch (Exception ex)
        {
            Toast.makeText(MainActivity.instance(), ex.getMessage()+"customerAdapter Constructor", Toast.LENGTH_SHORT).show();
        }
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
        final int expandedId = getChild(listPosition, expandedListPosition).ProductId;
        final String expandedListText = getChild(listPosition, expandedListPosition).ProductName;
        final String expandableImage = getChild(listPosition, expandedListPosition).ProductImage;
        final int expandableWeight = getChild(listPosition, expandedListPosition).Weight;
        final double expandablePrice = getChild(listPosition, expandedListPosition).ProductRate;
        final String expandableUnit = getChild(listPosition, expandedListPosition).UnitDEscription;
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
                    product.ProductId = expandedId;
                    product.ProductName = expandedListText;
                    product.ProductImage = expandableImage;
                    product.ProductRate = expandablePrice;
                    product.Weight = expandableWeight;
                    product.UnitDEscription = expandableUnit;
                    product.totalPrice = expandablePrice;
                    product.quantity = 1;

                    cartList.add(product);
                    if(cartList.size()> 0) {
                        TextView tex = (TextView) MainActivity.instance().findViewById(R.id.actionbar_notifcation_textview);
                        counter++;
                        tex.setText( counter+"");
                    }
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
        TextView priceText = (TextView) convertView.findViewById(R.id.ProductPrice);
        priceText.setText(expandablePrice+"");
        TextView weightText = (TextView) convertView.findViewById(R.id.ProductWeight);
        weightText.setText(expandableWeight +" "+ expandableUnit);
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

        final ProductCategory listObject = (ProductCategory) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        ProgressBar progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        final String path = productCategories.get(GetImageIndex(listObject.ProductSubCat)).ProductSubImage;
        final ImageView imgStar = (ImageView)convertView.findViewById(R.id.starId);
        imgStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgStar.setImageResource(R.drawable.colored_star);
                //DataBaseHandler dt = new DataBaseHandler(MainActivity.instance());

                /*boolean res = dt.insertData(listObject);

                if (res == true) {
                    Toast.makeText(MainActivity.instance(), "data is inserted", Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(MainActivity.instance(),"not inserted",Toast.LENGTH_LONG).show();*/

            }
        });
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        thumbNail.setImageUrl(ConstantClass.productImagePath+path,imageLoader);
        //progressBar.setVisibility(View.INVISIBLE);
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
        //productCategories = new ArrayList<>();
        productCategories.addAll(abc);
        notifyDataSetChanged();
    }

    public interface OnImageClickListener {
        public void onImageClicked();
    }
}
