package com.example.lab2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class order_fragment extends Fragment {

    EditText edit_name;
    EditText edit_phone_number;
    EditText edit_address;
    CheckBox check_box;
    TextView chosen_pizza_text;
    RadioGroup radio_group;

    DialogFragment dialogFragment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.order_fragment, null);

        edit_name = v.findViewById(R.id.edit_name);
        edit_phone_number = v.findViewById(R.id.edit_phone_number);
        edit_address = v.findViewById(R.id.edit_address);
        check_box = v.findViewById(R.id.checkBox);
        chosen_pizza_text = v.findViewById(R.id.chosen_pizza_text);
        radio_group = v.findViewById(R.id.radioGroup);

        Button choose_pizza_button = v.findViewById(R.id.choose_pizza_button);
        choose_pizza_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                FragmentManager manager = getFragmentManager();
                SingleChoiceDialogFragment dialog = new SingleChoiceDialogFragment();

                Bundle bundle = new Bundle();
                bundle.putStringArrayList(SingleChoiceDialogFragment.DATA, MainActivity.pizzas);
                bundle.putInt(SingleChoiceDialogFragment.SELECTED, -1);
                dialog.setArguments(bundle);
                dialog.show(manager, "Dialog");
            }
        });

        Button order_button = v.findViewById(R.id.order_button);
        order_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int radioId = radio_group.getCheckedRadioButtonId();
                if(chosen_pizza_text.getText() == "")
                {
                    Toast.makeText(getActivity(), "Choose pizza first", Toast.LENGTH_SHORT).show();
                }
                else if(radioId == -1)
                {
                    Toast.makeText(getActivity(), "Choose size first", Toast.LENGTH_SHORT).show();
                }
                else if(edit_name.getText().toString().equals("") || edit_phone_number.getText().toString().equals("") || edit_address.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(), "You have to fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    RadioButton radioButton = getActivity().findViewById(radioId);

                    String borders = check_box.isChecked() ? "Yes"  : "No";

                    int price = MainActivity.CountPrice(radioButton.getText().toString(), borders);

                    String str = "Your name: " + edit_name.getText() +
                            "\nYour phone: " + edit_phone_number.getText() +
                            "\nYour address: " + edit_address.getText() +
                            "\nPizza: " + chosen_pizza_text.getText() +
                            "\nSize: " + radioButton.getText() +
                            "\nCheese Borders: " + borders +
                            "\nTotal price: " + price + "$";

                    dialogFragment = new order_dialog();

                    Bundle bundle = new Bundle();
                    bundle.putString("info", str);
                    dialogFragment.setArguments(bundle);

                    dialogFragment.show(getFragmentManager(), "dialogFragment");
                }
            }
        });

        return v;
    }
}
