package ru.ikom.catalog.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.Lazy
import kotlinx.coroutines.launch
import ru.ikom.catalog.databinding.FragmentCatalogBinding
import ru.ikom.catalog.di.CatalogComponentProvider
import ru.ikom.common.BaseFragment
import javax.inject.Inject

class CatalogFragment : BaseFragment<FragmentCatalogBinding, CatalogViewModel>() {

    @Inject
    lateinit var viewModelFactory: Lazy<CatalogViewModel.Factory>

    override val viewModel: CatalogViewModel by lazy {
        ViewModelProvider(this, viewModelFactory.get())[CatalogViewModel::class.java]
    }

    private val adapter = ProductsAdapter {
        viewModel.action(Event.OpenDetails(it))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as CatalogComponentProvider).provideCatalogComponent().inject(this)
    }

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentCatalogBinding =
        FragmentCatalogBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.products.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect { state ->
                binding.loading.visibility = if (state.loading) View.VISIBLE else View.GONE

                if (state.products.isNotEmpty()) {
                    binding.products.visibility = View.VISIBLE
                    adapter.submitList(state.products)
                }
                else {
                    binding.products.visibility = View.GONE
                }

                binding.containerError.visibility = if (state.hasError != null) View.VISIBLE else View.GONE
                state.hasError?.let {
                    binding.hasError.text = getString(it)
                    binding.btnRetry.setOnClickListener {
                        viewModel.action(Event.Retry())
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = CatalogFragment()
    }
}