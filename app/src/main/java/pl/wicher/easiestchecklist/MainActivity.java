package pl.wicher.easiestchecklist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.wicher.easiestchecklist.adapters.CheckListAdapter;
import pl.wicher.easiestchecklist.model.Checklist;
import pl.wicher.easiestchecklist.repo.ChecklistRepo;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private ListView checkListView;
    private TextView emptyListTextView;
    private FloatingActionButton floatingActionButton;

    private ChecklistRepo checklistRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        checklistRepo = new ChecklistRepo();

        checkListView = findViewById(R.id.checkListView);
        checkListView.setVisibility(View.INVISIBLE);
        emptyListTextView = findViewById(R.id.emptyList);
        floatingActionButton = findViewById(R.id.addCheckList);

        fabClickListener(floatingActionButton);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateListView();
    }

    private void fabClickListener(FloatingActionButton fab)
    {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.add_checklist, null);
                final EditText name = mView.findViewById(R.id.checklistName);
                final RadioGroup radioGroup = mView.findViewById(R.id.icon_group);
                mBuilder.setPositiveButton(getResources().getString(R.string.accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!name.getText().toString().equals(""))
                        {
                            Checklist checklist = new Checklist();
                            checklist.setName(name.getText().toString());
                            View rb = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                            if(rb != null)
                                checklist.setIcon(rb.getId());
                            int id = checklistRepo.insert(checklist);
                            Intent intent = new Intent(context, CheckListActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("checkListId", String.valueOf(id));
                            context.startActivity(intent);
                        }else {
                            Toast.makeText(context, getResources().getString(R.string.provide_name), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mBuilder.setNegativeButton(getResources().getString(R.string.cancel), null);
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
    }

    private void updateListView()
    {
        ArrayList<Checklist> checkLists = checklistRepo.getAllLists();
        if(checkLists.size() > 0) {
            emptyListTextView.setVisibility(View.GONE);
            checkListView.setVisibility(View.VISIBLE);
            checkListView.setAdapter(new CheckListAdapter(checkLists, context));
        } else {
            emptyListTextView.setVisibility(View.VISIBLE);
            checkListView.setVisibility(View.GONE);
        }
    }
}
