package com.example.swipeassignment.fragments.listProduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.swipeassignment.R
import com.example.swipeassignment.databinding.FragmentProductListBinding
import com.example.swipeassignment.model.Model
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductListFragment : Fragment() {
    private lateinit var binding: FragmentProductListBinding
    private lateinit var productAdapter: ProductListAdapter
    private var apiResponse = ArrayList<Model>()
    private val viewModel by viewModel<ProductListViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentProductListBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doObserve()

    }

    override fun onResume() {
        super.onResume()
        doObserve()
    }

    private fun doObserve() {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            viewModel.product.observe(viewLifecycleOwner, Observer {
                binding.progressBar.visibility = View.INVISIBLE
                apiResponse.addAll(it)
                renderList(it!!)
                setUpSearch(apiResponse)

            })

        }

    }

    //Rendering List
    private fun renderList(it: List<Model>) {
        binding.ProductListRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        productAdapter = ProductListAdapter(it)
        binding.ProductListRv.adapter = productAdapter

    }

    /*This method is for performing search operation*
        Array list is pass to this function and looping through while checking condition
        and passed that filtered list to adapter.
     */
    private fun setUpSearch(apiResponse: ArrayList<Model>) {

        binding.productSearchSv.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchResponse = ArrayList<Model>()
                searchResponse.clear()
                val lowercaseAllQuery = newText!!.lowercase()
                if (lowercaseAllQuery.isNotEmpty()) {


                    apiResponse.forEach {
                        if (it.mProductName.lowercase().contains(lowercaseAllQuery)) {
                            searchResponse.add(it)
                        } else if (it.mProductType.lowercase().contains(lowercaseAllQuery)) {
                            searchResponse.add(it)
                        } else if (it.mPrice.toString().contains(lowercaseAllQuery)) {
                            searchResponse.add(it)
                        } else if (it.mTax.toString().contains(lowercaseAllQuery)) {
                            searchResponse.add(it)
                        }
                    }
                    if (searchResponse.isEmpty()) {
                        Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_LONG).show()
                    } else {
                        productAdapter.filteredList(searchResponse)
                    }
                } else {
                    searchResponse.addAll(apiResponse)
                    productAdapter.filteredList(searchResponse)
                }
                return true
            }


        })
        binding.addProductFab.setOnClickListener {
            findNavController().navigate(R.id.action_productListFragment_to_addProductFragments)
        }
    }


}