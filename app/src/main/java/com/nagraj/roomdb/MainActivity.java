package com.nagraj.roomdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button insert;
    private Button selectAll;
    private Button delete;
    private Button selectById;
    private Button selectByName;
    private EditText firstName;
    private EditText lastName;
    private EditText id;
    private EditText age;
    private RecyclerView recyclerView;
    private List<User> users;
    public static UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insert = findViewById(R.id.insert);
        selectAll = findViewById(R.id.selectAll);
        delete = findViewById(R.id.delete);
        selectById = findViewById(R.id.selectById);
        selectByName = findViewById(R.id.selectByName);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        id = findViewById(R.id.id);
        age = findViewById(R.id.age);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "Peoples").allowMainThreadQueries().build();

        insertSection();
        selectAllSection();
        deleteSection();
        selectByNameSection();
        selectByIdSection();
        reload();
    }

    public void insertSection() {
        insert.setOnClickListener(view -> {
            try {
                User user1 = new User();
                user1.setFirstName(firstName.getText().toString());
                user1.setLastName(lastName.getText().toString());
                user1.setUid(Integer.parseInt(id.getText().toString()));
                user1.setAge(Integer.parseInt(age.getText().toString()));
                userDatabase.userDao().insertAll(user1);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Not Inserted", Toast.LENGTH_LONG).show();

            } finally {
                reload();

            }

        });


    }

    public void selectAllSection() {
        selectAll.setOnClickListener(view -> reload());
    }

    public void deleteSection() {
        delete.setOnClickListener(view -> {
            try {
                userDatabase.userDao().delete(Integer.parseInt(id.getText().toString()));
                reload();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Not Deleted", Toast.LENGTH_LONG);
            }

        });

    }

    public void selectByNameSection() {
        selectByName.setOnClickListener(view -> {
            try {
                List<User> users = userDatabase.userDao().findByName(firstName.getText().toString(), lastName.getText().toString());
                recyclerView.setAdapter(new Recycle(users));
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Not Inserted", Toast.LENGTH_LONG).show();

            } finally {

            }

        });

    }

    public void selectByIdSection() {
        selectById.setOnClickListener(view -> {
            try {
                List<User> user1 = MainActivity.userDatabase.userDao().loadAllByIds(Integer.parseInt(id.getText().toString()));
                recyclerView.setAdapter(new Recycle(user1));

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Not Inserted", Toast.LENGTH_LONG).show();

            } finally {

            }
        });


    }

    public void reload() {
        List<User> users = MainActivity.userDatabase.userDao().getAll();
        recyclerView.setAdapter(new Recycle(users));
    }

}
