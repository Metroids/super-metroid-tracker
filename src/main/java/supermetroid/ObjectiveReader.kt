package supermetroid

import org.json.JSONObject
import supermetroid.trackable.BooleanTrackable
import java.io.File
import java.io.RandomAccessFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class ObjectiveReader {
    var total: Int
    var required: Int
    var objectives: MutableList<String?> = ArrayList()
    var objectiveToImage: MutableMap<String?, String> = HashMap()

    // Located in patches/common/sym/objectives.json
    private val numberOfObjectivesFilePointer = 8769536             // "n_objectives"
    private val numberOfRequiredObjectivesFilePointer = 8769538     // "n_objectives_required"
    private val objectiveFunctionsFilePointer = 8769540             // "objective_funcs"

    init {
        val bytes = ByteArray(2)

        val reader = RandomAccessFile(File(locateRom()), "r")
        reader.seek(variaAddressToFileAddress(numberOfObjectivesFilePointer).toLong())
        reader.read(bytes)
        total = ((bytes[1].toInt() and 255) shl 8) + (bytes[0].toInt() and 255)

        reader.seek(variaAddressToFileAddress(numberOfRequiredObjectivesFilePointer).toLong())
        reader.read(bytes)
        required = ((bytes[1].toInt() and 255) shl 8) + (bytes[0].toInt() and 255)

        val functionToObjective: MutableMap<Int, String> = HashMap()
        var s = readResource("/data/objectives.json")
        var jo = JSONObject(String(s.readAllBytes()))
        for (key in jo.keySet()) {
            functionToObjective[jo.getInt(key) and 0xFFFF] = key
        }
        s.close()

        s = readResource("/data/objective-to-image.json")
        jo = JSONObject(String(s.readAllBytes()))
        for (key in jo.keySet()) {
            objectiveToImage[key] = jo.getString(key)
        }
        s.close()

        for (i in 0 until total) {
            val address = objectiveFunctionsFilePointer + (i * 2)
            reader.seek(variaAddressToFileAddress(address).toLong())
            reader.read(bytes)

            val function = ((bytes[1].toInt() and 255) shl 8) + (bytes[0].toInt() and 255)
            objectives.add(functionToObjective[function])
        }
    }

    fun createObjectiveTrackable(objective: Int): BooleanTrackable {
        val image = "objective/" + objectiveToImage[objectives[objective]]

        return when (objective) {
            0 -> BooleanTrackable(image) { state: State -> (state.objectives[0] and 4) > 0 }
            1 -> BooleanTrackable(image) { state: State -> (state.objectives[0] and 16) > 0 }
            2 -> BooleanTrackable(image) { state: State -> (state.objectives[0] and 64) > 0 }
            3 -> BooleanTrackable(image) { state: State -> (state.objectives[1] and 1) > 0 }
            4 -> BooleanTrackable(image) { state: State -> (state.objectives[1] and 4) > 0 }
            5 -> BooleanTrackable(image) { state: State -> (state.objectives[1] and 16) > 0 }
            6 -> BooleanTrackable(image) { state: State -> (state.objectives[1] and 64) > 0 }
            7 -> BooleanTrackable(image) { state: State -> (state.objectives[2] and 1) > 0 }
            8 -> BooleanTrackable(image) { state: State -> (state.objectives[2] and 4) > 0 }
            9 -> BooleanTrackable(image) { state: State -> (state.objectives[2] and 16) > 0 }
            10 -> BooleanTrackable(image) { state: State -> (state.objectives[2] and 64) > 0 }
            11 -> BooleanTrackable(image) { state: State -> (state.objectives[3] and 1) > 0 }
            12 -> BooleanTrackable(image) { state: State -> (state.objectives[3] and 4) > 0 }
            13 -> BooleanTrackable(image) { state: State -> (state.objectives[3] and 16) > 0 }
            14 -> BooleanTrackable(image) { state: State -> (state.objectives[3] and 64) > 0 }
            15 -> BooleanTrackable(image) { state: State -> (state.objectives[4] and 1) > 0 }
            16 -> BooleanTrackable(image) { state: State -> (state.objectives[4] and 4) > 0 }
            17 -> BooleanTrackable(image) { state: State -> (state.objectives[4] and 16) > 0 }
            else -> throw RuntimeException("The max objective index is 17, given $objective.")
        }
    }

    @Throws(Exception::class)
    private fun locateRom(): String {
        val desktop = System.getProperty("user.home") + "\\Desktop"
        return Files.walk(Paths.get(desktop), 1)
            .filter { f: Path -> f.fileName.toString().endsWith(".sfc") }
            .findFirst()
            .get()
            .toString()
    }

    private fun variaAddressToFileAddress(address: Int): Int {
        val b1 = address shr 16
        val a1 = (b1 - 0x80) shr 1

        val b2 = address and 0xFFFF
        val a2 = if ((b1 and 1) == 0) b2 and 0x7FFF else b2

        return (a1 shl 16) or a2
    }
}
