package amaury.test1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment implements View.OnClickListener {

    public OptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_options, container, false);

        Button b;
        b = (Button)view.findViewById(R.id.button16);
        b.setOnClickListener(this);
        b = (Button)view.findViewById(R.id.button17);
        b.setOnClickListener(this);
        b = (Button)view.findViewById(R.id.button18);
        b.setOnClickListener(this);
        b = (Button)view.findViewById(R.id.button19);
        b.setOnClickListener(this);
        b = (Button)view.findViewById(R.id.button20);
        b.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button16:
                Toast.makeText(getActivity(), "Coucou", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button17:
                Toast.makeText(getActivity(), "Ca va", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button18:
                Toast.makeText(getActivity(), "bien ?", Toast.LENGTH_SHORT).show();
                break;
            default:
                Log.i("ERROR", "Unknown: " + view.getId());
                break;
        }
    }
}
