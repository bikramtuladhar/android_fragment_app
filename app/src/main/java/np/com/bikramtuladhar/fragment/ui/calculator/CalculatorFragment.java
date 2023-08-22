package np.com.bikramtuladhar.fragment.ui.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import np.com.bikramtuladhar.fragment.R;
import np.com.bikramtuladhar.fragment.databinding.FragmentCalculatorBinding;

public class CalculatorFragment extends Fragment {

    private FragmentCalculatorBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TableLayout tableLayout = root.findViewById(R.id.tlCaculator);
        TextView expression = root.findViewById(R.id.expression);
        TextView result = root.findViewById(R.id.result);

        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            View row = tableLayout.getChildAt(i);
            if (row instanceof TableRow) {
                TableRow tableRow = (TableRow) row;
                for (int j = 0; j < tableRow.getChildCount(); j++) {
                    View view = tableRow.getChildAt(j);
                    if (view instanceof Button) {
                        Button button = (Button) view;

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String buttonText = ((Button) v).getText().toString();

                                if (buttonText.equals("CE") || buttonText.equals("C")) {
                                    expression.setText("");
                                    result.setText("");
                                    return;
                                }

                                if (buttonText.equals("Delete")) {
                                    String text = expression.getText().toString();
                                    if (text.length() > 0) {
                                        expression.setText(text.substring(0, text.length() - 1));
                                    }
                                    return;
                                }

                                if (buttonText.equals("=")) {
                                    ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
                                    double calResult = 0;

                                    try {
                                        calResult = (double) engine.eval(expression.getText().toString());
                                    } catch (Exception e) {
                                        result.setText("ERR");
                                    }

                                    result.setText(String.valueOf(calResult));
                                    return;
                                }

                                String exp = expression.getText() + buttonText;

                                expression.setText(exp);
                            }
                        });
                    }
                }
            }
        }


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}