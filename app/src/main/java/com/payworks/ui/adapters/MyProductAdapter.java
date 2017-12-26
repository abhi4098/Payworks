package com.payworks.ui.adapters;

/**
 * Created by Abhinandan on 21/9/17.
 */

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.generated.model.Product;
import com.payworks.ui.activities.MyProductActivity;

import java.util.ArrayList;


public class MyProductAdapter extends ArrayAdapter<Product> {

    int groupid;
    ArrayList<Product> myProductList;
    Context context;


    public MyProductAdapter(MyProductActivity myProductActivity, int layout_my_product_list, int date, ArrayList<Product> myProductList) {
        super(myProductActivity,layout_my_product_list,date,myProductList);
        Log.e("abhi", "MyProductAdapter: " );
        groupid=layout_my_product_list;
        this.context = myProductActivity;
        this.myProductList = myProductList;

    }

    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView productName;
        public TextView productShipping;
        public TextView productSold;
        public TextView productPrice;
        public TextView productUpdateDate;





    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Log.e("abhi", "getView:------------------------- " );

        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.productName= (TextView) rowView.findViewById(R.id.product_name);
            viewHolder.productPrice= (TextView) rowView.findViewById(R.id.product_price);
            viewHolder.productShipping= (TextView) rowView.findViewById(R.id.product_shipping);
            viewHolder.productSold= (TextView) rowView.findViewById(R.id.product_sold);
            viewHolder.productUpdateDate= (TextView) rowView.findViewById(R.id.update_date);


            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final Product product = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (product !=null) {


            holder.productName.setText(product.getProductname());
            holder.productShipping.setText(product.getProductshipping());
            holder.productSold.setText(product.getSold());
            holder.productPrice.setText(product.getProductprice());

            if (product.getUpdatedDate() != null) {
                String date = product.getUpdatedDate();
                String[] splited = date.split("\\s+");
                String productDate = splited[0];
                holder.productUpdateDate.setText(productDate);
            }




        }



        return rowView;
    }


}

