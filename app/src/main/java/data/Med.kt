package data

import android.widget.CheckBox
import com.google.firebase.database.Exclude
import java.time.Month
import java.time.Year

data class Med(
    @get:Exclude
    var id: String? = null,
    var nameMed:String? = null,
    var amountMed: String? = null,
    var DataMed: String? = null,
    var periodMed: String? = null,
    var spinMed: String? = null,


    @get:Exclude
    var isDeleted: Boolean = false
) {

    override fun equals(other: Any?): Boolean {
        return if(other is Med){
            other.id == id
        }
        else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (nameMed?.hashCode() ?: 0)
        result = 31 * result + (amountMed?.hashCode() ?: 0)
        result = 31 * result + (DataMed?.hashCode() ?: 0)
        result = 31 * result + (periodMed?.hashCode() ?: 0)
        result = 31 * result + (spinMed?.hashCode() ?: 0)
        result = 31 * result + isDeleted.hashCode()
        return result
    }

}