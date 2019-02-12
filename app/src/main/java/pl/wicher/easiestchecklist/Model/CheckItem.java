package pl.wicher.easiestchecklist.Model;

public class CheckItem {
    public static final String TAG = CheckItem.class.getSimpleName();
    public static final String TABLE = "CheckItem";
    // Labels Table Columns names
    public static final String KEY_CheckItemId = "CheckItemId";
    public static final String KEY_ChecklistId = "ChecklistId";
    public static final String KEY_Title = "Title";
    public static final String KEY_IsChecked = "IsChecked";
    public static final String KEY_ITEM_LIST= "Item_List";

    private String checkItemId;
    private String title;
    private String checkListId;
    private int isChecked;

    public String getCheckItemId() {
        return checkItemId;
    }

    public void setCheckItemId(String checkItemId) {
        this.checkItemId = checkItemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCheckListId() {
        return checkListId;
    }

    public void setCheckListId(String checkListId) {
        this.checkListId = checkListId;
    }

    public int isChecked() {
        return isChecked;
    }

    public void setChecked(int checked) {
        isChecked = checked;
    }
}
