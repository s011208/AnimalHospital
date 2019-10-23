package yhh.dev.repository.local

import android.os.SystemClock
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class AnimalHospitalEntity(
    @field:[SerializedName("縣市") Expose]
    val county: String,

    @field:[SerializedName("字號") Expose PrimaryKey]
    val id: String,

    @field:[SerializedName("執照類別") Expose]
    val type: String,

    @field:[SerializedName("狀態") Expose]
    val status: String,

    @field:[SerializedName("機構名稱") Expose]
    val name: String,

    @field:[SerializedName("負責獸醫") Expose]
    val owner: String,

    @field:[SerializedName("機構電話") Expose]
    val tel: String,

    @field:[SerializedName("發照日期") Expose]
    val issueDate: String,

    @field:[SerializedName("機構地址") Expose]
    val address: String,

    val timeStamp: Long = SystemClock.elapsedRealtime()
)