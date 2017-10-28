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

public class EmployeeOptions extends AppCompatActivity {

    EmployeeDataAccess employeeDataAccess = new EmployeeDataAccess(this);

    Button deleteEmployeeButton;
    ListView listView;
    ListAdapter listAdapter;
    ArrayList<String> employeeList;
    int ListViewPosition;
    View rowView;
    View previouslySelectedView;
    LinearLayout addEmployeeLayout;
    EditText employeeIdEditText;
    EditText employeeNameEditText;
    int id;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_options);

//        getEmployeeList();
        updateListView();

        addEmployeeLayout = (LinearLayout) findViewById(R.id.employeeOptionsAddEmployeeLinearLayout);
        deleteEmployeeButton = (Button) findViewById(R.id.deleteEmployeeButton);
        employeeIdEditText = (EditText) findViewById(R.id.employeeOptionsIdEditText);
        employeeNameEditText = (EditText) findViewById(R.id.employeeOptionsNameEditText);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                deleteEmployeeButton.setEnabled(true);
                rowView = view;
                ListViewPosition = position;
                if (previouslySelectedView != null) {
                    previouslySelectedView.setBackgroundColor(0);
                    previouslySelectedView = view;
                } else {
                    previouslySelectedView = view;

                }

                view.setBackgroundColor(Color.parseColor("#66ccff"));


            }
        });


    }

    public void onDeleteButtonClick(View view) {

        new AlertDialog.Builder(this)
                .setTitle("Delete User")
                .setMessage("Confirm delete user \"" + employeeList.get(ListViewPosition).substring(0, employeeList.get(ListViewPosition).indexOf(" ")) + "\"")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //Retrives the user id, which is the from index 0 to the index of  " " (space).
                        employeeDataAccess.deleteEmployee(employeeList.get(ListViewPosition)
                                .substring(0, employeeList.get(ListViewPosition).indexOf(" ")));

                        rowView.setBackgroundColor(0);
                        ((BaseAdapter) listAdapter).notifyDataSetChanged();

                        previouslySelectedView = null;


                        updateListView();
                        deleteEmployeeButton.setEnabled(false);

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }

    private void updateListView() {
        employeeList = employeeDataAccess.getEmployeeList();
        listAdapter = new ArrayAdapter<>(this, R.layout.employee_listview_row, R.id.listViewRow, employeeList);
        listView = (ListView) findViewById(R.id.employeeListView);
        listView.setAdapter(listAdapter);

    }

    private ArrayList<String> getEmployeeList() {
        employeeList = employeeDataAccess.getEmployeeList();
        return employeeList;
    }


    public void addEmployeeButtonOnClick(View view) {


        if (validateIdAndName()) {
            employeeIdEditText.setText("");
            employeeIdEditText.setHint("Enter Id...");
            employeeNameEditText.setText("");

            //Reset hint color.
            employeeIdEditText.setHintTextColor(Color.parseColor("#8FF742"));
            employeeNameEditText.setHintTextColor(Color.parseColor("#8FF742"));

            employeeDataAccess.addEmployeeToTable(id, name);

            id = 0;
            name = null;

            updateListView();
            ((BaseAdapter) listAdapter).notifyDataSetChanged();

        }
    }

    private boolean validateIdAndName() {

        if (employeeIdEditText.getText().toString().length() == 0) {
            employeeIdEditText.setHintTextColor(Color.parseColor("RED"));
            return false;
        }
        if (employeeNameEditText.getText().toString().length() == 0) {
            employeeNameEditText.setHintTextColor(Color.parseColor("RED"));
            return false;
        }

        id = Integer.parseInt(employeeIdEditText.getText().toString());
        name = employeeNameEditText.getText().toString();

        if(employeeDataAccess.doesEmployeeExist(id)) {
            employeeIdEditText.setText("");
            employeeIdEditText.setHint("Choose another ID");
            employeeIdEditText.setHintTextColor(Color.parseColor("RED"));
            return false;
        }

        return true;
    }
}



