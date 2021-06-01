package com.sun.makeupwindow.ui.producttype

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
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

class ProductTypeFragment : BaseFragment(), ProductTypeContract.View {

    private lateinit var binding: FragmentProductTypeBinding
    private var presenter: ProductTypePresenter? = null
    private val adapter = ProductTypeAdapter(this::itemProductClicked)
    private var loadingDialog: LoadingDialog? = null
    private var category: Category? = null
    private val dialogPrice = DialogPrice()
    private val dialogSort = DialogSort()
    private val products = mutableListOf<Product>()
    private var statusPrice: String? = null
    private var statusSort: String? = null

    override val layoutResource: Int
        get() = R.layout.fragment_product_type

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

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

        binding.buttonSort.setOnClickListener {
            fragmentManager?.let { fragmentManager ->
                dialogSort.setTargetFragment(this, REQUEST_CODE)
                dialogSort.show(fragmentManager, TAG)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_CODE) {
                if (data?.getStringExtra(RETURN_PRICE).toString() != Product.NULL) {
                    statusPrice = data?.getStringExtra(RETURN_PRICE).toString()
                }
                if (data?.getStringExtra(RETURN_SORT).toString() != Product.NULL) {
                    statusSort = data?.getStringExtra(RETURN_SORT).toString()
                }

                updateStatus(statusPrice, statusSort)
            }
        }
    }

    private fun updateStatus(price: String?, sort: String?) {
        var product = mutableListOf<Product>()

        when (price) {
            getString(ALL) -> product.addAll(products)

             getString(PRICE_1) ->
                product.addAll(products.filter { it.price.toDouble() < NUMBER_FIVE })

            getString(PRICE_2) ->
                product.addAll(products.filter { it.price.toDouble() <= NUMBER_TEN && it.price.toDouble() >= NUMBER_FIVE })

            getString(PRICE_3) ->
                product.addAll(products.filter { it.price.toDouble() <= NUMBER_FIFTEEN && it.price.toDouble() >= NUMBER_TEN })

            getString(PRICE_4) ->
                product.addAll(products.filter { it.price.toDouble() <= NUMBER_TWENTY && it.price.toDouble() >= NUMBER_FIFTEEN })

            getString(PRICE_5) ->
                product.addAll(products.filter { it.price.toDouble() > NUMBER_TWENTY })
        }

        if (product.size.equals(ZERO)) textStatusProduct.isVisible = true
        else textStatusProduct.isVisible = false

        when (sort) {
            getString(NEW) -> adapter.replaceData(product)
            getString(OLD) -> adapter.replaceData(product.reversed())
            getString(INCREASE) -> adapter.replaceData(product.sortedBy { it.price.toDouble() })
            getString(DECREASE) -> adapter.replaceData(product.sortedByDescending { it.price.toDouble() })
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
        statusPrice = getString(ALL)
        statusSort = getString(NEW)
    }

    companion object {
        private const val ALL = R.string.text_all
        private const val PRICE_1 = R.string.text_price_1
        private const val PRICE_2 = R.string.text_price_2
        private const val PRICE_3 = R.string.text_price_3
        private const val PRICE_4 = R.string.text_price_4
        private const val PRICE_5 = R.string.text_price_5
        private const val NEW = R.string.text_new
        private const val OLD = R.string.text_old
        private const val INCREASE = R.string.text_increase
        private const val DECREASE = R.string.text_decrease

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
