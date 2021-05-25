package com.sun.makeupwindow.ui.home

import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sun.makeupwindow.R
import com.sun.makeupwindow.base.BaseFragment
import com.sun.makeupwindow.data.model.Category
import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.ui.dialog.LoadingDialog
import com.sun.makeupwindow.ui.productdetail.ProductDetailFragment
import com.sun.makeupwindow.utlis.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(), HomeContract.View {

    private var presenter: HomePresenter? = null
    private var loadingDialog: LoadingDialog? = null
    private var loadMoreItems = mutableListOf<Product>()
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll
    private lateinit var layout: RecyclerView.LayoutManager
    private val adapterProduct = ProductAdapter(this::itemProductClicked)
    private val adapterCategory = CategoryAdapter(this::itemCategoryClicked)

    override val layoutResource: Int
        get() = R.layout.fragment_home

    override fun initData() {
        initAdapter()
        initPresenter()
        initDialog()
        presenter?.start()
    }

    override fun initActions() {
        setLayoutManager()
        setScrollListener()
    }

    private fun initAdapter() {
        recyclerProduct.adapter = adapterProduct
        recyclerCategory.adapter = adapterCategory
    }

    private fun initPresenter() {
        val context = context ?: return
        val repository = RepositoryFactory.getRepository(context)
        presenter = HomePresenter(this, repository)
    }

    private fun initDialog() {
        context?.let {
            loadingDialog = LoadingDialog(it)
        }
    }

    override fun showProducts(products: List<Product>) {
        adapterProduct.replaceData(products)
        loadMoreItems.addAll(products)
    }

    override fun showProductsLoadMore(products: List<Product>) {
        loadMoreItems.addAll(products)
        adapterProduct.removeLoadingView()
        adapterProduct.replaceData(loadMoreItems)
        scrollListener.isLoading = false
    }

    override fun showCategories(categories: List<Category>) {
        adapterCategory.replaceData(categories)
    }

    private fun setLayoutManager() {
        layout = GridLayoutManager(context, 2)
        recyclerProduct.apply {
            layoutManager = layout
            setHasFixedSize(true)
            adapter = adapterProduct
        }
        (layout as GridLayoutManager).spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapterProduct.getItemViewType(position)) {
                        VIEW_TYPE_ITEM -> 1
                        VIEW_TYPE_LOADING -> 2
                        else -> -1
                    }
                }
            }
    }

    private fun setScrollListener() {
        scrollListener = RecyclerViewLoadMoreScroll(layout as GridLayoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                LoadMoreData()
            }
        })
        recyclerProduct.addOnScrollListener(scrollListener)
    }

    private fun LoadMoreData() {
        adapterProduct.addLoadingView()
        val totalItem = adapterProduct.itemCount
        presenter?.getProductLoadMore(totalItem)
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
            ProductDetailFragment.getInstance(product)
        )
    }

    private fun itemCategoryClicked(category: Category) {
    }

    companion object {
        private var instance: HomeFragment? = null

        fun getInstance() = instance ?: HomeFragment().also { instance = it }
    }
}
