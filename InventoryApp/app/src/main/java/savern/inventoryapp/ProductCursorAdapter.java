package savern.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import savern.inventoryapp.data.ProductContract.ProductEntry;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class ProductCursorAdapter extends CursorAdapter {


    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        int cursorId = cursor.getInt(cursor.getColumnIndex(ProductEntry._ID));

        final Uri currentUri = Uri.withAppendedPath(ProductEntry.CONTENT_URI, String.valueOf(cursorId));

        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        TextView salesTextView = (TextView) view.findViewById(R.id.sales);
        Button saleButton = (Button) view.findViewById(R.id.sale_button);

        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
        int saleColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SALES);

        String productName = cursor.getString(nameColumnIndex);
        int productPrice = cursor.getInt(priceColumnIndex);
        final int productQuantity = cursor.getInt(quantityColumnIndex);
        final int productSales = cursor.getInt(saleColumnIndex);


        final Context contx = context;
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (0 < productQuantity) {
                    int decreasedNum = productQuantity - 1;
                    int salesNum = productSales + 1;
                    ContentValues values = new ContentValues();
                    values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, decreasedNum);
                    values.put(ProductEntry.COLUMN_PRODUCT_SALES, salesNum);
                    contx.getContentResolver().update(currentUri, values, null, null);
                }
            }
        });


        nameTextView.setText(productName);
        priceTextView.setText(productPrice + " $");
        quantityTextView.setText("Quantity: " + productQuantity);
        salesTextView.setText("Sales: " + productSales);
    }


}
