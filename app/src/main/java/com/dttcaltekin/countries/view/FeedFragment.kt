package com.dttcaltekin.countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dttcaltekin.countries.adapter.CountryAdapter
import com.dttcaltekin.countries.databinding.FragmentFeedBinding
import com.dttcaltekin.countries.viewmodel.FeedViewModel


class FeedFragment : Fragment() {

    private lateinit var feedBinding : FragmentFeedBinding
    private lateinit var feedViewModel : FeedViewModel
    private var countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        feedBinding = FragmentFeedBinding.inflate(layoutInflater)
        return feedBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        feedViewModel.refreshData()

        feedBinding.countryList.layoutManager = LinearLayoutManager(context)
        feedBinding.countryList.adapter = countryAdapter

        feedBinding.swipeRefreshLayout.setOnRefreshListener {
            feedBinding.countryList.visibility = View.GONE
            feedBinding.countryError.visibility = View.GONE
            feedBinding.countryLoading.visibility = View.VISIBLE
            feedViewModel.refreshFromAPI()

            feedBinding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        feedViewModel.countries.observe(viewLifecycleOwner, Observer { countries ->

            countries?.let {
                feedBinding.countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })

        feedViewModel.countryError.observe(viewLifecycleOwner, Observer { error ->

            error?.let {
                if (it) {
                    feedBinding.countryError.visibility = View.VISIBLE
                } else {
                    feedBinding.countryError.visibility = View.GONE
                }
            }
        })

        feedViewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    feedBinding.countryLoading.visibility = View.VISIBLE
                    feedBinding.countryList.visibility = View.GONE
                    feedBinding.countryError.visibility = View.GONE
                } else {
                    feedBinding.countryLoading.visibility = View.GONE
                }
            }
        })
    }
}