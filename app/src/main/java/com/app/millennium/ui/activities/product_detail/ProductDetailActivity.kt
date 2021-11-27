package com.app.millennium.ui.activities.product_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.app.millennium.core.common.Constant
import com.app.millennium.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityProductDetailBinding

    private lateinit var bundle: Bundle
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bundle = intent.getBundleExtra(Constant.BUNDLE_PRODUCT)!!
    
        initUI()
    }
    
    private fun initUI(){
        Log.d("BUNDLE", "${Constant.PROP_ID_PRODUCT} -> " + bundle[Constant.PROP_ID_PRODUCT].toString())
        Log.d("BUNDLE", "${Constant.PROP_ID_USER_PRODUCT} -> " + bundle[Constant.PROP_ID_USER_PRODUCT].toString())
        Log.d("BUNDLE", "${Constant.PROP_TITLE_PRODUCT} -> " + bundle[Constant.PROP_TITLE_PRODUCT].toString())
        Log.d("BUNDLE", "${Constant.PROP_DESCRIPTION_PRODUCT} -> " + bundle[Constant.PROP_DESCRIPTION_PRODUCT].toString())
        Log.d("BUNDLE", "${Constant.PROP_CATEGORY_PRODUCT} -> " + bundle[Constant.PROP_CATEGORY_PRODUCT].toString())
        Log.d("BUNDLE", "${Constant.PROP_PRICE_PRODUCT} -> " + bundle[Constant.PROP_PRICE_PRODUCT].toString())
        Log.d("BUNDLE", "${Constant.PROP_NEGOTIABLE_PRODUCT} -> " + bundle[Constant.PROP_NEGOTIABLE_PRODUCT].toString())
        Log.d("BUNDLE", "${Constant.PROP_PRODUCT_STATUS_PRODUCT} -> " + bundle[Constant.PROP_PRODUCT_STATUS_PRODUCT].toString())
        Log.d("BUNDLE", "${Constant.PROP_IMAGE1_PRODUCT} -> " + bundle[Constant.PROP_IMAGE1_PRODUCT].toString())
        Log.d("BUNDLE", "${Constant.PROP_IMAGE2_PRODUCT} -> " + bundle[Constant.PROP_IMAGE2_PRODUCT].toString())
        Log.d("BUNDLE", "${Constant.PROP_IMAGE3_PRODUCT} -> " + bundle[Constant.PROP_IMAGE3_PRODUCT].toString())
        Log.d("BUNDLE", "${Constant.PROP_IMAGE4_PRODUCT} -> " + bundle[Constant.PROP_IMAGE4_PRODUCT].toString())
        Log.d("BUNDLE", "${Constant.PROP_TIMESTAMP_PRODUCT} -> " + bundle[Constant.PROP_TIMESTAMP_PRODUCT].toString())
    }
}