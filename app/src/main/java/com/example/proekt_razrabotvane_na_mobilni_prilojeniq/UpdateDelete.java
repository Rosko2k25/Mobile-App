package com.example.proekt_razrabotvane_na_mobilni_prilojeniq;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateDelete extends AppCompatActivity {

    EditText editName, editPhone, editEmail, editAddress,editProduct,editPrice;
    Button btnUpdate, btnDelete;
    String ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        editName = findViewById(R.id.editSellerName);
        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);
        editAddress = findViewById(R.id.editAddress);
        editProduct = findViewById(R.id.editProduct);
        editPrice = findViewById(R.id.editPrice);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        Bundle b = getIntent().getExtras();
        ID = b.getString("ID");
        editName.setText(b.getString("Name"));
        editPhone.setText(b.getString("Phone"));
        editEmail.setText(b.getString("Email"));
        editAddress.setText(b.getString("Address"));
        editProduct.setText(b.getString("Product"));
        editPrice.setText(b.getString("Price"));

        Validations.validateEmail(editEmail, btnUpdate);




        findViewById(R.id.btnShowMap).setOnClickListener(
                view -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", ID);
                    bundle.putString("Address", editAddress.getText().toString());
                    bundle.putString("Phone", editPhone.getText().toString());
                    bundle.putString("Email", editEmail.getText().toString());
                    bundle.putString("Name", editName.getText().toString());
                    bundle.putString("Product", editProduct.getText().toString());
                    bundle.putString("Price", editPrice.getText().toString());

                    Intent intent = new Intent(
                            UpdateDelete.this,
                            MapsActivity.class
                    );
                    intent.putExtras(bundle);
                    startActivity(intent, bundle);

                }
        );

        editEmail.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Validations.validateEmail(editEmail, btnUpdate);
                return false;
            }
        });

        btnDelete.setOnClickListener(view -> {
            try(DatabaseHelper db = new DatabaseHelper(getApplicationContext())){
                db.delete(
                        ID
                );
                Toast.makeText(getApplicationContext(), "DELETE OK",
                        Toast.LENGTH_LONG).show();
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }
            BackToMain();
        });

        btnUpdate.setOnClickListener(view -> {
            try(DatabaseHelper db = new DatabaseHelper(getApplicationContext())){
                db.update(
                        editName.getText().toString(),
                        editPhone.getText().toString(),
                        editEmail.getText().toString(),
                        editAddress.getText().toString(),
                        editProduct.getText().toString(),
                        editPrice.getText().toString(),
                        ID
                );
                Toast.makeText(getApplicationContext(), "UPDATE OK",
                        Toast.LENGTH_LONG).show();
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }
            BackToMain();


        });


    }
    private void BackToMain(){
        finishActivity(201);
        Intent intent = new Intent(UpdateDelete.this, MainActivity.class);
        startActivity(intent);
    }
}