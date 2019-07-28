package ms.fitcalculator.model.remote


import com.google.gson.annotations.SerializedName

data class FoodNutrients(
    @SerializedName("hints")
    val hints: List<Hint>,
    @SerializedName("_links")
    val links: Links,
    @SerializedName("parsed")
    val parsed: List<Parsed>,
    @SerializedName("text")
    val text: String
) {
    data class Hint(
        @SerializedName("food")
        val food: Food,
        @SerializedName("measures")
        val measures: List<Measure>
    ) {
        data class Measure(
            @SerializedName("label")
            val label: String,
            @SerializedName("uri")
            val uri: String
        )

        data class Food(
            @SerializedName("brand")
            val brand: String,
            @SerializedName("category")
            val category: String,
            @SerializedName("categoryLabel")
            val categoryLabel: String,
            @SerializedName("foodContentsLabel")
            val foodContentsLabel: String,
            @SerializedName("foodId")
            val foodId: String,
            @SerializedName("label")
            val label: String,
            @SerializedName("nutrients")
            val nutrients: Nutrients
        ) {
            data class Nutrients(
                @SerializedName("CHOCDF")
                val cHOCDF: Double,
                @SerializedName("ENERC_KCAL")
                val eNERCKCAL: Double,
                @SerializedName("FAT")
                val fAT: Double,
                @SerializedName("FIBTG")
                val fIBTG: Double
            )
        }
    }

    data class Parsed(
        @SerializedName("food")
        val food: Food
    ) {
        data class Food(
            @SerializedName("category")
            val category: String,
            @SerializedName("categoryLabel")
            val categoryLabel: String,
            @SerializedName("foodId")
            val foodId: String,
            @SerializedName("image")
            val image: String,
            @SerializedName("label")
            val label: String,
            @SerializedName("nutrients")
            val nutrients: Nutrients
        ) {
            data class Nutrients(
                @SerializedName("CHOCDF")
                val cHOCDF: Double,
                @SerializedName("ENERC_KCAL")
                val eNERCKCAL: Int,
                @SerializedName("FAT")
                val fAT: Double,
                @SerializedName("FIBTG")
                val fIBTG: Double,
                @SerializedName("PROCNT")
                val pROCNT: Double
            )
        }
    }

    data class Links(
        @SerializedName("next")
        val next: Next
    ) {
        data class Next(
            @SerializedName("href")
            val href: String,
            @SerializedName("title")
            val title: String
        )
    }
}