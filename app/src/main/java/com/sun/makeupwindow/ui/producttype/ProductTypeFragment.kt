package com.sun.makeupwindow.ui.producttype

import android.content.Intent
import android.widget.Toast
import androidx.core.os.bundleOf
import com.sun.makeupwindow.R
import com.sun.makeupwindow.base.BaseFragment
import com.sun.makeupwindow.data.model.Category
import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.databinding.FragmentProductTypeBinding
import com.sun.makeupwindow.ui.dialog.LoadingDialog
import com.sun.makeupwindow.ui.productdetail.ProductDetailFragment
import com.sun.makeupwindow.utlis.*
import kotlinx.android.synthetic.main.fragment_detail_product.*
import kotlinx.android.synthetic.main.fragment_product_type.*
import kotlinx.android.synthetic.main.fragment_product_type.buttonBackDetail

class ProductTypeFragment : BaseFragment(), ProductTypeContract.View {

    private lateinit var binding: FragmentProductTypeBinding
    private var presenter: ProductTypePresenter? = null
    private val adapter = ProductTypeAdapter(this::itemProductClicked)
    private var loadingDialog: LoadingDialog? = null
    private var category: Category? = null
    private val dialogPrice = DialogPrice()
    private val products = mutableListOf<Product>()

    override val layoutResource: Int
        get() = R.layout.fragment_product_type

    override fun initData() {
        initInputData()
        initAdapter()
        initPresenter()
        initDialog()
    }

    private fun initAdapter() {
        binding.recyclerProductType.adapter = adapter
    }

    private fun initPresenter() {
        val context = context ?: return
        val repository = RepositoryFactory.getRepository(context)
        presenter = ProductTypePresenter(this, repository)
    }

    private fun initDialog() {
        loadingDialog = LoadingDialog(requireContext())
    }

    override fun initActions() {
        category?.let {
            presenter?.getProducts(it.name)
        }

        binding.buttonBackDetail.apply {
            setOnClickListener {
                fragmentManager?.removeFragment(
                    R.id.frameContainer,
                    this@ProductTypeFragment
                )
            }
        }

        binding.buttonPrice.setOnClickListener {
            fragmentManager?.let { fragmentManager ->
                dialogPrice.setTargetFragment(this, REQUEST_CODE)
                dialogPrice.show(fragmentManager, TAG)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_CODE) {
                val price = data?.getStringExtra(RETURN_PRICE).toString()
                when {
                    price == getString(R.string.text_all) -> {
                        adapter.replaceData(products)
                    }
                    price == getString(R.string.text_price_1) -> {
                        adapter.replaceData(products.filter { it.price.toDouble() < NUMBER_FIVE })
                    }
                    price == getString(R.string.text_price_2) -> {
                        adapter.replaceData(products.filter { it.price.toDouble() <= NUMBER_TEN && it.price.toDouble() >= NUMBER_FIVE })
                    }
                    price == getString(R.string.text_price_3) -> {
                        adapter.replaceData(products.filter { it.price.toDouble() <= NUMBER_FIFTEEN && it.price.toDouble() >= NUMBER_TEN })
                    }
                    price == getString(R.string.text_price_4) -> {
                        adapter.replaceData(products.filter { it.price.toDouble() <= NUMBER_TWENTY && it.price.toDouble() >= NUMBER_FIFTEEN })
                    }
                    price == getString(R.string.text_price_5) -> {
                        adapter.replaceData(products.filter { it.price.toDouble() > NUMBER_TWENTY })
                    }
                }
            }
        }
    }

    override fun showProducts(products: List<Product>) {
        this.products.addAll(products)
        adapter.replaceData(products)
    }

    override fun showError(message: String) {
        context?.showToast(message, Toast.LENGTH_LONG)
    }

    override fun showLoading() {
        loadingDialog?.show()
    }

    override fun hideLoading() {
        loadingDialog?.dismiss()
    }

    private fun itemProductClicked(product: Product) {
        fragmentManager?.addFragment(
            R.id.frameContainer,
            ProductDetailFragment.newInstance(product)
        )
    }

    private fun initInputData() {
        category = arguments?.getParcelable<Category>(BUNDLE_PRODUCT_TYPE)
    }

    companion object {
        private const val BUNDLE_PRODUCT_TYPE = "BUNDLE_PRODUCT_TYPE"
        private const val NUMBER_FIVE = 5
        private const val NUMBER_TEN = 10
        private const val NUMBER_FIFTEEN = 15
        private const val NUMBER_TWENTY = 20
        const val TAG = "TAG"

        fun newInstance(category: Category) = ProductTypeFragment().apply {
            arguments = bundleOf(BUNDLE_PRODUCT_TYPE to category)
        }
    }
}
