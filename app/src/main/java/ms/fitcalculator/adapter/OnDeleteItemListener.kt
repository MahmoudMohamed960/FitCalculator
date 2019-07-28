package ms.fitcalculator.adapter

import ms.fitcalculator.model.local.FitCalcDB

interface OnDeleteItemListener {
    fun onClickedDelete(fitCalcDB: FitCalcDB)
}