package com.amicus.testapk;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btAdd;
    Button btRead;
    Button btClear;
    Button btnDelID;

    Button btnSearch;

    EditText etName;
    EditText etEmail;
    EditText etPhone;
    EditText etID;

    AppDatabase db;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btAdd = findViewById(R.id.btnAdd);
        btRead = findViewById(R.id.btnRead);
        btClear = findViewById(R.id.btnClear);
        btnDelID = findViewById(R.id.btnId);
        btnSearch = findViewById(R.id.btnSearch);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone= findViewById(R.id.etPhone);
        etID= findViewById(R.id.etID);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "myDB").addMigrations(AppDatabase.MIGRATION_1_2).allowMainThreadQueries().build();
        userDao = db.userDao();

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();

                if (name.isEmpty())
                {
                    Log.d("SQL", "Enter name");
                    return;
                }

                if (email.isEmpty())
                {
                    Log.d("SQL", "Enter email");
                    return;
                }

                if (phone.isEmpty())
                {
                    Log.d("SQL", "Enter phone");
                    return;
                }

                User user = new User();
                user.name = name;
                user.email = email;
                user.phone = phone;

                long id = userDao.insert(user);

                Log.d("SQL", "Add user id " + id);

                etName.setText("");
                etEmail.setText("");
                etPhone.setText("");
            }
        });

        btRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<User> users = userDao.getAll();
                if (users.size() == 0) {
                    Log.d("SQL", "Empty");
                    return;
                }
                for (User user : users) {
                    Log.d("SQL", "ID " + user.id + ", name " + user.name + ", email " + user.email+", phone "+user.phone);
                }
            }
        });


        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count =userDao.clearAll();
                userDao.reset();
                Log.d("SQL", "Delete rows count "+ count);
            }
        });

        btnDelID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idText = etID.getText().toString();
                if (idText.isEmpty()){
                    Log.d("SQL","Enter id");
                    return;
                }
                int id = Integer.parseInt(idText);
                int deleteCount = userDao.deleteById(id);
                if (deleteCount>0){
                    Log.d("SQL","User deleted id "+id);
                }else {
                    Log.d("SQL","User with id "+id +" not found");
                }
                etID.setText("");
            }

        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = etName.getText().toString().trim();

                if (search.isEmpty())
                {
                    Log.d("SQL", "Enter name or email for search");
                    return;
                }

                List<User> foundUsers = userDao.search(search);

                if (foundUsers.size() == 0)
                {
                    Log.d("SQL", "No users found");
                    return;
                }

                for (User user : foundUsers)
                {
                    Log.d("SQL", "ID " + user.id +
                            ", name " + user.name +
                            ", email " + user.email +
                            ", phone " + user.phone);
                }
            }
        });
    }
}