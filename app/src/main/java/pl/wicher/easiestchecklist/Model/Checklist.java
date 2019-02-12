package pl.wicher.easiestchecklist.Model;

public class Checklist {
    public static final String TAG = Checklist.class.getSimpleName();
    public static final String TABLE = "Checklist";
    // Labels Table Columns names
    public static final String KEY_ChecklistId = "ChecklistId";
    public static final String KEY_Name = "Name";
    public static final String KEY_Icon = "Icon";

    private String checklistId;
    private String name;
    private int icon;


    public String getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(String checklistId) {
        this.checklistId = checklistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
