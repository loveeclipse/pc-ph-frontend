package it.unibo.preh_frontend.utils

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.PreHData

class HistoryListAdapter(private var activity: Activity, private var items: ArrayList<PreHData>) : BaseAdapter() {
    private class ViewHolder(row: View?) {
        var eventName: TextView? = null
        var eventDate: TextView? = null
        var eventDataButton: ImageView? = null
        init {
            eventName = row?.findViewById(R.id.history_action)
            eventName?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17F)
            eventDate = row?.findViewById(R.id.hour_and_date)
            eventDate?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12F)
            eventDataButton = row?.findViewById(R.id.history_item_info)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.row_layout, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val historyItem = items[position]
        viewHolder.eventName?.text = historyItem.eventName
        viewHolder.eventDate?.text = DateManager.getHistoryRepresentation(historyItem.eventTime)

        return view
    }

    override fun getItem(i: Int): PreHData {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}