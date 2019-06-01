package com.example.shrijhajhra.careergo;
import java.io.Serializable;
import java.util.*;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class testActivity extends AppCompatActivity {


    // public String[] que = {"ARTS","AVIATION AND AEROSPACE"};
    //String[] ans= {"ARTS","AVIATION AND AEROSPACE"};

    int que_no;
    boolean answerd;
    public static ArrayList<String> selectedAnswers;
    Handler handler = new Handler();
    int status = 0;
    Button button;
    ProgressDialog progressdialog;
    List<String> ans;
    ListView listView;
    List<String> category_option;
    Listadapter adapter;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference("test");
    ArrayList<test_item> items = new ArrayList<>();
    Button submit,next;
    RadioButton op1, op2, op3;
    RadioGroup radioGroup;
    TextView question;
    private  Map<String,Integer> testResult ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        CreateProgressDialog();

        ShowProgressDialog();
        question = (TextView) findViewById(R.id.question);
        radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
        op1 = (RadioButton) findViewById(R.id.op1);
        op2 = (RadioButton) findViewById(R.id.op2);
        op3 = (RadioButton) findViewById(R.id.op3);
        next= (Button)findViewById(R.id.next);

        //listView = (ListView) findViewById(R.id.listview);


        //to add a button at bottom of list
        //FrameLayout footerLayout = (FrameLayout) getLayoutInflater().inflate(R.layout.footerview, null);
        // Button btnPostYourEnquiry = (Button) footerLayout.findViewById(R.id.btnGetMoreResults);
        // listView.addFooterView(footerLayout);

        //set adapter
        // adapter = new Listadapter(this, items);
        //listView.setAdapter(adapter);
        //listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        // Toast.makeText(testActivity.this, "before :  " + items.size(), Toast.LENGTH_SHORT).show();


        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(testActivity.this, "before :  " + items.size(), Toast.LENGTH_SHORT).show();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    test_item item = new test_item();
                    item.setQuestion(snapshot.child("question").getValue(String.class));
                    String ans1 = snapshot.child("ans1").getValue(String.class);
                    String ans2 = snapshot.child("ans2").getValue(String.class);
                    String ans3 = snapshot.child("ans3").getValue(String.class);
                    ans = new ArrayList<>();
                    if (ans1 != null)
                        ans.add(ans1);
                    if (ans2 != null)
                        ans.add(ans2);
                    if (ans3 != null)
                        ans.add(ans3);
                    item.setAns_list(ans);

                    String catg1 = snapshot.child("catg1").getValue(String.class);
                    String catg2 = snapshot.child("catg2").getValue(String.class);
                    String catg3 = snapshot.child("catg3").getValue(String.class);
                    category_option  = new ArrayList<>();
                    if(catg1!=null)
                        category_option.add(catg1);
                    if(catg2!=null)
                        category_option.add(catg2);
                    if(catg3!=null)
                        category_option.add(catg3);
                    // Toast.makeText(testActivity.this, "datasnapshot :  " + snapshot, Toast.LENGTH_SHORT).show();
                    item.setcategory_option(category_option);
                   // Toast.makeText(testActivity.this, "option :  " + category_option.size(), Toast.LENGTH_SHORT).show();
                    items.add(item);
                }
                testResult= new HashMap<String, Integer>();
                testResult.clear();
                selectedAnswers = new ArrayList<>();
                selectedAnswers.clear();
                for (int i = 0; i < items.size(); i++) {
                    //selectedAnswers.add("Not Attempted");
                }
                updateQuestion();
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!answerd) {
                            if (op1.isChecked() || op2.isChecked() || op3.isChecked()) {
                                answerd = true;
                                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                                View radioButton = radioGroup.findViewById(radioButtonID);
                                String idx = String.valueOf(radioGroup.indexOfChild(radioButton));
                                //RadioButton radioButton1 = findViewById(radioGroup.getCheckedRadioButtonId());
                               // Toast.makeText(testActivity.this,idx + (String)radioButton1.getText(), Toast.LENGTH_SHORT).show();

                                selectedAnswers.add(que_no-1, idx );
                                updateQuestion();
                            } else {
                                Toast.makeText(testActivity.this, "Please select an option  ", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            if(next.getText().equals("Submit"))
                            {
                                Toast.makeText(testActivity.this, "Submitting test ..."+selectedAnswers.size(), Toast.LENGTH_SHORT).show();

                                //get category for each response
                                for(int i=0;i<selectedAnswers.size();i++)
                                {
                                    int j=0;
                                    //category for selected option for ith item
                                    String catg = items.get(i).getCatg_atIndex(Integer.valueOf(selectedAnswers.get(i)));

                                        Integer prev_count = (Integer) testResult.get(catg);
                                         if(prev_count==null)
                                         {
                                            prev_count = 1;
                                         }
                                         else {
                                             prev_count ++;
                                         }

                                    testResult.put(catg,prev_count);
                                   // Toast.makeText(testActivity.this, selectedAnswers.get(i), Toast.LENGTH_SHORT).show();

                                }
                                //sort map
                                 //testResult = sortByValue(testResult);

                              /*  for (Map.Entry<String,Integer> entry : testResult.entrySet()) {
                                    String key = entry.getKey();
                                    int value = entry.getValue();
                                    Toast.makeText(testActivity.this, key + " "+ value, Toast.LENGTH_SHORT).show();
                                    // do stuff
                                }*/

                                Intent intent = new Intent(getApplicationContext(),test_result.class);
                                intent.putExtra("map", (Serializable) testResult);
                                startActivity(intent);
                            }
                            else{
                                updateQuestion();
                            }

                        }
                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Toast.makeText(testActivity.this, "after dref :  " + items.size(), Toast.LENGTH_SHORT).show();

    }

    private Map<String,Integer> sortByValue(Map<String, Integer> testResult) {
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(testResult.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        /*
        //classic iterator example
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }*/


        return sortedMap;
    }


    private void updateQuestion() {



        if (que_no < items.size()) {
            radioGroup.clearCheck();
            int j = que_no+1;
            question.setText("Q"+j+": "+items.get(que_no).getQuestion());
            int i=0;
            op1.setText(items.get(que_no).getans_atIndex(i++));
            op2.setText(items.get(que_no).getans_atIndex(i++));
            op3.setText(items.get(que_no).getans_atIndex(i++));
            que_no++;
            answerd = false;
           // radioGroup.clearCheck();
        } else {
            next.setText("Submit");

        }
    }


        //Toast.makeText(testActivity.this, "after f :  " + items.size(), Toast.LENGTH_SHORT).show();


      /*  submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int child=listView.getChildCount();
                for(int i=0;i<child;i++) {
                    View rgg=listView.getChildAt(i);

                    RadioGroup radioGroup = (RadioGroup) rgg.findViewById(R.id.myRadioGroup);

                    int selectedId=radioGroup.getCheckedRadioButtonId();

                    RadioButton radioButton = (RadioButton) rgg.findViewById(selectedId);
                    Toast.makeText(testActivity.this, "text :  " + radioButton.getText(), Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnPostYourEnquiry.setOnClickListener(new View.OnClickListener() {
            String message = new String();


            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(testActivity.this);
                builder.setMessage("Do You Want To Submit The Test");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //FirebaseAuth.getInstance().signOut();
                        Toast.makeText(testActivity.this, "Submiting test..", Toast.LENGTH_SHORT).show();
                        // Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                        // finish();
                        //startActivity(intent);
                        for (int i = 0; i < listView.getAdapter().getCount() - 1; i++) {
                            message = message + (i + 1) + " " + selectedAnswers.get(i) + "\n";
                            String index = selectedAnswers.get(i);
                            //Toast.makeText(getApplicationContext(), index, Toast.LENGTH_SHORT).show();
                        }

                        //test analysis

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),test_result.class);
                        startActivity(intent);
                    }


                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }


        });
    }*/

    public void onBackPressed() {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Warning");
        alertdialog.setMessage("Are you sure you Want to exit the test???");
        alertdialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        AlertDialog alert = alertdialog.create();
        alertdialog.show();
    }




    public void CreateProgressDialog() {

        progressdialog = new ProgressDialog(testActivity.this);

        progressdialog.setIndeterminate(false);

        progressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        progressdialog.setCancelable(true);

        progressdialog.setMax(100);

        progressdialog.show();

    }

    public void ShowProgressDialog() {
        status = 0;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (status < 100) {

                    status += 1;

                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            progressdialog.setProgress(status);

                            if (status == 100) {

                                progressdialog.dismiss();
                            }
                        }
                    });
                }
            }
        }).start();

    }


}
