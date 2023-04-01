package com.example.swipeassignment.fragments.addProduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import com.example.swipeassignment.databinding.FragmentAddProductFragmentsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class AddProductFragments : Fragment() {
    private lateinit var binding: FragmentAddProductFragmentsBinding
    private val viewModel by viewModel<AddProductViewModel>()
    private var storeFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentAddProductFragmentsBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
    }

    private fun setUpUI() {
        var spinnerSelectedItem = ""

        // this launcher is responsible for getting URI from albums or local storage.
        val activityLauncher = registerForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(5)
        ){
            if(it.size == 1){
                storeFile = viewModel.uriToFile(it[0],requireContext().applicationContext)

            }

        }
        binding.uploadImageBt.setOnClickListener {
            activityLauncher.launch(
                PickVisualMediaRequest.Builder()
                    .setMediaType(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                    .build()

            )

        }

        binding.productTypeSv.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                spinnerSelectedItem = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        binding.submitBtn.setOnClickListener {
            val check = viewModel.checkInput(binding.productNameEt.text.toString(),
                spinnerSelectedItem,
                binding.priceEt.text.toString(),
                binding.taxEt.text.toString(),storeFile)
            if(check){
                viewModel.addProductLiveData.observe(viewLifecycleOwner, Observer {
                    if(it){
                        Toast.makeText(requireContext(),"Upload Successfully",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(),"Something Went Wrong...",Toast.LENGTH_SHORT).show()
                    }
                })
            }else{
                Toast.makeText(requireContext(),"Please fill all field",Toast.LENGTH_SHORT).show()
            }
        }
    }

}
