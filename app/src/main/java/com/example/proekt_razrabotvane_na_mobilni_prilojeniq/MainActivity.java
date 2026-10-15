package com.example.proekt_razrabotvane_na_mobilni_prilojeniq;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ArrayAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editSellerName, editPhone, editEmail, editAddress,editProduct,editPrice;
    Button btnInsert;
    ListView listView;
    public class CustomArrayAdapter extends ArrayAdapter<DatabaseHelper.Sellers>{
        private Context context;
        private int resource;


        public CustomArrayAdapter(@NonNull Context context, int resource, @NonNull List<DatabaseHelper.Sellers> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            DatabaseHelper.Sellers contacts = getItem(position);

            if(convertView == null){
                convertView = LayoutInflater.from(context).inflate(resource,
                        parent, false);
            }
            TextView viewID =convertView.findViewById(R.id.listViewID);
            viewID.setText(contacts.getID());
            TextView viewName =convertView.findViewById(R.id.listViewName);
            viewName.setText(contacts.getName());
            TextView viewAddress =convertView.findViewById(R.id.listViewAddress);
            viewAddress.setText(contacts.getAddress());
            TextView viewPhone =convertView.findViewById(R.id.listViewPhone);
            viewPhone.setText(contacts.getPhone());
            TextView viewEmail =convertView.findViewById(R.id.listViewEmail);
            viewEmail.setText(contacts.getEmail());
            TextView viewProduct =convertView.findViewById(R.id.listViewProduct);
            viewProduct.setText(contacts.getProduct());
            TextView viewPrice =convertView.findViewById(R.id.listViewPrice);
            viewPrice.setText(contacts.getPrice());
            return  convertView;

        }

    }
    protected void FillListView(){
        try(DatabaseHelper db = new DatabaseHelper(getApplicationContext())){
            List<DatabaseHelper.Sellers> sellers = db.select();
            CustomArrayAdapter arrayAdapter = new CustomArrayAdapter(
                    this,
                    R.layout.activity_list_view,
                    sellers

            );
            listView.clearChoices();
            listView.setAdapter(arrayAdapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        FillListView();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editSellerName = findViewById(R.id.editSellerName);
        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);
        editAddress = findViewById(R.id.editAddress);
        editProduct = findViewById(R.id.editProduct);
        editPrice = findViewById(R.id.editPrice);
        btnInsert = findViewById(R.id.btnInsert);
        listView = findViewById(R.id.listView);

        FillListView();

        Validations.validateEmail(editEmail, btnInsert);

        editEmail.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Validations.validateEmail(editEmail, btnInsert);
                return false;
            }
        });

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String ID = ((TextView)(view.findViewById(R.id.listViewID))).getText().toString();
            String Name = ((TextView)(view.findViewById(R.id.listViewName))).getText().toString();
            String Address = ((TextView)(view.findViewById(R.id.listViewAddress))).getText().toString();
            String Email = ((TextView)(view.findViewById(R.id.listViewEmail))).getText().toString();
            String Phone = ((TextView)(view.findViewById(R.id.listViewPhone))).getText().toString();
            String Product = ((TextView)(view.findViewById(R.id.listViewProduct))).getText().toString();
            String Price = ((TextView)(view.findViewById(R.id.listViewPrice))).getText().toString();

            Bundle b = new Bundle();
            b.putString("ID", ID);
            b.putString("Name", Name);
            b.putString("Address", Address);
            b.putString("Email", Email);
            b.putString("Phone", Phone);
            b.putString("Product", Product);
            b.putString("Price", Price);

            Intent intent = new Intent(MainActivity.this, UpdateDelete.class);
            intent.putExtras(b);

            startActivityForResult(intent, 200, b);



        });


        btnInsert.setOnClickListener(view -> {
            try(DatabaseHelper db = new DatabaseHelper(getApplicationContext())){
                db.insert(
                        editSellerName.getText().toString(),
                        editPhone.getText().toString(),
                        editEmail.getText().toString(),
                        editAddress.getText().toString(),
                        editProduct.getText().toString(),
                        editPrice.getText().toString()
                );

                Toast.makeText(getApplicationContext(), "INSERT OK",
                        Toast.LENGTH_LONG).show();

            }catch ( Exception e){
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }

            FillListView();


        });


    }
}