package pl.wicher.easiestchecklist.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pl.wicher.easiestchecklist.CheckListActivity;
import pl.wicher.easiestchecklist.Model.CheckItem;
import pl.wicher.easiestchecklist.R;
import pl.wicher.easiestchecklist.Repo.CheckItemRepo;

public class CheckListItemAdapter extends BaseAdapter {

    private ArrayList<CheckItem> checkItems;
    private Context context;
    private final CheckItemRepo checkItemRepo = new CheckItemRepo();

    public CheckListItemAdapter(ArrayList<CheckItem> checkItems, Context context){
        this.checkItems = checkItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return checkItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try {
            convertView = li.inflate(R.layout.checklist_item_adapter, null);
            final TextView name = convertView.findViewById(R.id.item_name);
            final CheckBox checkBox = convertView.findViewById(R.id.item_checkbox);
            final ImageView delete = convertView.findViewById(R.id.delete);

            name.setText(checkItems.get(position).getTitle());

            if(checkItems.get(position).isChecked() == 1)
                checkBox.setChecked(true);
            else
                checkBox.setChecked(false);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(position);
                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox.setChecked(!checkBox.isChecked());
                }
            });

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                        checkItemRepo.updateItem(checkItems.get(position).getCheckItemId(),1);
                    else
                        checkItemRepo.updateItem(checkItems.get(position).getCheckItemId(),0);
                }
            });

            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    deleteItem(position);
                    return false;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private void deleteItem(final int checkItemPosition)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.delete_item));
        builder.setMessage(context.getResources().getString(R.string.delete_item_confirm));
        builder.setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkItemRepo.deleteCheckItem(checkItems.get(checkItemPosition).getCheckItemId());
                Intent intent = new Intent(context, CheckListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                context.startActivity(intent);
                dialog.cancel();
            }
        });
        builder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}