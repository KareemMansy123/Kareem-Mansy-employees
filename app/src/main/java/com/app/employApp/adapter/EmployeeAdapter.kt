package com.app.employApp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.core.domain.Employee
import com.app.employApp.R

class EmployeeAdapter(
    var employeeList: List<Employee>, private val context: Context,
) : RecyclerView.Adapter<EmployeeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.employee_column_layout, parent, false)
        return EmployeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
       val employee = employeeList[position]
        holder.tvId.text = employee.id
        holder.tvProjectId.text = employee.projectId
        holder.tvStartDate.text = employee.startDate
        holder.tvEndDate.text = employee.endDate
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    fun setData(employee: List<Employee>){
    employeeList = employee
    }
}

class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvId: TextView = itemView.findViewById(R.id.tvId)
    val tvProjectId: TextView = itemView.findViewById(R.id.tvProjectId)
    val tvStartDate: TextView = itemView.findViewById(R.id.tvStartDate)
    val tvEndDate: TextView = itemView.findViewById(R.id.tvEndDate)
}
