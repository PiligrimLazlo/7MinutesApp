package ru.pl.a7minuteworkout.historyDB

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.pl.a7minuteworkout.R
import ru.pl.a7minuteworkout.databinding.ItemHistoryBinding
import java.text.SimpleDateFormat
import java.util.*

class HistoryEntryEntityAdapter(val items: List<HistoryEntryEntity>) :
    RecyclerView.Adapter<HistoryEntryEntityAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        val currentDate = currentItem.finishDate

        val sdf =
            SimpleDateFormat(
                holder.itemView.context
                    .getString(R.string.dateFormat), Locale.getDefault()
            )
        val formattedDate = sdf.format(currentDate.time)

        holder.tvExerciseDate.text = formattedDate


        if (position % 2 == 0) {
            holder.mainLayout.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.lightGrey
                )
            )
        } else {
            holder.mainLayout.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvExerciseDate = binding.tvExercisesDate
        val mainLayout = binding.mainLayout
    }


}