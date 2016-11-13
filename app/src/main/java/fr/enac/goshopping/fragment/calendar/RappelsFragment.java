package fr.enac.goshopping.fragment.calendar;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

import fr.enac.goshopping.R;
import fr.enac.goshopping.database.GoShoppingDBHelper;
import fr.enac.goshopping.notification.MyAlarmIntentService;
import fr.enac.goshopping.objects.ShoppingListObject;

import static android.content.Context.ALARM_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RappelsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RappelsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RappelsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String daySelected;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    private OnFragmentInteractionListener mListener;
    private FloatingActionButton fab;

    public RappelsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param daySelected Parameter 1.
     * @return A new instance of fragment RappelsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RappelsFragment newInstance(String daySelected) {
        RappelsFragment fragment = new RappelsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, daySelected);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            daySelected = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rappels, container, false);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        final TimePicker t = (TimePicker) v.findViewById(R.id.timePicker);
        final TextView tjour=(TextView) v.findViewById(R.id.Jour);
        tjour.setText(daySelected);
        t.setVisibility(View.GONE);
        t.setIs24HourView(true);
        ListView calendar_list = (ListView) v.findViewById(R.id.manage_rappel_list);
        ArrayList<ShoppingListObject> list = new GoShoppingDBHelper(getContext()).getShoppingLists();
        ArrayList<String> listName = new ArrayList<>();
        for (ShoppingListObject s : list) {
            listName.add(s.getList_name());
        }
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listName);
        calendar_list.setAdapter(adapter);
        calendar_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                t.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();

                //cal.set(t.getHour(),t.getMinute(),00);
                cal.set(Calendar.HOUR_OF_DAY, t.getHour());
                cal.set(Calendar.MINUTE, t.getMinute());

                setAlarm(cal);


            }
        });
        return v;
    }

    private void setAlarm(Calendar targetCal)
    {   Intent alarmIntent = new Intent(getActivity(), MyAlarmIntentService.class);
        PendingIntent sender = PendingIntent.getService(getActivity(), 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), sender);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    // handle back button's click listener
                    getFragmentManager().popBackStack("CallendarFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });
    }
}
