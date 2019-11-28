package perangkaikode.com.sampletemplatingclass.sample_pagination.model

import java.io.Serializable

data class Student(val name: String, val emailId: String) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }
}
