package com.app.millennium.ui.activities.filter_products

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.millennium.R
import com.app.millennium.core.common.Constant
import com.app.millennium.core.common.isNotNull
import com.app.millennium.data.model.Product
import com.app.millennium.databinding.ActivityFilterProductsBinding
import com.app.millennium.ui.adapters.product_home.ProductHomeAdapter

class FilterProductsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityFilterProductsBinding
    private val viewModel: FilterProductsViewModel by viewModels()

    private var products = mutableListOf<Product>()
    private lateinit var productHomeAdapter: ProductHomeAdapter

    private var category = ""
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        category = intent.extras?.getString(Constant.PROP_CATEGORY_PRODUCT)!!

        initUI()
        initObservables()
    }

    private fun initUI(){

        configEvents()
        initComponents()
        viewModel.getAllProductsByCategory(category)
    }

    private fun initObservables() {
        viewModel.getAllProductsByCategory.observe(
            this,
            {
                it?.let { query ->
                    query.addSnapshotListener { value, error ->
                        if(products.isNotEmpty())
                            products.clear()

                        if (error.isNotNull()){
                            return@addSnapshotListener
                        }

                        if (value.isNotNull() && !value?.isEmpty!!){
                            for (document in value.documents){
                                if (document.exists()){
                                    products.add(document.toObject(Product::class.java)!!)
                                }
                            }

                            productHomeAdapter = ProductHomeAdapter(products)
                            binding.rvProducts.adapter = productHomeAdapter

                            binding.clSinProducts.visibility = View.GONE
                            binding.llListProducts.visibility = View.VISIBLE
                            binding.progress.visibility = View.GONE
                        } else {
                            binding.clSinProducts.visibility = View.VISIBLE
                            binding.llListProducts.visibility = View.GONE
                            binding.progress.visibility = View.GONE
                        }
                    }
                }
            }
        )
    }

    @SuppressLint("SetTextI18n")
    private fun initComponents() {
        binding.mtvCategory.text = category
        binding.mtvInfoSinProducts.text = "${getString(R.string.mtv_msg_info_sin_prodcutos_filtrados)} $category"
        binding.rvProducts.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun configEvents() {
        binding.ivBack.setOnClickListener {
            finishAndRemoveTask()
        }
    }
}