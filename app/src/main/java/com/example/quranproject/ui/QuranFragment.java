package com.example.quranproject.ui;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.transition.FragmentTransitionSupport;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.arindicatorview.ARIndicatorView;
//import com.example.quranproject.QuranFragmentDirections;
import com.example.quranproject.R;
import com.example.quranproject.adapters.QuranAdapter;

public class QuranFragment extends Fragment  {
    // initialize global object fro adapter
    QuranAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quran, container, false);
    }

    // Quran Chapters Names in array to display it in recyclerView items
    String []ArSuras={"الفاتحه","البقرة","آل عمران","النساء","المائدة","الأنعام","الأعراف","الأنفال","التوبة","يونس","هود"
            ,"يوسف","الرعد","إبراهيم","الحجر","النحل","الإسراء","الكهف","مريم","طه","الأنبياء","الحج","المؤمنون"
            ,"النّور","الفرقان","الشعراء","النّمل","القصص","العنكبوت","الرّوم","لقمان","السجدة","الأحزاب","سبأ"
            ,"فاطر","يس","الصافات","ص","الزمر","غافر","فصّلت","الشورى","الزخرف","الدّخان","الجاثية","الأحقاف"
            ,"محمد","الفتح","الحجرات","ق","الذاريات","الطور","النجم","القمر","الرحمن","الواقعة","الحديد","المجادلة"
            ,"الحشر","الممتحنة","الصف","الجمعة","المنافقون","التغابن","الطلاق","التحريم","الملك","القلم","الحاقة","المعارج"
            ,"نوح","الجن","المزّمّل","المدّثر","القيامة","الإنسان","المرسلات","النبأ","النازعات","عبس","التكوير","الإنفطار"
            ,"المطفّفين","الإنشقاق","البروج","الطارق","الأعلى","الغاشية","الفجر","البلد","الشمس","الليل","الضحى","الشرح"
            ,"التين","العلق","القدر","البينة","الزلزلة","العاديات","القارعة","التكاثر","العصر",
            "الهمزة","الفيل","قريش","الماعون","الكوثر","الكافرون","النصر","المسد","الإخلاص","الفلق","الناس"};
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ARIndicatorView indicatorView = view.findViewById(R.id.pageIndicatorView);
        adapter = new QuranAdapter(view.getContext(),ArSuras);
        RecyclerView recyclerView = view.findViewById(R.id.quranRecyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),3,RecyclerView.HORIZONTAL,false));
        recyclerView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        recyclerView.setAdapter(adapter);
        indicatorView.setNumberOfIndicators(13);
        indicatorView.attachTo(recyclerView,true);

        // on recycler View item Clicked
        adapter.onSuraClicked(new QuranAdapter.OnItemClickListenerInterface() {
            @Override
            public void onItemClicked(int pos) {
                // init navigation action to Quran Chapter Description

                //NavDirections action = QuranFragmentDirections.FromQuranToRead(pos, ArSuras[pos]);
               QuranFragmentDirections.FromQuranToRead action = QuranFragmentDirections.FromQuranToRead(pos,ArSuras[pos]);
                /* // put safe args for action
                action.setSuraNumber(pos);
                action.setSuraName(ArSuras[pos]);*/
                Navigation.findNavController(view).navigate(action);
            }
        });

    }


}