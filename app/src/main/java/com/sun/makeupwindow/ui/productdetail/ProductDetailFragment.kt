package com.sun.makeupwindow.ui.productdetail

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.sun.makeupwindow.R
import com.sun.makeupwindow.base.BaseFragment
import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.ui.dialog.LoadingDialog
import com.sun.makeupwindow.utlis.*
import kotlinx.android.synthetic.main.fragment_detail_product.*

class ProductDetailFragment : BaseFragment(), ProductDetailContract.View {

    private var presenter: ProductDetailPresenter? = null
    private var loadingDialog: LoadingDialog? = null
    private var productColorAdapter = ProductColorAdapter()
    private var relatedProductAdapter = RelatedProductAdapter(this::itemRelatedClicked)
    private var product: Product? = null

    override val layoutResource: Int
        get() = R.layout.fragment_detail_product

    override fun initData() {
        initInputData()
        initAdapter()
        initPresenter()
        initDialog()
        bindRelatedProductData()
        presenter?.start()
    }

    private fun initAdapter() {
        recyclerColor.adapter = productColorAdapter
        recyclerRelate.adapter = relatedProductAdapter
    }

    private fun initPresenter() {
        val context = context ?: return
        val repository = RepositoryFactory.getRepository(context)
        val repositoryFavorite = RepositoryFactory.getRepositoryFavorite(context)
        presenter = ProductDetailPresenter(this, repository, repositoryFavorite)
    }

    private fun bindRelatedProductData() {
        product?.let { product ->
            presenter?.apply {
                getRelatedProducts(product.category)
            }
        }
    }

    private fun initDialog() {
        loadingDialog = LoadingDialog(requireContext())
    }

    override fun isFavoriteProduct(check: Boolean) {
        buttonFavorite.isVisible = check
        buttonUnFavorite.isVisible = !check
    }

    override fun initActions() {
        product?.let {
            presenter?.getProductFavorite(it.id)
        }

        buttonBackDetail.apply {
            setOnClickListener {
                fragmentManager?.removeFragment(
                    R.id.frameContainer,
                    this@ProductDetailFragment
                )
            }
        }

        buttonFavorite.setOnClickListener {
            product?.let { item -> presenter?.deleteFavorite(item.id) }
            it.visibility = View.GONE
            buttonUnFavorite.visibility = View.VISIBLE
        }

        buttonUnFavorite.setOnClickListener {
            product?.let { item -> presenter?.insertFavorite(item.id) }
            buttonFavorite.visibility = View.VISIBLE
            it.visibility = View.GONE
        }

        buttonBuy.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(product?.productLink))
            startActivity(browserIntent)
        }
    }

    override fun showRelatedProducts(products: List<Product>) {
        relatedProductAdapter.replaceData(products)
    }

    override fun isInsertedFavoriteProduct(check: Boolean) {
        context?.showToast(
            resources.getString(R.string.msg_insert_success)
        )
    }

    override fun isDeletedFavoriteProduct(check: Boolean) {
        context?.showToast(
            resources.getString(R.string.msg_delete_success)
        )
    }

    override fun showError(message: String) {
        context?.showToast(message)
    }

    override fun showLoading() {
        loadingDialog?.show()
    }

    override fun hideLoading() {
        loadingDialog?.dismiss()
    }

    private fun itemRelatedClicked(product: Product) {
        fragmentManager?.addFragment(R.id.frameContainer, getInstance(product))
    }

    private fun initInputData() {
        product = arguments?.getParcelable<Product>(BUNDLE_PRODUCT_DETAIL)?.apply {
            imageProduct.loadImage(this.imageLink)
            textProductName.text = this.name
            textProductPrice.text = this.price + " " + this.priceSign
            textProductDetail.text = this.description
            productColorAdapter.replaceData(this.color)
        }
    }

    companion object {
        private const val BUNDLE_PRODUCT_DETAIL = "BUNDLE_PRODUCT_DETAIL"

        fun getInstance(product: Product) = ProductDetailFragment().apply {
            arguments = bundleOf(BUNDLE_PRODUCT_DETAIL to product)
        }
    }
}
