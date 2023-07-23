package com.example.whattodo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.whattodo.data.DataSource
import com.example.whattodo.databinding.FragmentCreateEventBinding
import com.example.whattodo.model.Events
import kotlinx.android.synthetic.*
import java.text.DateFormat
import java.util.*
import com.example.whattodo.model.Event
import kotlinx.android.synthetic.main.fragment_create_event.*


private var _binding: FragmentCreateEventBinding? = null
private val binding get() = _binding!!


class CreateEvent : Fragment() {
    private var name: EditText? = null
    private var desc: EditText? = null
    private var street: EditText? = null
    private var city: EditText? = null
    private var state: EditText? = null
    private var date: DatePicker? = null
    private var btn: Button? = null
    private var imgDrop: Spinner? = null


    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateEventBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name = view.findViewById<EditText>(R.id.EventName)
        desc = view.findViewById<EditText>(R.id.Description)
        street = view.findViewById<EditText>(R.id.Street)
        city = view.findViewById<EditText>(R.id.City)
        state = view.findViewById<EditText>(R.id.State)
        date = view.findViewById<DatePicker>(R.id.date)
        imgDrop = view.findViewById<Spinner>(R.id.imageDropdown)
        val items = arrayOf("Bar", "Axe-Throwing", "Bowling", "Frisbee", "Put-Put", "Racing", "Skydiving", "Top-Golf", "Concert")
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        imgDrop?.setAdapter(adapter)


        btn = view.findViewById<Button>(R.id.create_button)
        btn?.setOnClickListener {
            handleCreate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getImageIDFromString(name:String): Int {
        if (name == "Bar") {
            return 1;
        }
        if (name == "Axe-Throwing") {
            return 2;
        }
        if (name == "Bowling"){
            return 3;
        }
        if (name == "Frisbee") {
            return 4;
        }
        if (name == "Put-Put") {
            return 5;
        }
        if (name == "Racing") {
            return 6;
        }
        if (name == "Skydiving") {
            return 7;
        }
        if (name == "Top-Golf") {
            return 8;
        }
        if (name == "Concert") {
            return 9;
        }
        else {
            return 10;
        }
    }

    fun handleCreate() {
        if ((name?.text.toString().trim().isEmpty()) or (desc?.text.toString().trim().isEmpty())) {
            Log.w("CreateEvent", "Must give a name and description to create an event")
        }
        else {
            var day = date?.dayOfMonth
            var month = date?.month
            var year = date?.year
            val event = Event(
                getImageIDFromString(imgDrop?.getSelectedItem().toString()),
                name?.text.toString(),
                desc?.text.toString(),
                Date(year!!, month!!, day!!),
                city?.text.toString(),
                state?.text.toString(),
                street?.text.toString(),
                null,
                null,
                null
            )
            DataSource().addEvent(event)
        }
        view?.findNavController()?.navigate(NavGraphDirections.actionGlobalContent())
    }

}