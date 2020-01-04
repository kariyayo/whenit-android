package info.bati11.whenit.ui.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import info.bati11.whenit.databinding.ListItemEventBinding
import info.bati11.whenit.domain.Event

class EventAdapter(val menuClickListener: EventMenuClickListener) : ListAdapter<Event, EventAdapter.ViewHolder>(EventDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, menuClickListener)
    }

    class ViewHolder private constructor(val binding: ListItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Event, menuClickListener: EventMenuClickListener) {
            binding.event = item
            binding.menuClickListener = menuClickListener
            binding.textYears.text = item.years(System.currentTimeMillis()).toString()
            binding.textDays.text = item.daysOfYears(System.currentTimeMillis()).toString()
            binding.textTitle.text = "${item.title} ( ${item.year}-${item.month}-${item.dayOfMonth} )"
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemEventBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class EventDiffCallback : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }

}

class EventMenuClickListener(val clickListener: (id: Long)-> Unit) {
    fun onClick(event: Event) = clickListener(event.id!!)
}
