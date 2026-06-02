package com.example.zoo.controller

import com.example.zoo.model.Animal
import com.example.zoo.service.ZooService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.http.MediaType
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post
import tools.jackson.databind.ObjectMapper

@SpringBootTest
@AutoConfigureMockMvc
class ZooControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc
    // allows you to sent requests to your rest api without http requests

    @Autowired
    lateinit var objectMapper: ObjectMapper
    //for serializing and deserializing into jsona nd from json

    @Nested
    @DisplayName("GET /animals")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetAnimals {
        @Test
        fun `should return all the animals `() {
            // when
            mockMvc.get("/api/animals")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].id") { value("1") }
                    jsonPath("$[0].name") { value("Leo") }


                }
        }
    }

    @Nested
    @DisplayName("GET /animal")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetAnimal {
        @Test
        fun `should return the animal with the given name `() {
            // given

            val name = "Leo"
            // when
            mockMvc.get("/api/animals/$name")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.name") { value("Leo") }
                }


            // then

        }
    }

    @Test
    fun `should return NOT FOUND if the name of animal does not exist`() {
        // given
        val name = "Jobin"

        // when
        mockMvc.get("/api/animals/$name")
            .andDo { print() }
            .andExpect {
                status { isNotFound() }
            }




        // then

    }

    @Nested
    @DisplayName("POST/api/animals")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PostNewAnimal {
        @Test
        fun `should add a new animal`() {
            // given
            val newAnimal = Animal(21, "Oscar", "Orangutan", 12, "Male", "Rainforest House", "ENC-402", "Omnivore", 85.0, 140.0, "Healthy", "Critically Endangered", "2017-03-18", "12:30", "David Lee")
    
    
            // when
            val performPost=mockMvc.post("/api/animals") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newAnimal)
            }
    
            //then
            performPost.andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.id") { value("21") }
                }
    
        }
        
    @Test
    fun `should return BAD REQUEST when adding a id when it already exists`() {
        // given
        val invalidAnimal =  Animal(1, "Leo", "Lion", 8, "Male", "Savannah Zone", "ENC-101", "Carnivore", 190.5, 120.0, "Healthy", "Vulnerable", "2019-03-15", "09:00", "John Smith")


        // when

        val performPost=mockMvc.post("/api/animals") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalidAnimal)
        }
        
    
        // then
        performPost.andDo { print() }
        .andExpect {
            status { isBadRequest() }
        }
        
    }



     @Nested
     @DisplayName("PATCH/api/animals")
     @TestInstance(Lifecycle.PER_CLASS)
     inner class PatchAnimal{
         @Test
         fun `should return the updated Animal data`() {
             // given
             val newAge = 333
             val patchedAnimal =  Animal(1, "Leo", "Lion", newAge, "Male", "Savannah Zone", "ENC-101", "Carnivore", 190.5, 120.0, "Healthy", "Vulnerable", "2019-03-15", "09:00", "John Smith")


             // when
             val performPatch=mockMvc.patch("/api/animals") {
                 contentType = MediaType.APPLICATION_JSON
                 content = objectMapper.writeValueAsString(patchedAnimal)
             }

             // then
             performPatch.andDo { print() }
             .andExpect {
                 status { isOk() }
                 content { contentType(MediaType.APPLICATION_JSON) }
                 jsonPath("$.age") { value(newAge) }
             }

         }


     }

      @Test
      fun `should return BAD REQUEST when trying to patch with the same data`() {
          // given
          val updatedAnimal =  Animal(1, "Leo", "Lion", 8, "Male", "Savannah Zone", "ENC-101", "Carnivore", 190.5, 120.0, "Healthy", "Vulnerable", "2019-03-15", "09:00", "John Smith")



          // when

          val performPatch=mockMvc.patch("/api/animals") {
              contentType = MediaType.APPLICATION_JSON
              content = objectMapper.writeValueAsString(updatedAnimal)
          }


          // then
          performPatch.andDo { print() }
          .andExpect {
              status { isBadRequest() }
          }

      }


       @Test
       fun `should return NOT FOUND if an animal with an id not in database is trying to patch`() {
           // given
           val updatedAnimal =  Animal(55, "Leo", "Lion", 8, "Male", "Savannah Zone", "ENC-101", "Carnivore", 190.5, 120.0, "Healthy", "Vulnerable", "2019-03-15", "09:00", "John Smith")

           // when
           val performPatch=mockMvc.patch("/api/animals") {
               contentType = MediaType.APPLICATION_JSON
               content = objectMapper.writeValueAsString(updatedAnimal)
           }

           // then
           performPatch.andDo { print() }
               .andExpect {
                   status { isNotFound() }
               }

       }

       @Nested
       @DisplayName("DELETE /api/animals")
       @TestInstance(Lifecycle.PER_CLASS)
       inner class DeleteAnimal {
           @Test
           fun `should delete an animal from db if the id matches`() {
               // given
               val deletedAnimal =  Animal(1, "Leo", "Lion", 8, "Male", "Savannah Zone", "ENC-101", "Carnivore", 190.5, 120.0, "Healthy", "Vulnerable", "2019-03-15", "09:00", "John Smith")


               // when
               val performDelete=mockMvc.delete("/api/animals") {
                   contentType = MediaType.APPLICATION_JSON
                   content = objectMapper.writeValueAsString(deletedAnimal)
               }


               // then
               performDelete.andDo { print() }
               .andExpect {
                   status { isNoContent() }
               }

           }


       }


        @Test
        fun `should return NOT FOUND when trying to delete an animal with id which does not exist in the database`() {
            // given
            val deletedAnimal =  Animal(34, "Leo", "Lion", 8, "Male", "Savannah Zone", "ENC-101", "Carnivore", 190.5, 120.0, "Healthy", "Vulnerable", "2019-03-15", "09:00", "John Smith")


            // when
            val performDelete=mockMvc.delete("/api/animals") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(deletedAnimal)
            }


            // then
            performDelete.andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
            }

}
}