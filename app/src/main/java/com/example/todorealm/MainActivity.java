package com.example.todorealm;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextAge;
    private Button buttonSave;
    private RecyclerView recyclerView;
    private DataAdapter adapter;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        buttonSave = findViewById(R.id.buttonSave);
        recyclerView = findViewById(R.id.recyclerView);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToRealmAsync();
            }
        });

        realm = Realm.getDefaultInstance();
        readDataFromRealm();
    }

    private void updateAdapter(RealmResults<MyDataModel> newData) {
        adapter.updateData(newData);
    }

    private void readDataFromRealm() {
        RealmResults<MyDataModel> newData = realm.where(MyDataModel.class).findAll();
        adapter = new DataAdapter(newData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void updateDataInRealm(final long dataId, final String newName, final int newAge) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MyDataModel dataModel = realm.where(MyDataModel.class).equalTo("id", dataId).findFirst();
                if (dataModel != null) {
                    dataModel.setName(newName);
                    dataModel.setAge(newAge);
                }
            }
        });
    }

    private void deleteDataFromRealm(final long dataId) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MyDataModel dataModel = realm.where(MyDataModel.class).equalTo("id", dataId).findFirst();
                if (dataModel != null) {
                    dataModel.deleteFromRealm();
                }
            }
        });
    }

    private void saveDataToRealmAsync() {
        final String name = editTextName.getText().toString();
        final int age = Integer.parseInt(editTextAge.getText().toString());

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MyDataModel dataModel = realm.createObject(MyDataModel.class, System.currentTimeMillis());
                dataModel.setName(name);
                dataModel.setAge(age);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                readDataFromRealm(); // Refresh the data after adding
                editTextName.setText("");
                editTextAge.setText("");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Handle error
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }
}
