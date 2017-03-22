package com.vates.cycles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    List<Integer> listNumbers = new ArrayList<Integer>();

    @ViewById
    EditText et_number;

    @ViewById
    TextView tv_numbers;

    @ViewById
    ImageButton run;

    @ViewById
    TextView tv_result;

    @Click(R.id.reset)
    void reset() {
        tv_numbers.setText("");
        tv_result.setText("");
        listNumbers = new ArrayList<Integer>();
    }

    @Click(R.id.add)
    void addNumber() {
        _add();
    }


    @EditorAction(R.id.et_number)
    void onEditorActionsOnHelloTextView(TextView hello, int actionId, KeyEvent keyEvent) {

        if (actionId == EditorInfo.IME_ACTION_DONE) {
            _add();
        }
    }

    private void _add() {
        String snum = et_number.getText().toString().trim();

        if (snum.length() > 0) {

            listNumbers.add(Integer.valueOf(snum));

            String value = tv_numbers.getText().toString() + " " + snum;
            tv_numbers.setText(value);

            et_number.setText("");
        }
    }

    List<Integer> ready = new ArrayList<Integer>();

    @Click(R.id.run)
    void doCycle() {
        cycle1();
    }

    public int getInt(int index) {
        return listNumbers.get(index).intValue();
    }

    public boolean existIndex(int index) {
        for (Integer i : ready) {
            if (index == i.intValue()) {
                return true;
            }
        }
        return false;
    }

    public void addReady(int index) {
        if (!existIndex(index)) {
            ready.add(new Integer(index));
        }
    }

    public String printReady() {

        String value = "";
        for (Integer i : ready) {
            value = value + " " + String.valueOf(i);
        }
        return value;
    }


    public void cycle1() {
        ready = new ArrayList<Integer>();
        int num = 0;
        String value = "";
        for (int x = 0; x < listNumbers.size(); x++) {
            if (!existIndex(listNumbers.get(x))) {
                value = "";
                num = listNumbers.get(x);
                for (int y = x + 1; y < listNumbers.size(); y++) {
                    if (!existIndex(y + 1)) {
                        if (num == getInt(y)) {
                            value = value + String.valueOf(num);
                            addReady(listNumbers.get(x));
                            addReady(listNumbers.get(y));
                            if (y + 1 != listNumbers.size()) {
                                if (!existIndex(y + 1)) {
                                    int t = 1;
                                    boolean same = true;
                                    while (same) {
                                        int ta = x + t;
                                        int tb = y + t;
                                        if (!(ta >= listNumbers.size() || tb >= listNumbers.size())) {
                                            if (getInt(ta) == getInt(tb)) {
                                                int temp = x + t;
                                                addReady(listNumbers.get(temp));
                                                temp = y + t;
                                                addReady(listNumbers.get(temp));
                                                value = value + String.valueOf(getInt(x + t));
                                                t++;
                                            } else {
                                                same = false;
                                            }
                                        } else {
                                            same = false;
                                        }
                                    }

                                    if (value != "") {
                                        tv_result.setText(tv_result.getText() + " " + value);
                                        value = "";
                                    }

                                } else {
                                    tv_result.setText(tv_result.getText() + " " + value);
                                    value = "";
                                }
                            }
                        }
                    }

                    if (value != "") {
                        tv_result.setText(tv_result.getText() + " " + value);
                    }

                }
            }
        }

        tv_result.setText(printReady());

        listNumbers = new ArrayList<Integer>();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
