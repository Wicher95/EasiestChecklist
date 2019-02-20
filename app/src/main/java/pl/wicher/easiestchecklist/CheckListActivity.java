package pl.wicher.easiestchecklist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.wicher.easiestchecklist.adapters.CheckListItemAdapter;
import pl.wicher.easiestchecklist.model.CheckItem;
import pl.wicher.easiestchecklist.repo.CheckItemRepo;
import pl.wicher.easiestchecklist.repo.ChecklistRepo;

public class CheckListActivity extends AppCompatActivity {

    private Context context;
    private ListView checkListItems;
    private TextView emptyListTextView;
    private FloatingActionButton floatingActionButton;

    private ChecklistRepo checklistRepo;
    private CheckItemRepo checkItemRepo;
    private String checkListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        checklistRepo = new ChecklistRepo();
        checkItemRepo = new CheckItemRepo();

        checkListId = getIntent().getStringExtra("checkListId");
        setTitle(checklistRepo.getCheckListName(checkListId));

        checkListItems = findViewById(R.id.checkListItems);
        checkListItems.setVisibility(View.INVISIBLE);
        emptyListTextView = findViewById(R.id.emptyItemList);
        floatingActionButton = findViewById(R.id.addCheckListItem);

        fabClickListener(floatingActionButton);
        updateListView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CheckListActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.add_checklist_item, null);
                final EditText name = mView.findViewById(R.id.checklistItemName);
                mBuilder.setPositiveButton(getResources().getString(R.string.accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!name.getText().toString().equals(""))
                        {
                            CheckItem checkItem = new CheckItem();
                            checkItem.setCheckListId(checkListId);
                            checkItem.setTitle(name.getText().toString());
                            checkItem.setChecked(0);
                            checkItemRepo.insert(checkItem);
                            updateListView();
                        } else {
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
        ArrayList<CheckItem> checkItems = checkItemRepo.getAllItemsById(checkListId);
        if(checkItems.size() > 0) {
            emptyListTextView.setVisibility(View.GONE);
            checkListItems.setVisibility(View.VISIBLE);
            checkListItems.setAdapter(new CheckListItemAdapter(checkItems, context));
        } else {
            emptyListTextView.setVisibility(View.VISIBLE);
            checkListItems.setVisibility(View.GONE);
        }
    }
}
