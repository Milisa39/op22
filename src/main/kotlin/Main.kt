import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import java.io.File

@JacksonXmlRootElement(localName = "students")
data class Students @JsonCreator constructor(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "student")
    @JsonProperty("student")
    val student: List<Student>
)

data class Student @JsonCreator constructor(
    @JacksonXmlProperty(localName = "first_name")
    @JsonProperty("first_name")
    val firstName: String,

    @JacksonXmlProperty(localName = "second_name")
    @JsonProperty("second_name")
    val secondName: String,

    @JsonProperty("skills")
    val skills: Skills
)

data class Skills @JsonCreator constructor(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "skill")
    @JsonProperty("skill")
    val skill: List<Skill>
)

data class Skill(
    @field:JacksonXmlProperty(isAttribute = true)
    val hard: Boolean? = null,

    @field:JacksonXmlProperty(isAttribute = true)
    val soft: Boolean? = null,

    @field:JacksonXmlText
    val name: String = ""
)

fun main() {
    val xmlFile = File("students.xml") // adjust if needed
    val xmlMapper = XmlMapper()

    val students: Students = xmlMapper.readValue(xmlFile, Students::class.java)

    println("Parsed students:")
    students.student.forEach { student ->
        println("${student.firstName} ${student.secondName}")
        student.skills.skill.forEach { skill ->
            println("  - ${skill.name} (hard=${skill.hard}, soft=${skill.soft})")
        }
    }
}
