package hwardak.shiftlog;


import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * This activity is where the employees are added, deleted, or viewed to or from the application.
 */
public class EmployeeOptionsActivity extends AppCompatActivity {

    EmployeeDataAccess employeeDataAccess = new EmployeeDataAccess(this);
    int ListViewPosition;
    int id;
    String name;
    Button deleteEmployeeButton;
    EditText employeeIdEditText;
    EditText employeeNameEditText;
    ListView listView;
    ListAdapter listAdapter;
    ArrayList<String> employeeList;
    View rowView;
    View previouslySelectedView;
    LinearLayout addEmployeeLayout;


    /**
     * Creates activity and invokes methods to instantiate views.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_options);
        updateListView();
        instantiateViews();
        instantiateListViewClickListener();
    }

    /**
     * On click listener on the ListView of employees. If an item in the ListView is clicked, it is
     * highlighted, and the previous selection is un-highlighted. The first time an item is selected
     * the deleteEmployeeButton is enabaled.
     */
    private void instantiateListViewClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                deleteEmployeeButton.setEnabled(true);
                rowView = view;
                ListViewPosition = position;
                //If there is a previously selected view in the list, it will be un-highlighted when
                //a new view within the list is selected.
                if (previouslySelectedView != null) {
                    previouslySelectedView.setBackgroundColor(0);
                    previouslySelectedView = view;
                } else {
                    previouslySelectedView = view;
                }
                //Clicked view in list will be highlighted. Light blue
                view.setBackgroundColor(Color.parseColor("#66ccff"));
            }
        });
    }

    /**
     * All views in this activity will be instantiated within this method.
     */
    private void instantiateViews() {
        addEmployeeLayout = (LinearLayout) findViewById(R.id.employeeOptionsAddEmployeeLinearLayout);
        deleteEmployeeButton = (Button) findViewById(R.id.deleteEmployeeButton);
        employeeIdEditText = (EditText) findViewById(R.id.employeeOptionsIdEditText);
        employeeNameEditText = (EditText) findViewById(R.id.employeeOptionsNameEditText);
    }


    /**
     * Button to delete employees from the list. Invokes the deleteEmployee() from the employee data
     * access layer.
     * @param view The item in the ListView that is selected.
     */
    public void onDeleteButtonClick(View view) {
        //User is prompted to confirm the deletion of the selected employee.
        new AlertDialog.Builder(this)
                .setTitle("Delete User")
                .setMessage("Confirm delete user \"" + employeeList.get(ListViewPosition).substring(0, employeeList.get(ListViewPosition).indexOf(" ")) + "\"")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //Retrives the user id, which is the from index 0 to the index of  " " (space).
                        employeeDataAccess.deleteEmployee(employeeList.get(ListViewPosition)
                                .substring(0, employeeList.get(ListViewPosition).indexOf(" ")));

                        //The row index that was just deleted is un-highlighted.
                        rowView.setBackgroundColor(0);
                        previouslySelectedView = null;

                        //Update the ListView with the new list view.
                        ((BaseAdapter) listAdapter).notifyDataSetChanged();
                        updateListView();

                        //Disable delete button since there is no longer a selection.
                        deleteEmployeeButton.setEnabled(false);
                    }
                })

                //Do nothing if the no if user wishes to NOT delete the selection.
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Refresh the listview.
     */
    private void updateListView() {
        employeeList = employeeDataAccess.getEmployeeList();
        listAdapter = new ArrayAdapter<>(this, R.layout.employee_listview_row, R.id.listViewRow, employeeList);
        listView = (ListView) findViewById(R.id.employeeListView);
        listView.setAdapter(listAdapter);

    }


    /**
     * On click to add new employee to list.
     * @param view
     */
    public void addEmployeeButtonOnClick(View view) {
        //Check if the name and Id in the editTexts are acceptable.
        if (isNameAndIdValid()) {
            //Clear fields and reset hints.
            employeeIdEditText.setText("");
            employeeIdEditText.setHint("Enter Id...");
            employeeNameEditText.setText("");

            //Reset hint color.
            employeeIdEditText.setHintTextColor(Color.parseColor("#8FF742"));
            employeeNameEditText.setHintTextColor(Color.parseColor("#8FF742"));

            //Add the employee and their ID to the DB.
            employeeDataAccess.addEmployeeToTable(id, name);

            //Clear variables.
            id = 0;
            name = null;

            //Update listview to include newly added employee.
            updateListView();
            ((BaseAdapter) listAdapter).notifyDataSetChanged();
        }
    }

    /**
     * Check if the values in the employeeIdEditText and employeeNameEditText are acceptable.
     * If not the user is informed via highlighed hints.
     * @return
     */
    private boolean isNameAndIdValid() {

        //Check if the employeeIdEditText has a value. Returns false if not.
        if (employeeIdEditText.getText().toString().trim().length() == 0) {
            employeeIdEditText.setHintTextColor(Color.parseColor("RED"));
            return false;
        }

        //Check if the employeeNameEditText has a value. Returns false if not.
        if (employeeNameEditText.getText().toString().trim().length() == 0) {
            employeeNameEditText.setHintTextColor(Color.parseColor("RED"));
            return false;
        }


        //Check if the employee id already already exists in the employee table.
        if(employeeDataAccess.doesEmployeeExist(Integer.parseInt(employeeIdEditText.getText().toString().trim()))) {
            employeeIdEditText.setText("");
            employeeIdEditText.setHint("Choose another ID");
            employeeIdEditText.setHintTextColor(Color.parseColor("RED"));
            return false;
        }


        //If this method has not returned false yet, below variables are assigned accordingly.
        id = Integer.parseInt(employeeIdEditText.getText().toString().trim());
        name = employeeNameEditText.getText().toString();

        //If all EditText entries are valid...
        return true;
    }
}



