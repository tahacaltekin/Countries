package com.dttcaltekin.countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dttcaltekin.countries.databinding.FragmentCountryBinding
import com.dttcaltekin.countries.util.downloadFromUrl
import com.dttcaltekin.countries.util.placeHolderProgressBar
import com.dttcaltekin.countries.viewmodel.CountryViewModel

class CountryFragment : Fragment() {

    private lateinit var countryBinding : FragmentCountryBinding
    private lateinit var countryViewModel : CountryViewModel
    private var countryUuid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        countryBinding = FragmentCountryBinding.inflate(layoutInflater)

        return countryBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        countryViewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        countryViewModel.getDataFromRoom(countryUuid)

        observeLiveData()
    }

    private fun observeLiveData() {
        countryViewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {
                countryBinding.countryName.text = country.countryName
                countryBinding.countryCapital.text = country.countryCapital
                countryBinding.countryRegion.text = country.countryRegion
                countryBinding.countryCurrency.text = country.countryCurrency
                countryBinding.countryLanguage.text = country.countryLanguage

                context?.let {
                    countryBinding.countryImage.downloadFromUrl(country.imageUrl, placeHolderProgressBar(it))
                }
            }
        })
    }
}