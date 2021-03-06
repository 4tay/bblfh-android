package a4tay.xyz.brokebandslookingforhome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import a4tay.xyz.brokebandslookingforhome.Util.BCrypt;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.LoginCreate;
import a4tay.xyz.brokebandslookingforhome.Util.QueryUtils;

import static a4tay.xyz.brokebandslookingforhome.TabActivity.loggedIn;

/**
 * Created by johnkonderla on 3/22/17.
 */

public class CreateUserFrag extends Fragment {

    private EditText userName;
    private EditText email;
    private EditText passOne;
    private EditText passTwo;
    private Button submitFan;
    private JSONObject userInfo;
    private String url = "http://dev.4tay.xyz:4567/addFan?";
    private String submittedUN;
    private String submittedEM;
    private String submittedPW1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.create_user, container, false);
        userName = (EditText) rootView.findViewById(R.id.et_fan_name);
        email = (EditText) rootView.findViewById(R.id.et_fan_email);
        passOne = (EditText) rootView.findViewById(R.id.et_fan_pass1);
        passTwo = (EditText) rootView.findViewById(R.id.et_fan_pass2);
        submitFan = (Button) rootView.findViewById(R.id.bt_submit_fan);


        submitFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submittedUN = QueryUtils.getText(userName);
                submittedEM = QueryUtils.getText(email);
                submittedPW1 = QueryUtils.getText(passOne);
                String submittedPW2 = QueryUtils.getText(passTwo);
                String paramUN = "fanName";
                String paramEM = "fanEmail";
                String paramPW = "fanPass";
                if (!submittedUN.equals("") && !submittedEM.equals("")) {
                    if (submittedPW1.equals(submittedPW2) && !submittedPW1.equals("")) {
                        userInfo = new JSONObject();
                        try {
                            userInfo.put(paramUN, submittedUN);
                            userInfo.put(paramEM, submittedEM);
                            userInfo.put(paramPW, submittedPW1);
                            new LoginCreate(getActivity()).execute(new String[]{url, userInfo.toString()});

                        } catch (JSONException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        Toast.makeText(getContext(), "password must match", Toast.LENGTH_LONG).show();
                        passOne.setError("Password is required");
                        passTwo.setError("Password must match");
                    }
                } else if (submittedUN.equals("")) {
                    userName.setError("Name is required");
                } else {
                    email.setError("Email is required");
                }


            }
        });

        return rootView;
    }
}
