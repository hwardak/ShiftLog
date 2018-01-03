# ShiftLog 
Current version in field testing. New versiosn currently in development. 

Keeps track of shift details replacing the need for filling shift forms.
Originally developed for gas stations and convienence stores, but ultimately can be used by any small business. 
Intended to keep track of employee hours, payroll, punctuality, till counts, and specific inventory items per shift.

This application is to run on a location specific, dedicated Android device. 
Users are to create their account via the Employees activity. From within the same Employees activity, users can be view and deleted.
User must select their own user ID. The employee ID is a unique identifier. No two employees can share the same ID.

From the Main activity users can start logging their shifts once they have entered their ID in the only available EditText. Once they enter thier ID, the shift logging form activity will be brought into focus. The employee's name and date are automatically collected and set into their respective TextViews. Both the actual time and declared times are logged and saved, this is to provide insight into each employee's punctuality. Remaining fields are left for the employee to complete. Certain fields, such as "Starting till amount" cannot be left empty.
Some fields cannot be editted until the shift has started, other fields cannot be changed once the shift is in progress.

From within the Shift Log activity, managers can view the history of shifts and their details in multiple view formats. 
The default view will display every shift worked, its date, employee name, start time and end time. In the default view, managers can select to filter results by specifiic employee, month and/or year.
Shifts can be viewed in detailed per day by selecting the "Detailed day" option via the "Display by" spinner. In this view, the most recent day is displayed, with the details of each shift that was logged that day. Click the "Previous" and "Next" will display the next of previous day of shifts. 


