package pl.wicher.easiestchecklist.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pl.wicher.easiestchecklist.CheckListActivity;
import pl.wicher.easiestchecklist.MainActivity;
import pl.wicher.easiestchecklist.model.Checklist;
import pl.wicher.easiestchecklist.R;
import pl.wicher.easiestchecklist.repo.CheckItemRepo;
import pl.wicher.easiestchecklist.repo.ChecklistRepo;

public class CheckListAdapter extends BaseAdapter {

    private ArrayList<Checklist> checklist;
    private Context context;
    private final CheckItemRepo checkItemRepo = new CheckItemRepo();
    private final ChecklistRepo checklistRepo = new ChecklistRepo();

    public CheckListAdapter(ArrayList<Checklist> checklist, Context context){
        this.checklist = checklist;
        this.context = context;
    }

    @Override
    public int getCount() {
        return checklist.size();
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
            convertView = li.inflate(R.layout.checklist_adapter, null);
            ImageView image = convertView.findViewById(R.id.icon);
            TextView name = convertView.findViewById(R.id.name);
            TextView progress = convertView.findViewById(R.id.progress);

            switch (checklist.get(position).getIcon()) {
                case R.id.meeting:
                    image.setImageResource(R.drawable.ic_meeting);
                    break;
                case R.id.party:
                    image.setImageResource(R.drawable.ic_party);
                    break;
                case R.id.pet:
                    image.setImageResource(R.drawable.ic_pet);
                    break;
                case R.id.shop:
                    image.setImageResource(R.drawable.ic_shop);
                    break;
                case R.id.trip:
                    image.setImageResource(R.drawable.ic_trip);
                    break;
                default:
                    image.setImageResource(R.drawable.ic_default);
                    break;
            }

            name.setText(checklist.get(position).getName());

            int[] progressValue = checkItemRepo.countAllItemsById(checklist.get(position).getChecklistId());
            String pv = progressValue[0] + "/" + progressValue[1];
            progress.setText(pv);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CheckListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("checkListId", checklist.get(position).getChecklistId());
                    context.startActivity(intent);
                }
            });

            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(context.getResources().getString(R.string.delete_checklist));
                    builder.setMessage(context.getResources().getString(R.string.delete_confirm));
                    builder.setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            checklistRepo.deleteCheckList(checklist.get(position).getChecklistId());
                            Intent intent = new Intent(context, MainActivity.class);
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
                    return false;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
}