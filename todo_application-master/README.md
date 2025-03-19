RecyclerView and Adapter Implementation
This project demonstrates the use of RecyclerView in Android development to display a list of TODO items. Here’s how it works:

RecyclerView Setup: 
The RecyclerView is initialized in the main activity (MainActivity.kt). It uses a LinearLayoutManager to arrange items vertically.

Adapter (TodoAdapter.kt): 
The TodoAdapter class extends RecyclerView.Adapter and manages the data binding for each TODO item. It inflates the item_todo.xml layout for each item in the list.

Data Handling: 
TODO items are stored in a MutableList in the MainActivity.kt. The TodoAdapter receives this list and binds each item’s data to the corresponding views in the RecyclerView.

Adding New Items: 
New TODO items can be added by entering text in the EditText and clicking the "Add" button (btnTodo). This action updates the data list and notifies the adapter to reflect the changes in the RecyclerView.
