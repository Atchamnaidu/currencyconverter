package com.example.currencyconverter.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.R
import com.example.currencyconverter.databinding.CurrenciesListItemBinding
import com.example.currencyconverter.model.Currency
import com.example.currencyconverter.view.CurrenciesListAdapter.ViewHolder

class CurrenciesListAdapter : RecyclerView.Adapter<ViewHolder>() {

    private var currencies: ArrayList<Currency> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    internal fun setData(data: ArrayList<Currency>) {
        // can use DiffUtil here if data huge and frequently updating
        currencies = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CurrenciesListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.currencies_list_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currencies[position])
    }

    override fun getItemCount(): Int {
        return currencies.size
    }

    class ViewHolder(private var binding: CurrenciesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currency: Currency) {
            binding.currency = currency
        }
    }
}