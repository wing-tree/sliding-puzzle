package com.wing.tree.sid.data.mapper

import com.wing.tree.sid.core.constant.EIGHT
import com.wing.tree.sid.core.constant.FIFTEEN
import com.wing.tree.sid.core.constant.THIRTY_FIVE
import com.wing.tree.sid.core.constant.TWENTY_FOUR
import com.wing.tree.sid.domain.entity.Size

class SizeMapper : DataModelMapper<Int, Size> {
    override fun toEntity(dataModel: Int): Size = when(dataModel) {
        EIGHT -> Size.Eight
        FIFTEEN -> Size.Fifteen
        TWENTY_FOUR -> Size.TwentyFour
        THIRTY_FIVE -> Size.ThirtyFive
        else -> throw IllegalArgumentException("$dataModel")
    }
}
